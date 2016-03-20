package info.kapable.tools;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
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
import info.kapable.tools.DataWriter.TwigDataWriter;
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
public class linearizeToTwigTest 
    extends TestCase
{
  	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public linearizeToTwigTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( linearizeToTwigTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {	
    	TwigDataWriter output = null;
	
    	try {
			/**
			 * Build the context
			 */
			ApplicationContext context = new FileSystemXmlApplicationContext("example" + File.separator + "linearizeToTwig" + File.separator + "linearizeToTwig_context.xml");
			output = (TwigDataWriter) context.getBean("output");
			StringWriter outputWriter = new StringWriter();
			output.setOutput(outputWriter);
			/**
			 * Processing output to build data
			 */
			output.process();
			output.close();
			outputWriter.flush();
			assertTrue(outputWriter.toString().contains("obj1->desc:Un premier objet"));
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		fail("Exception raise");
    	}
		assertTrue(true);
    }
}
