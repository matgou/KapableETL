package info.kapable.tools.DataWriter;

import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.InfluxDBFactory;

public class InfluxDBDataWriter extends AbstractDataWriter {

	private InfluxDB influxDB;
	private BatchPoints batchPoints;

	private NamedMapModel dataModel;
	private String measurementName;
	private String valueColumnName;
	private String timeColumnName;
	private SimpleDateFormat timeFormat;

	public InfluxDBDataWriter(NamedMapModel dataModel, String dbURL, String dbName, String measurementName, String valueColumnName) {
		this(dataModel, dbURL, dbName, measurementName, valueColumnName, null, null);
	}
	public InfluxDBDataWriter(NamedMapModel dataModel, String dbURL, String dbName, String measurementName, String valueColumnName, String timeColumnName, String timeFormat) {
		super();
		this.dataModel = dataModel;
		this.measurementName = measurementName;
		this.valueColumnName = valueColumnName;
		this.timeColumnName = timeColumnName;
		this.timeFormat = new SimpleDateFormat(timeFormat);
		this.influxDB = InfluxDBFactory.connect(dbURL);
		batchPoints = BatchPoints
                .database(dbName)
                .retentionPolicy("autogen")
                .consistency(ConsistencyLevel.ALL)
                .build();
	}

	@Override
	public void write(Vector vector) {
		Builder point = Point.measurement(measurementName);
		try {
			if(this.timeColumnName == null) {
				point = point.time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
			} else {
				Dimension dimentionTime = dataModel.getDimentionForName(this.timeColumnName);
				String value;
				try {
					value = (String) vector.get(dimentionTime);
				} catch (DimensionException e) {
					e.printStackTrace();
					return;
				}
				point = point.time(this.convertStringToTS(value), TimeUnit.MILLISECONDS);
			}
			for(Dimension dim: dataModel.getDimentions()) {
				if(dim.getDimentionName().contentEquals(this.valueColumnName)) {
					Double value = (Double) vector.get(dim);
					point = point.addField("value", value);
				} else {
					String value = (String) vector.get(dim);
					point = point.tag(dim.getDimentionName(), value);
				}
			}
			batchPoints.point(point.build());
		} catch (DimensionException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private long convertStringToTS(String value) throws ParseException {
		Date date = this.timeFormat.parse(value);
		Calendar c = Calendar.getInstance();
	    c.setTime(date);

		return c.getTimeInMillis();
	}
	@Override
	public void close() {
		influxDB.write(batchPoints);
		influxDB.close();
	}
}
