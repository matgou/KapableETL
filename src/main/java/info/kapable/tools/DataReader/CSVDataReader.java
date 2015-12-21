package info.kapable.tools.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import info.kapable.tools.Exception.ConversionNotFoundException;
import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.Exception.NotCompatibleMappingException;
import info.kapable.tools.MappingModel.AbstractModel;
import info.kapable.tools.MappingModel.IndexedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class CSVDataReader extends AbstractDataReader {

	InputStream input;
	BufferedReader bufferReader;
	String separator;

	private IndexedMapModel dataModel;
	private int headerToIgnore = 0;
	private boolean headerAlredyIgnore = false;
	
	public void setModel(AbstractModel model) throws NotCompatibleMappingException {
		if(IndexedMapModel.class.isInstance(model))
		{
			throw new NotCompatibleMappingException();
		}
		this.dataModel = (IndexedMapModel) model;
	}
	
	
	/**
	 * Init the CSVDataReader
	 * @param input
	 * @param model
	 * @param separator
	 */
	public CSVDataReader(InputStream input, IndexedMapModel model, String separator) {
		this.input = input;
		this.dataModel = model;
		this.bufferReader = new BufferedReader(new InputStreamReader(input));
		this.separator = separator;
		this.dataModel.init(input);
	}
	
	/**
	 * Init the CSVDataReader with default separator ","
	 * @param input
	 * @param model
	 */
	public CSVDataReader(InputStream input, IndexedMapModel model) {
		this(input, model, ",");
		
	}
	
	@Override
	public Vector doRead() throws IOException {
		String line = null;
		Vector vector = this.dataModel.newVector();
		try {
			// If input contains line, read line
			if(headerAlredyIgnore == false)
			{
				for(int i=0; i<headerToIgnore; i++)
				{
					this.bufferReader.readLine();
				}
				this.headerAlredyIgnore = true;
			}
			if((line = this.bufferReader.readLine()) != null)
			{
				String[] data = line.split(this.separator);
				for(int i=0; i < data.length; i++)
				{
					Dimension dim = this.dataModel.getDimentionFor(i);
					if(dim == null)
					{
						throw new DimensionException("Dimention for column "+(i+1)+" is not defined");
					}
					vector.set(dim, dim.getValFromString(data[i]));
				}
				return vector;
			}
		} catch (DimensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConversionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setHeaderToIgnore(int headerToIgnore)
	{
		this.headerToIgnore = headerToIgnore;
	}
	
	public int getHeaderToIgnore()
	{
		return this.headerToIgnore;
	}
}
