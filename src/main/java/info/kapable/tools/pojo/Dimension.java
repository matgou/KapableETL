package info.kapable.tools.pojo;

import info.kapable.tools.Exception.ConversionNotFoundException;

public class Dimension {
	private static int maxIndex;
	int index;
	String type;
	private String dimentionName;
	
	public int getIndex()
	{
		return this.index;
	}
	
	public Dimension (int index, String type)
	{
		this.setDimentionName(dimentionName);
		this.index = index;
		this.type = type;
		if(this.index > Dimension.maxIndex) {
			Dimension.maxIndex = this.index;
		}
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

	public static int getMaxIndex() {
		return Dimension.maxIndex;
	}

	public String getDimentionName() {
		return dimentionName;
	}

	public void setDimentionName(String dimentionName) {
		this.dimentionName = dimentionName;
	}
	
}
