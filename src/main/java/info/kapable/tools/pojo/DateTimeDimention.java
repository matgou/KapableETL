package info.kapable.tools.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.kapable.tools.Exception.ConversionNotFoundException;

public class DateTimeDimention extends Dimention {

	private SimpleDateFormat format;
	
	public DateTimeDimention(int dimentionLenght, SimpleDateFormat format)
	{
		super(dimentionLenght, "java.util.Date");
		
		this.format = format;
	}
	
	public Object getValFromString(String string) throws ConversionNotFoundException {
		try {
			return format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ConversionNotFoundException();
		}
	}
	

	public String getStringFromVal(Object object) {
		return this.format.format(object);
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}
	
}
