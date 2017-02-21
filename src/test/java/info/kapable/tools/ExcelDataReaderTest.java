package info.kapable.tools;

import info.kapable.tools.DataWriter.AbstractDataWriter;

import java.io.File;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ExcelDataReaderTest extends TestCase {

	public void testApp() {
		AbstractDataWriter output = null;

		try {
			/**
			 * Build the context
			 */
			ApplicationContext context = new FileSystemXmlApplicationContext(
					"example" + File.separator + "ExcelCourrier"
							+ File.separator + "context-etl.xml");
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
