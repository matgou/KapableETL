package info.kapable.tools.MappingModel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import info.kapable.tools.pojo.Dimention;

public class IndexedMapModel extends AbstractModel {

	HashMap<Integer, Dimention> modelMap;
	
	public IndexedMapModel()
	{
		this.modelMap = new HashMap<Integer, Dimention>();
	}
	
	/**
	 * Define in model mapping between index and type 
	 * @param index
	 * @param type Java class name String 
	 */
	public void setMapping(Integer index, Dimention dimention)
	{
		this.modelMap.put(index, dimention);
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

	public Dimention getDimentionFor(int i) {
		return this.modelMap.get(i);
	}

	@Override
	public void setColumn(List<Dimention> dims) {
		this.modelMap.clear();
		for(Dimention dim: dims) {
			this.setMapping(dim.getIndex(), dim);
		}
	}

}
