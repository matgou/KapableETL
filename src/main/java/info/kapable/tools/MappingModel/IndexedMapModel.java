package info.kapable.tools.MappingModel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import info.kapable.tools.pojo.Dimension;

public class IndexedMapModel extends AbstractModel {

	HashMap<Integer, Dimension> modelMap;
	
	public IndexedMapModel()
	{
		this.modelMap = new HashMap<Integer, Dimension>();
	}
	
	/**
	 * Define in model mapping between index and type 
	 * @param index
	 * @param type Java class name String 
	 */
	public void setMapping(Integer index, Dimension dimension)
	{
		this.modelMap.put(index, dimension);
	}
	
	/**
	 * 
	 * @param index
	 * @return the type of specified value
	 */
	public String getTypeOf(Integer index) {
		return this.modelMap.get(index).getType();
	}

	@Override
	public void init(InputStream input) {
		// nothing to do, in this model the map is independant to data
		
	}

	@Override
	public int getDimentionLenght() {
		return this.modelMap.size();
	}

	public Dimension getDimentionFor(int i) {
		return this.modelMap.get(i);
	}

	@Override
	public void setColumn(List<Dimension> dims) {
		this.modelMap.clear();
		for(Dimension dim: dims) {
			this.setMapping(dim.getIndex(), dim);
		}
	}

}
