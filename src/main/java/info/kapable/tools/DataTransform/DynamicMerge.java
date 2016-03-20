package info.kapable.tools.DataTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import info.kapable.tools.DataReader.AbstractDataReader;
import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class DynamicMerge extends AbstractDataTransform {

	public Boolean isInitialize = false;
	public List<Vector> vectors;
	public Integer index = 0;
	private Dimension propertyKey;
	private Dimension propertyValue;
	private Dimension dimentionId;
	private NamedMapModel namedModel;
	
	public DynamicMerge(Dimension id, Dimension propertyKey, Dimension propertyValue, NamedMapModel model)
	{
		this.setNamedModel(model);
		this.setDimentionId(id);
		this.setPropertyKey(propertyKey);
		this.setPropertyValue(propertyValue);
		this.vectors = new ArrayList<Vector>();
	}

	@Override
	public Vector doRead() throws IOException {
		// if already isInitialize return vector
		if(isInitialize) {
			if(this.index < this.vectors.size()) {
				Vector vector = vectors.get(this.index);	
				this.index = this.index + 1;
				return vector;
			} else {
				return null;
			}
		}
		
		try {
			this.processAll();
		} catch (DimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		this.isInitialize = true;
		return this.doRead();
	}
	
	/**
	 * Process all vector and map
	 * @throws IOException 
	 * @throws DimensionException 
	 */
	private void processAll() throws IOException, DimensionException {
		AbstractDataReader reader = this.currentReader;
		Vector vec = reader.doRead();
		while(vec != null)
		{
			 this.process(vec);
			 vec = reader.doRead();
		}
	}

	private void process(Vector vec) throws DimensionException {
		Boolean isNew = true;
		String id = (String) vec.get(this.dimentionId);
		Iterator<Vector> it = this.vectors.iterator();
		while (it.hasNext()) {
			Vector currentVector = it.next();
			
			String currentId = (String) currentVector.get(this.dimentionId);
			
			// if Key match copy propertyValue
			if(currentId.equals(id)) {
				this.copy(currentVector, vec, this.propertyKey, this.propertyValue);
				isNew = false;
			}
		}
		if(isNew) {
			vec = this.copy(vec, vec, this.propertyKey, this.propertyValue);
			this.vectors.add(vec);
		}
	}

	private Vector copy(Vector currentVector, Vector vec, Dimension propertyKey2, Dimension propertyValue2) {
		// Test if a dimention exit
		try {
			String dimentionName = (String) vec.get(propertyKey2);
			if(!this.namedModel.hasDimentionForName(dimentionName))
			{
				Integer index = Dimension.getMaxIndex()+1;
			
				Dimension dimension = new Dimension(index, "java.lang.String");
				this.namedModel.setMapping(index, dimension, dimentionName);
				currentVector.set(dimension, vec.get(propertyValue2));
			} else {
				Dimension dimension = this.namedModel.getDimentionForName(dimentionName);
				currentVector.set(dimension, vec.get(propertyValue2));
			}
		} catch (DimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentVector;
	}

	public Dimension getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(Dimension propertyKey) {
		this.propertyKey = propertyKey;
	}

	public Dimension getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Dimension propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Dimension getDimentionId() {
		return dimentionId;
	}

	public void setDimentionId(Dimension dimentionId) {
		this.dimentionId = dimentionId;
	}

	public NamedMapModel getNamedModel() {
		return namedModel;
	}

	public void setNamedModel(NamedMapModel namedModel) {
		this.namedModel = namedModel;
	}
}