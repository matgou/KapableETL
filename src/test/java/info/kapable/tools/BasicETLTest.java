package info.kapable.tools;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import info.kapable.tools.DataReader.AbstractDataReader;
import info.kapable.tools.DataReader.CSVDataReader;
import info.kapable.tools.DataTransform.StaticValueSetter;
import info.kapable.tools.DataWriter.JsonDataWriter;
import info.kapable.tools.MappingModel.IndexedMapModel;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.DateTimeDimension;
import info.kapable.tools.pojo.Dimension;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BasicETLTest 
    extends TestCase
{
	String CSVData;
	NamedMapModel model, modelWriting;
	DateTimeDimension column0;
	Dimension column1, column2, column3;
	
	protected void setUp() throws Exception {
		super.setUp();

		CSVData=  "12/11/1988;1;2\n"
				+ "13/11/1988;test;4\n"
				+ "14/11/1988;test2;6\n"
				+ "15/11/1988;test4;8\n"
				+ "16/11/1988;9;10\n"
				+ "17/11/1988;label1;12\n"
				+ "18/11/1988;label3;14\n"
				+ "19/11/1988;label5;16\n"
				+ "20/11/1988;17;18\n";
		

		// Column 0 map to dimension 0 (format date)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeDimension column0 = new DateTimeDimension(0, simpleDateFormat);
		// Column 1 map to dimension 1 (format string)
		column1 = new Dimension(1, "java.lang.String");
		// Column 2 map to dimension 2 (format integer)
		column2 = new Dimension(2, "java.lang.Integer");

		// Static column
		column3 = new Dimension(3, "java.lang.String");
		
		// Model for reading data
		model = new NamedMapModel();
		model.setMapping(0,column0, "date");
		model.setMapping(1, column1, "label");
		model.setMapping(2, column2, "value");
		
		// Model for writing data
		modelWriting = new NamedMapModel();
		modelWriting.setMapping(0,column0, "date");
		modelWriting.setMapping(1, column1, "label");
		modelWriting.setMapping(3, column3, "staticLabel");
		modelWriting.setMapping(2, column2, "value");
		
		
	}
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BasicETLTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BasicETLTest.class );
    }

    /**
     * Test 
     */
    public void testApp()
    {
		byte[] bytes;
		try {
			bytes = CSVData.getBytes("UTF-8");
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			CSVDataReader dataReader = new CSVDataReader(stream, model, ";");
			JsonDataWriter dataWriter = new JsonDataWriter(System.out, model);
			List<AbstractDataReader> input = new ArrayList<AbstractDataReader>();
			input.add(dataReader);
			dataWriter.setInput(input);
			dataWriter.process();
			dataWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException raise");
		}
    }

    public void testAddSimpleValue()
    {
		byte[] bytes;
		try {
			bytes = CSVData.getBytes("UTF-8");
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			
			/* READER */
			CSVDataReader dataReader = new CSVDataReader(stream, model, ";");
			List<AbstractDataReader> input = new ArrayList<AbstractDataReader>();
			input.add(dataReader);
			
			/* Add static value */
			StaticValueSetter setter = new StaticValueSetter();
			setter.setDimensionResult(column3);
			setter.setStaticValue("Ceci est un texte static");
			setter.setInput(input);
			
			List<AbstractDataReader> inputJson = new ArrayList<AbstractDataReader>();
			inputJson.add(setter);
			
			/* WRITER */
			JsonDataWriter dataWriter = new JsonDataWriter(System.out, modelWriting);
			input.add(dataReader);
			dataWriter.setInput(inputJson);
			dataWriter.process();
			dataWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("IOException raise");
		}
    }
}
