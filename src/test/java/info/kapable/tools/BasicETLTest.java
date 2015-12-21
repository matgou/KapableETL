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
import info.kapable.tools.DataWriter.JsonDataWriter;
import info.kapable.tools.MappingModel.IndexedMapModel;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.DateTimeDimention;
import info.kapable.tools.pojo.Dimention;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BasicETLTest 
    extends TestCase
{
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
     * Rigourous Test :-)
     */
    public void testApp()
    {
		String CSVData=  "12/11/1988;1;2\n"
				+ "13/11/1988;test;4\n"
				+ "14/11/1988;test2;6\n"
				+ "15/11/1988;test4;8\n"
				+ "16/11/1988;9;10\n"
				+ "17/11/1988;label1;12\n"
				+ "18/11/1988;label3;14\n"
				+ "19/11/1988;label5;16\n"
				+ "20/11/1988;17;18\n";

		NamedMapModel model = new NamedMapModel();
		// Column 0 map to dimention 0 (format date)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeDimention column0 = new DateTimeDimention(0, simpleDateFormat);
		model.setMapping(0,column0, "date");
		// Column 1 map to dimention 1 (format string)
		Dimention column1 = new Dimention(1, "java.lang.String");
		model.setMapping(1, column1, "label");
		// Column 2 map to dimention 2 (format integer)
		Dimention column3 = new Dimention(2, "java.lang.Integer");
		model.setMapping(2, column3, "value");
		
		
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
}
