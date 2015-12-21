package info.kapable.tools.MappingModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import info.kapable.tools.pojo.Dimension;

public class NamedMapModel extends IndexedMapModel {

	private HashMap<Integer,String> dimentionNames;
	
	public NamedMapModel()
	{
		super();
		this.dimentionNames = new HashMap<Integer,String>();
	}
	
	public void setAttributes(Map<String,Dimension> map)
	{
	    Iterator<Entry<String, Dimension>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<String,Dimension> pair = (Map.Entry<String,Dimension>)it.next();
	        String name = (String) pair.getKey();
	        Dimension dim = (Dimension) pair.getValue();
	        this.setMapping(dim.getIndex(), dim, name);
	    }
	}

	public void setMapping(Integer index, Dimension dimension)
	{
		this.setMapping(index, dimension, index.toString());
	}
	
	public void setMapping(Integer index, Dimension dimension, String columnName)
	{
		this.modelMap.put(index, dimension);
		this.dimentionNames.put(index, columnName);
	}
	
	public String getName(Integer index) {
		return this.dimentionNames.get(index);
	}
	
	public String getName(Dimension dim) 
	{
		return this.getName(dim.getIndex());
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
	
}
