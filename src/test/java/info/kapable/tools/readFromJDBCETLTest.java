package info.kapable.tools;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import info.kapable.tools.DataReader.AbstractDataReader;
import info.kapable.tools.DataReader.CSVDataReader;
import info.kapable.tools.DataWriter.AbstractDataWriter;
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
public class readFromJDBCETLTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public readFromJDBCETLTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( readFromJDBCETLTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {	
    	AbstractDataWriter output = null;
	
    	try {
			/**
			 * Build the context
			 */
			ApplicationContext context = new FileSystemXmlApplicationContext("example" + File.separator + "readFromJDBC" + File.separator + "readFromJDBC.xml");
			output = (AbstractDataWriter) context.getBean("output");

			/**
			 * Processing output to build data
			 */
			output.process();
			output.close();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		fail("Exception raise");
    	}
		assertTrue(true);
    }
}
