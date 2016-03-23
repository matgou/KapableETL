package info.kapable.tools.DataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.util.HashMap;
import java.util.Iterator;

import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class FreemarkerDataWriter extends AbstractDataWriter {

	private String templatePath;
	private List<Map<String, Object>> data;
	private OutputStream output;
	private NamedMapModel model;
	private Dimension nameColumn;

	public FreemarkerDataWriter(OutputStream output, String templatePath, NamedMapModel model) {
		super();
		this.templatePath = templatePath;
		this.output = output;
		this.setModel(model);
		this.data = new ArrayList<Map<String, Object>>();
	}

	public FreemarkerDataWriter(OutputStream output, String templatePath, NamedMapModel model, Dimension nameColumn) {
		super();
		this.templatePath = templatePath;
		this.output = output;
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
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setDefaultEncoding("UTF-8");
		Template template;
		try {
			cfg.setDirectoryForTemplateLoading(new File("."));
			template = cfg.getTemplate(this.templatePath);
			Writer out = new OutputStreamWriter(this.output);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("data", this.data);
			if (this.nameColumn != null) {
				Map<String, List<Object>> vecData = new HashMap<String, List<Object>>();
				String nameColumnString = this.model.getName(nameColumn);
				for (Map<String, Object> vec : this.data) {
					String key = (String) vec.get(nameColumnString);
					if (!vecData.containsKey(key)) {
						vecData.put(key, new ArrayList<Object>());
					}
					vecData.get(key).add(vec);
				}
				Iterator<Entry<String, List<Object>>> it = vecData.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, List<Object>> pair = it.next();
					root.put(pair.getKey(), pair.getValue());
				}
			}

			template.process(root, out);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
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

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}

}
