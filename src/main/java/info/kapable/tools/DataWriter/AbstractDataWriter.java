package info.kapable.tools.DataWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.DataReader.AbstractDataReader;
import info.kapable.tools.pojo.Vector;

public abstract class AbstractDataWriter {
	private List<AbstractDataReader> input;
	
	public AbstractDataWriter()
	{
		this.input = new ArrayList<AbstractDataReader>();
	}
	
	public void addInput(AbstractDataReader dataReader) {
		this.input.add(dataReader);
	}
	
	public List<AbstractDataReader> getInput()
	{
		return this.input;
	}
	
	
	/**
	 * Write a vector
	 * @param vector
	 */
	public abstract void write(Vector vector);
	
	/**
	 * At the end close the flow
	 */
	public abstract void close();
	
	public void process() throws IOException
	{
		for(AbstractDataReader reader: this.input) {
			Vector vec = reader.doRead();
			while(vec != null)
			{
				 this.write(vec);
				 vec = reader.doRead();
			}
		}
	}
}
