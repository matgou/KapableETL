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

import info.kapable.tools.pojo.DateTimeDimention;
import info.kapable.tools.pojo.Dimention;
import info.kapable.tools.pojo.Vector;

public class ChartDataWriter extends AbstractDataWriter {
	
	private JFreeChart chart;
	private String chartTitle;
	
	private String XTitle;
	private String YTitle;
	private HashMap<Dimention, TimeSeries> series;
	private DateTimeDimention xDimention;
	private TimeSeriesCollection dataset;
	private String filenameResult;
	
	public ChartDataWriter(String filenameResult)
	{
		super();
		this.series = new HashMap<Dimention, TimeSeries>();
		this.filenameResult = filenameResult;
	}
	@Override
	public void write(Vector vector) {
		// TODO Auto-generated method stub
		Object x = vector.get(xDimention);

	    Iterator<Entry<Dimention, TimeSeries>> it = this.series.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Dimention, TimeSeries> pair = (Map.Entry<Dimention, TimeSeries>)it.next();
	        Dimention dimention=pair.getKey();
	        TimeSeries serie = pair.getValue();
	        serie.addOrUpdate(new FixedMillisecond((Date)x), ((Integer)vector.get(dimention)).intValue());
	    }
	}

	
	@Override
	public void close() {
		this.dataset = new TimeSeriesCollection();
	    Iterator<Entry<Dimention, TimeSeries>> it = this.series.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Dimention, TimeSeries> pair = (Map.Entry<Dimention, TimeSeries>)it.next();
	        TimeSeries serie = pair.getValue();
	        this.dataset.addSeries(serie);
	    }
		chart = ChartFactory.createTimeSeriesChart(
		         chartTitle ,
		         XTitle ,
		         YTitle ,
		         this.dataset,
		         true , true , false);
		
		((DateAxis)(chart.getXYPlot().getDomainAxis())).setDateFormatOverride(this.xDimention.getFormat()); 
        try {
			ChartUtilities.saveChartAsJPEG(new File(filenameResult), chart, 800, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

	public void setDimentionToGraph(Map<String,Dimention> dimentions)
	{
	    Iterator<Entry<String, Dimention>> it = dimentions.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Dimention> pair = (Map.Entry<String, Dimention>)it.next();
	        String title = pair.getKey();
	        Dimention dimention = pair.getValue();
	        TimeSeries serie = new TimeSeries(title);
		 	this.series.put(dimention, serie);
	    }
	}
	
	public String getXTitle() {
		return XTitle;
	}

	public void setXTitle(String xTitle) {
		XTitle = xTitle;
	}

	public DateTimeDimention getxDimention() {
		return xDimention;
	}

	public void setxDimention(DateTimeDimention xDimention) {
		this.xDimention = xDimention;
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
