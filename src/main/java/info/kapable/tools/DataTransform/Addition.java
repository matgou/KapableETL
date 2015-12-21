package info.kapable.tools.DataTransform;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.Exception.NotSummableException;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

/**
 * This class can sum attributes of vector, attributes must be Date or Integer
 * 
 * You must defined a new dimension to store the result via the dimensionResult property
 * You must defined the list of dimension to sum via the dimensionToSum property
 * @author matgou
 *
 */
public class Addition extends AbstractDataTransform {

	/**
	 * the list of dimension to sum
	 */
	private List<Dimension> dimensionToSum;
	/**
	 * a new dimension to store the result
	 */
	private Dimension dimensionResult;

	@Override
	public Vector doRead() throws IOException {
		Vector vector = super.doRead();
		// if empty
		if(vector == null)
			return null;
		Object resultVal = null;
		@SuppressWarnings("rawtypes")
		Class resultClass;
		try {
			resultClass = Class.forName(dimensionResult.getType());
			
			resultVal = resultClass.newInstance();
			if(Integer.class.isInstance(resultVal))
			{
				for(Dimension dim: this.getDimensionToSum())
				{
					Integer retour;
					try {
						retour = (Integer) vector.get(dim);
						Integer init = (Integer) resultVal;
						resultVal = init + retour;
					} catch (DimensionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if(Date.class.isInstance(resultVal))
			{
				resultVal = new Date(0);
				for(Dimension dim: this.getDimensionToSum())
				{
					Calendar cal = Calendar.getInstance();
					cal.setTime((java.util.Date) resultVal);
					long orig = cal.getTimeInMillis();
					
					Date inputTime;
					try {
						inputTime = (Date) vector.get(dim);
						Calendar calInput = Calendar.getInstance();
						calInput.setTime((java.util.Date) inputTime);
						long toAdd = calInput.getTimeInMillis();

						resultVal = new Date(orig + toAdd);
					} catch (DimensionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				throw new NotSummableException("Le type " + dimensionResult.getType() + " ne peux pas être additionné");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSummableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vector.addDimension(dimensionResult, resultVal);
		return vector;
	}
	
	/**
	 * Defined the list of dimension to sum
	 * @return
	 */
	public List<Dimension> getDimensionToSum() {
		return dimensionToSum;
	}
	public void setDimensionToSum(List<Dimension> dimensionToSum) {
		this.dimensionToSum = dimensionToSum;
	}
	
	/**
	 * Defined a new dimension to store the result
	 * @return
	 */
	public Dimension getDimensionResult() {
		return dimensionResult;
	}
	public void setDimensionResult(Dimension dimensionResult) {
		this.dimensionResult = dimensionResult;
	}

}
