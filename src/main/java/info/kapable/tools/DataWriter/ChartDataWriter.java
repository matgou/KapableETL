package info.kapable.tools.DataWriter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.FixedMillisecond;

import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.pojo.DateTimeDimension;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class ChartDataWriter extends AbstractDataWriter {

	private JFreeChart chart;
	private String chartTitle;

	private String XTitle;
	private String YTitle;
	private HashMap<Dimension, TimeSeries> series;
	private DateTimeDimension xDimension;
	private TimeSeriesCollection dataset;
	private String filenameResult;

	public ChartDataWriter(String filenameResult) {
		super();
		this.series = new HashMap<Dimension, TimeSeries>();
		this.filenameResult = filenameResult;
	}

	@Override
	public void write(Vector vector) {
		// TODO Auto-generated method stub
		Object x;
		try {
			x = vector.get(xDimension);
			Iterator<Entry<Dimension, TimeSeries>> it = this.series.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Dimension, TimeSeries> pair = (Map.Entry<Dimension, TimeSeries>) it.next();
				Dimension dimension = pair.getKey();
				TimeSeries serie = pair.getValue();
				serie.addOrUpdate(new FixedMillisecond((Date) x), ((Integer) vector.get(dimension)).intValue());
			}
		} catch (DimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		this.dataset = new TimeSeriesCollection();
		Iterator<Entry<Dimension, TimeSeries>> it = this.series.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Dimension, TimeSeries> pair = (Map.Entry<Dimension, TimeSeries>) it.next();
			TimeSeries serie = pair.getValue();
			this.dataset.addSeries(serie);
		}
		chart = ChartFactory.createTimeSeriesChart(chartTitle, XTitle, YTitle, this.dataset, true, true, false);

		((DateAxis) (chart.getXYPlot().getDomainAxis())).setDateFormatOverride(this.xDimension.getFormat());
		try {
			ChartUtilities.saveChartAsJPEG(new File(filenameResult), chart, 800, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

	public void setDimensionToGraph(Map<String, Dimension> dimensions) {
		Iterator<Entry<String, Dimension>> it = dimensions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Dimension> pair = (Map.Entry<String, Dimension>) it.next();
			String title = pair.getKey();
			Dimension dimension = pair.getValue();
			TimeSeries serie = new TimeSeries(title);
			this.series.put(dimension, serie);
		}
	}

	public String getXTitle() {
		return XTitle;
	}

	public void setXTitle(String xTitle) {
		XTitle = xTitle;
	}

	public DateTimeDimension getxDimension() {
		return xDimension;
	}

	public void setxDimension(DateTimeDimension xDimension) {
		this.xDimension = xDimension;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getYTitle() {
		return YTitle;
	}

	public void setYTitle(String yTitle) {
		YTitle = yTitle;
	}

}
