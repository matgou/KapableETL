package info.kapable.tools;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import info.kapable.tools.DataWriter.JsonDataWriter;
import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.DateTimeDimension;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class JsonDataWriterTest {

	@Test
	public void test() {
		NamedMapModel model = new NamedMapModel();
		// Column 0 map to dimention 0 (format date)
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeDimension column0 = new DateTimeDimension(0, simpleDateFormat);
		model.setMapping(0, column0, "Date");
		// Column 1 map to dimention 1 (format string)
		Dimension column1 = new Dimension(1, "java.lang.String");
		model.setMapping(1, column1, "titre");
		// Column 2 map to dimention 2 (format integer)
		Dimension column3 = new Dimension(2, "java.lang.Integer");
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
		} catch (DimensionException e) {
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
