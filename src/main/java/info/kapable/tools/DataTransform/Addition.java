package info.kapable.tools.DataTransform;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import info.kapable.tools.Exception.NotSummableException;
import info.kapable.tools.pojo.Dimention;
import info.kapable.tools.pojo.Vector;

/**
 * This class can sum attributes of vector, attributes must be Date or Integer
 * 
 * You must defined a new dimension to store the result via the dimentionResult property
 * You must defined the list of dimension to sum via the dimentionToSum property
 * @author matgou
 *
 */
public class Addition extends AbstractDataTransform {

	/**
	 * the list of dimension to sum
	 */
	private List<Dimention> dimentionToSum;
	/**
	 * a new dimension to store the result
	 */
	private Dimention dimentionResult;

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
			resultClass = Class.forName(dimentionResult.getType());
			
			resultVal = resultClass.newInstance();
			if(Integer.class.isInstance(resultVal))
			{
				for(Dimention dim: this.getDimentionToSum())
				{
					Integer retour = (Integer) vector.get(dim);
					Integer init = (Integer) resultVal;
					resultVal = init + retour;
				}
			} else if(Date.class.isInstance(resultVal))
			{
				resultVal = new Date(0);
				for(Dimention dim: this.getDimentionToSum())
				{
					Calendar cal = Calendar.getInstance();
					cal.setTime((java.util.Date) resultVal);
					long orig = cal.getTimeInMillis();
					
					Date inputTime = (Date) vector.get(dim);
					Calendar calInput = Calendar.getInstance();
					calInput.setTime((java.util.Date) inputTime);
					long toAdd = calInput.getTimeInMillis();

					resultVal = new Date(orig + toAdd);
				}
			} else {
				throw new NotSummableException("Le type " + dimentionResult.getType() + " ne peux pas être additionné");
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
		vector.addDimention(dimentionResult, resultVal);
		return vector;
	}
	
	/**
	 * Defined the list of dimension to sum
	 * @return
	 */
	public List<Dimention> getDimentionToSum() {
		return dimentionToSum;
	}
	public void setDimentionToSum(List<Dimention> dimentionToSum) {
		this.dimentionToSum = dimentionToSum;
	}
	
	/**
	 * Defined a new dimension to store the result
	 * @return
	 */
	public Dimention getDimentionResult() {
		return dimentionResult;
	}
	public void setDimentionResult(Dimention dimentionResult) {
		this.dimentionResult = dimentionResult;
	}

}
