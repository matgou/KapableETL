package info.kapable.tools.DataTransform;

import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.DataReader.AbstractDataReader;

public abstract class AbstractDataTransform extends AbstractDataReader{
	public AbstractDataTransform() {
		super();
		this.input = new ArrayList<AbstractDataReader>();
	}

	protected List<AbstractDataReader> input;

	public List<AbstractDataReader> getInput() {
		return input;
	}

	public void setInput(List<AbstractDataReader> input) {
		this.input = input;
	}
	
	
}
