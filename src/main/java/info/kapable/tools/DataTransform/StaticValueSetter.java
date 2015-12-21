package info.kapable.tools.DataTransform;

import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import info.kapable.tools.Exception.ConversionNotFoundException;
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
public class StaticValueSetter extends AbstractDataTransform {

	/**
	 * a new dimension to store the result
	 */
	private Dimension dimensionResult;
	
	/**
	 * Static value type
	 */
	private String staticValue;

	@Override
	public Vector doRead() throws IOException {
		Vector vector = super.doRead();
		// if empty
		if(vector == null)
			return null;
		
		Object value = null;
		try {
			value = dimensionResult.getValFromString(this.staticValue);
		} catch (ConversionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vector.addDimension(dimensionResult, value);
		return vector;
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

	/**
	 * The value to add to vector
	 * @return
	 */
	public String getStaticValue() {
		return staticValue;
	}

	public void setStaticValue(String staticValue) {
		this.staticValue = staticValue;
	}
	
	

}
