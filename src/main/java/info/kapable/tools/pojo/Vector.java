package info.kapable.tools.pojo;

import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.Exception.DimentionException;

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
	 * @throws DimentionException 
	 */
	public void set(int dimention, Object value) throws DimentionException
	{
		if(dimention < 0 || dimention >= this.values.size())
		{
			throw new DimentionException("Trying to put value in unkown dimention (vector is an object in " + this.values.size() + " dimentions)");
		}
		this.values.set(dimention, value);
	}
	
	/**
	 * Get value of a vector dimention
	 * 
	 * @param dimention
	 * @return
	 */
	public Object get(int dimention)
	{
		return this.values.get(dimention);
	}
	public Object get(Dimention dimention)
	{
		return this.values.get(dimention.index);
	}

	public List<Object> getAllValues()
	{
		return this.values;
	}
	public void set(Dimention dim, Object valFromString) throws DimentionException {
		this.set(dim.index, valFromString);
		
	}

	public void addDimention(Dimention dimentionResult, Object resultVal) {
		this.values.add(resultVal);
	}
}
