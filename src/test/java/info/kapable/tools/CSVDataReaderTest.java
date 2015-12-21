package info.kapable.tools;

import static org.junit.Assert.*;

import org.junit.Test;

import info.kapable.tools.DataReader.CSVDataReader;
import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.IndexedMapModel;
import info.kapable.tools.pojo.DateTimeDimension;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;
import sun.misc.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVDataReaderTest {

	
	@Test
	public void testReadCSV() {
		String CSVData=  "12/11/1988;1;2\n"
				+ "13/11/1988;test;4\n"
				+ "14/11/1988;test2;6\n"
				+ "15/11/1988;test4;8\n"
				+ "16/11/1988;9;10\n"
				+ "17/11/1988;label1;12\n"
				+ "18/11/1988;label3;14\n"
				+ "19/11/1988;label5;16\n"
				+ "20/11/1988;17;18\n";

		IndexedMapModel model = new IndexedMapModel();
		// Column 0 map to dimension 0 (format date)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeDimension column0 = new DateTimeDimension(0, simpleDateFormat);
		model.setMapping(0,column0);
		// Column 1 map to dimension 1 (format string)
		Dimension column1 = new Dimension(1, "java.lang.String");
		model.setMapping(1, column1);
		// Column 2 map to dimension 2 (format integer)
		Dimension column3 = new Dimension(2, "java.lang.Integer");
		model.setMapping(2, column3);
		
		
		byte[] bytes;
		try {
			bytes = CSVData.getBytes("UTF-8");
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			CSVDataReader dataReader = new CSVDataReader(stream, model, ";");
			try {
				Vector vecteur1 = dataReader.doRead();
				assertEquals(2, vecteur1.get(column3));
				Vector vecteur2 = dataReader.doRead();
				assertEquals(simpleDateFormat.parse("13/11/1988"), vecteur2.get(column0));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("IOException raise");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				fail("ParseException raise");
				e.printStackTrace();
			} catch (DimensionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("DimensionException raise");
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("UnsupportedEncodingException");
		}
	}

}
