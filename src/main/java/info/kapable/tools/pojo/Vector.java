package info.kapable.tools.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.kapable.tools.Exception.DimensionException;

public class Vector {
	private Map<Integer,Object> values;
	
	public Vector()
	{
		this.values = new HashMap<Integer,Object>();
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
		this.values.put(dimension, value);
	}
	public void set(Dimension dimension, Object value) throws DimensionException
	{
		try {
			if(Class.forName(dimension.getType()).isInstance(value))
				this.values.put(dimension.index, value);
			else
				throw new DimensionException("Trying to put value of invalid type into dimention");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public Object get(Dimension dimension) throws DimensionException
	{
		if(this.values.containsKey(dimension.index))
			return this.values.get(dimension.index);
		else 
			throw new DimensionException("Vector as no dimension: " + dimension.index + "(" + dimension.type + ")");
	}

	public Map<Integer, Object> getAllValues()
	{
		return this.values;
	}

	public void addDimension(Dimension dimensionResult, Object resultVal) {
		this.values.put(dimensionResult.index,resultVal);
	}

	public boolean hasDimension() {
		return (this.values.size()!=0);
	}
}
