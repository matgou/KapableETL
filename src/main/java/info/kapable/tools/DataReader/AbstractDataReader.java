package info.kapable.tools.DataReader;

import java.io.IOException;

import info.kapable.tools.Exception.NotCompatibleMappingException;
import info.kapable.tools.MappingModel.AbstractModel;
import info.kapable.tools.pojo.Vector;

public abstract class AbstractDataReader {

	AbstractModel dataModel;
	
	public void setModel(AbstractModel model) throws NotCompatibleMappingException {
		this.dataModel = model;
	}
	
	public abstract Vector doRead() throws IOException;
	
}
