package info.kapable.tools.DataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;

import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class TwigDataWriter extends AbstractDataWriter {

	private String templatePath;
	private List<Map<String, Object>> data;
	private BufferedWriter output;
	private NamedMapModel model;
	private Dimension nameColumn;
	
	public TwigDataWriter(OutputStream output, String templatePath, NamedMapModel model)
	{
		super();
		this.templatePath = templatePath;
		this.output = new BufferedWriter(new OutputStreamWriter(output));
		this.setModel(model);
		this.data = new ArrayList<Map<String, Object>>();
	}

	public TwigDataWriter(OutputStream output, String templatePath, NamedMapModel model, Dimension nameColumn)
	{
		super();
		this.templatePath = templatePath;
		this.output = new BufferedWriter(new OutputStreamWriter(output));
		this.setModel(model);
		this.data = new ArrayList<Map<String, Object>>();
		this.nameColumn = nameColumn;
	}

	@Override
	public void write(Vector vector) {
		this.data.add(vector.toHashMap(this.model));
	}

	@Override
	public void close() {
        JtwigTemplate template = new JtwigTemplate(new File(this.templatePath), new JtwigConfiguration());
        try {
        	JtwigModelMap twigModelMap = new JtwigModelMap();
        	twigModelMap.withModelAttribute("data", this.data);
        	if(this.nameColumn != null) {
        		Map<String, List<Object>> vecData = new HashMap<String, List<Object>>();
        		String nameColumnString = this.model.getName(nameColumn);
        		for(Map<String, Object> vec: this.data) {
        			String key = (String) vec.get(nameColumnString);
        			if(!vecData.containsKey(key))
        			{
        				vecData.put(key, new ArrayList<Object>());
        			}
        			vecData.get(key).add(vec);
        		}
        		Iterator<Entry<String, List<Object>>> it = vecData.entrySet().iterator();
        		while(it.hasNext())
        		{
        			Entry<String, List<Object>> pair = it.next();
        			twigModelMap.withModelAttribute(pair.getKey(), pair.getValue());
        		}
        	}
			String output_compiled = template.output(twigModelMap);

			try {
				output.write(output_compiled);
				this.output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NamedMapModel getModel() {
		return model;
	}

	public void setModel(NamedMapModel model) {
		this.model = model;
	}

}
