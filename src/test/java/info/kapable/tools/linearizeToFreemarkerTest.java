package info.kapable.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import info.kapable.tools.DataWriter.FreemarkerDataWriter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class linearizeToFreemarkerTest 
    extends TestCase
{
  	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public linearizeToFreemarkerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( linearizeToFreemarkerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {	
    	FreemarkerDataWriter output = null;
	
    	try {
			/**
			 * Build the context
			 */
			ApplicationContext context = new FileSystemXmlApplicationContext("example" + File.separator + "linearizeToFreemarker" + File.separator + "linearizeToFreemarker_context.xml");
			output = (FreemarkerDataWriter) context.getBean("output");
			output.setOutput(outContent);
			/**
			 * Processing output to build data
			 */
			output.process();
			output.close();
			assertTrue(outContent.toString().contains("obj1->desc:Un premier objet"));
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		fail("Exception raise");
    	}
		assertTrue(true);
    }
}
