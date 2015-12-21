package info.kapable.tools.pojo;

import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.Exception.DimensionException;

public class Vector {
	private List<Object> values;
	
	public Vector(int dimentionLenght)
	{
		this.values = new ArrayList<Object>();
		for(int i = 0; i < dimentionLenght; i++)
		{
			this.values.add(null);
		}
	}
	
	/**
	 * Set value on a vector dimention
	 * 
	 * @param dimention
	 * @param value
	 * @throws DimensionException 
	 */
	public void set(int dimension, Object value) throws DimensionException
	{
		if(dimension < 0 || dimension >= this.values.size())
		{
			throw new DimensionException("Trying to put value in unkown dimention (vector is an object in " + this.values.size() + " dimentions)");
		}
		this.values.set(dimension, value);
	}
	
	/**
	 * Get value of a vector dimention
	 * 
	 * @param dimention
	 * @return
	 */
	public Object get(int dimension)
	{
		return this.values.get(dimension);
	}
	public Object get(Dimension dimension)
	{
		return this.values.get(dimension.index);
	}

	public List<Object> getAllValues()
	{
		return this.values;
	}
	public void set(Dimension dim, Object valFromString) throws DimensionException {
		this.set(dim.index, valFromString);
		
	}

	public void addDimension(Dimension dimensionResult, Object resultVal) {
		this.values.add(resultVal);
	}
}
