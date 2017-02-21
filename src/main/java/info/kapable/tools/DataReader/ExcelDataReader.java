package info.kapable.tools.DataReader;

import info.kapable.tools.Exception.ConversionNotFoundException;
import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.Exception.NotCompatibleMappingException;
import info.kapable.tools.MappingModel.AbstractModel;
import info.kapable.tools.MappingModel.IndexedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataReader extends AbstractDataReader {

	InputStream input;
    XSSFWorkbook  wb;

	private IndexedMapModel dataModel;
	private Sheet sheet;
	private int headerToIgnore = 0;
	private int lineCounter = headerToIgnore;
	
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
	public ExcelDataReader(InputStream input, IndexedMapModel model, String sheetName) {
		this.input = input;
		this.dataModel = model;
    	try {
			this.wb = new XSSFWorkbook(input);
			this.sheet = wb.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(255);
		}
		this.dataModel.init(input);
	}
		
	@Override
	public Vector doRead() throws IOException {
		this.lineCounter = this.lineCounter + 1;
		Vector vector = this.dataModel.newVector();
		try {
        	Row row = sheet.getRow(lineCounter);
        	String value = null;
        	for(int column = 0; column < this.dataModel.getDimentionLenght(); column++) {
        		value=row.getCell(column).getStringCellValue();
        		if(value != null && value.length() > 0) {
        			Dimension dim = this.dataModel.getDimentionFor(column);
        			if(dim == null)
					{
						throw new DimensionException("Dimention for column "+(column+1)+" is not defined");
					}
					vector.set(dim, dim.getValFromString(value));
        		}
        	}
			return vector;
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
