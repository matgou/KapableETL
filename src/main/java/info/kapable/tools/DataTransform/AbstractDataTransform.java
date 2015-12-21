package info.kapable.tools.DataTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.DataReader.AbstractDataReader;
import info.kapable.tools.pojo.Dimention;
import info.kapable.tools.pojo.Vector;

/**
 * Abstract class representing a transformation of Vector flow
 * You can specify an list of input via input property
 * @author matgou
 *
 */
public abstract class AbstractDataTransform extends AbstractDataReader{

	protected List<AbstractDataReader> input;
	private int currentReaderIndex = 0;
	private AbstractDataReader currentReader;
	
	public AbstractDataTransform() {
		super();
		this.input = new ArrayList<AbstractDataReader>();
	}
	
	public Vector doRead() throws IOException
	{
		Vector vector = this.currentReader.doRead();
		if(vector == null)
		{
			if(currentReaderIndex >= this.input.size()-1)
			{
				return null;
			}
			currentReaderIndex=currentReaderIndex+1;
			this.currentReader = input.get(currentReaderIndex);
			vector = this.currentReader.doRead();
		}
		return vector;
	}


	public List<AbstractDataReader> getInput() {
		return input;
	}

	public void setInput(List<AbstractDataReader> input) {
		this.input = input;
		if(this.input.size() > 0)
			this.currentReaderIndex = 0;
			this.currentReader = input.get(0);
	}
	
	
}
