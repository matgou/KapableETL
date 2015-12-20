package info.kapable.tools;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import info.kapable.tools.DataWriter.JsonDataWriter;
import info.kapable.tools.Exception.DimentionException;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.DateTimeDimention;
import info.kapable.tools.pojo.Dimention;
import info.kapable.tools.pojo.Vector;

public class JsonDataWriterTest {

	@Test
	public void test() {
		NamedMapModel model = new NamedMapModel();
		// Column 0 map to dimention 0 (format date)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeDimention column0 = new DateTimeDimention(0, simpleDateFormat);
		model.setMapping(0, column0, "Date");
		// Column 1 map to dimention 1 (format string)
		Dimention column1 = new Dimention(1, "java.lang.String");
		model.setMapping(1, column1, "titre");
		// Column 2 map to dimention 2 (format integer)
		Dimention column3 = new Dimention(2, "java.lang.Integer");
		model.setMapping(2, column3, "valeur");
		
		OutputStream output = new ByteArrayOutputStream();
		JsonDataWriter writer = new JsonDataWriter(output, model);
		
		try {
			Vector vector = model.newVector();
			vector.set(column0, simpleDateFormat.parse("12/11/1988"));
			vector.set(column1, "Mathieu");
			vector.set(column3, 15);
			

			Vector vector2 = model.newVector();
			vector2.set(column0, simpleDateFormat.parse("14/07/1989"));
			vector2.set(column1, "Johanna");
			vector2.set(column3, 15);
			
			writer.write(vector);
			writer.write(vector2);
			writer.close();
			
			assertTrue(output.toString().contains("Mathieu"));
			assertTrue(output.toString().contains("Johanna"));
		} catch (DimentionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("DimentionException raise");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ParseException raise");
		}
	}

}
