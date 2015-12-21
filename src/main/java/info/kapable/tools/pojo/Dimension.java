package info.kapable.tools.pojo;

import info.kapable.tools.Exception.ConversionNotFoundException;

public class Dimension {
	int index;
	String type;
	
	public int getIndex()
	{
		return this.index;
	}
	
	public Dimension (int index, String type)
	{
		this.index = index;
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
		
	}

	public Object getValFromString(String string) throws ConversionNotFoundException {
		try {
			if(Class.forName(this.type) == string.getClass())
				return string;
			if(Class.forName(this.type) == java.util.Date.class)
				return new java.util.Date();
			if(Class.forName(this.type) == java.lang.Integer.class)
				return Integer.parseInt(string.replace(" ", ""));
			throw new ConversionNotFoundException();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ConversionNotFoundException();
		}
	}

	public String getStringFromVal(Object object) {
		return object.toString();
	}
	
}
