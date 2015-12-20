package info.kapable.tools;

import java.io.FileNotFoundException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import info.kapable.tools.DataWriter.AbstractDataWriter;

/**
 * Application ETL
 *
 */
public class App {
	public static void main(String[] args) {
		AbstractDataWriter output = null;
		System.out.println("Using config : " + args[0]);
		try {
			/**
			 * Build the context
			 */
			ApplicationContext context = new FileSystemXmlApplicationContext(args[0]);
			output = (AbstractDataWriter) context.getBean("output");

			/**
			 * Processing output to build data
			 */
			output.process();
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

}
