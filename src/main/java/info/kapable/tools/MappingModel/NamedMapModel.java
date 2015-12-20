package info.kapable.tools.MappingModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import info.kapable.tools.pojo.Dimention;

public class NamedMapModel extends IndexedMapModel {

	private HashMap<Integer,String> dimentionNames;
	
	public NamedMapModel()
	{
		super();
		this.dimentionNames = new HashMap<Integer,String>();
	}
	
	public void setAttributes(Map<String,Dimention> map)
	{
	    Iterator<Entry<String, Dimention>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<String,Dimention> pair = (Map.Entry<String,Dimention>)it.next();
	        String name = (String) pair.getKey();
	        Dimention dim = (Dimention) pair.getValue();
	        this.setMapping(dim.getIndex(), dim, name);
	    }
	}

	public void setMapping(Integer index, Dimention dimention)
	{
		this.setMapping(index, dimention, index.toString());
	}
	
	public void setMapping(Integer index, Dimention dimention, String columnName)
	{
		this.modelMap.put(index, dimention);
		this.dimentionNames.put(index, columnName);
	}
	
	public String getName(Integer index) {
		return this.dimentionNames.get(index);
	}
	
	public String getName(Dimention dim) 
	{
		return this.getName(dim.getIndex());
	}

	public List<Dimention> getDimentions() {
		List<Dimention> list = new ArrayList<Dimention>();
		Iterator<Entry<Integer, Dimention>> it = this.modelMap.entrySet().iterator();
	    while (it.hasNext()) {
			Map.Entry<Integer,Dimention> pair = (Map.Entry<Integer,Dimention>)it.next();
	        list.add(pair.getValue());
	    }
		return list;
	}
	
}
