package info.kapable.tools.MappingModel;

import java.io.InputStream;
import java.util.List;

import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public abstract class AbstractModel {
                 
	public abstract int getDimentionLenght();
	
	/**
	 * This method is call when model is associated to reader
	 * @param input
	 */
	public abstract void init(InputStream input);

	public abstract void setColumn(List<Dimension> dim);
	
	public Vector newVector() {
		return new Vector();
	}
}
