package info.kapable.tools.DataWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimention;
import info.kapable.tools.pojo.Vector;

public class JsonDataWriter extends AbstractDataWriter {

	private NamedMapModel model;
	private BufferedWriter output;
	private ObjectMapper mapper;
	private ArrayNode root;
	
	public JsonDataWriter(OutputStream output, NamedMapModel model)
	{
		super();
		this.model = model;
		this.output = new BufferedWriter(new OutputStreamWriter(output));

		mapper = new ObjectMapper();

		root = mapper.createArrayNode();
	}
	@Override
	public void write(Vector vector) {
		List<Object> vectorValues = vector.getAllValues();
		ObjectNode node = mapper.createObjectNode();
		for(Dimention dim: this.model.getDimentions())
		{
			String string = dim.getStringFromVal(vector.get(dim));
			String colName = model.getName(dim);
			node.put(colName, string);
		}
		root.add(node);
	}

	@Override
	public void close() {
		try {
			output.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
			this.output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
