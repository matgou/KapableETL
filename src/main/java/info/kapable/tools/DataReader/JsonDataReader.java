package info.kapable.tools.DataReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.kapable.tools.Exception.NotCompatibleMappingException;
import info.kapable.tools.MappingModel.AbstractModel;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class JsonDataReader extends AbstractDataReader {

	private NamedMapModel dataModel;
	private InputStream input;
	private ObjectMapper mapper;
	private JsonNode root;
	private Iterator<JsonNode> it;

	public JsonDataReader()
	{
		mapper = new ObjectMapper();
	}
	
	private void initialize() throws JsonProcessingException, IOException
	{
		if(root==null)
		{
			this.root = mapper.readTree(input);
			this.it = this.root.iterator();
		}
		
	}

	public void setModel(AbstractModel model) throws NotCompatibleMappingException {
		if(!NamedMapModel.class.isInstance(model))
		{
			throw new NotCompatibleMappingException();
		}
		this.dataModel = (NamedMapModel) model;
	}

	@Override
	public Vector doRead() throws IOException {
		this.initialize();
		Vector vector = this.dataModel.newVector();
		if(!it.hasNext())
			return null;
		JsonNode node = it.next();
		for(Dimension dim: this.dataModel.getDimentions())
		{
			String value = node.get(this.dataModel.getName(dim)).asText();
			vector.addDimension(dim, value);
		}
		return vector;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}
	

}
