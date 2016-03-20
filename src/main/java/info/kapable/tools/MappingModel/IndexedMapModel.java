package info.kapable.tools.MappingModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import info.kapable.tools.pojo.Dimension;

public class IndexedMapModel extends AbstractModel {

	Map<Integer, Dimension> modelMap;
	
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
	
	public Integer getKeyFor(Dimension dim) {
		Iterator<Entry<Integer, Dimension>> it = this.modelMap.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<Integer,Dimension> pair = (Map.Entry<Integer,Dimension>)it.next();
	        if(pair.getValue() == dim)
	        {
	        	return pair.getKey();
	       	}
	    }
		return null;
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

	public List<Dimension> getDimentions() {
		List<Dimension> list = new ArrayList<Dimension>();
		Iterator<Entry<Integer, Dimension>> it = this.modelMap.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<Integer,Dimension> pair = (Map.Entry<Integer,Dimension>)it.next();
	        list.add(pair.getValue());
	    }
		return list;
	}

	public Map<Integer, Dimension> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<Integer, Dimension> modelMap) {
		this.modelMap = modelMap;
	}

}
