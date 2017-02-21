package info.kapable.tools;

import info.kapable.tools.DataWriter.AbstractDataWriter;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class PopulationFrancaiseETLTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PopulationFrancaiseETLTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PopulationFrancaiseETLTest.class );
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
			ApplicationContext context = new FileSystemXmlApplicationContext("example" + File.separator + "Population_France" + File.separator + "PopulationFrance_context.xml");
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
