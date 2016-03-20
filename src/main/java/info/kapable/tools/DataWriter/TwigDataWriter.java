package info.kapable.tools.DataWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

import info.kapable.tools.pojo.Vector;

public class TwigDataWriter extends AbstractDataWriter {

	private String templatePath;
	private List<Vector> data;
	private BufferedWriter output;
	
	public TwigDataWriter(OutputStream output, String templatePath)
	{
		super();
		this.templatePath = templatePath;
		this.output = new BufferedWriter(new OutputStreamWriter(output));
		this.data = new ArrayList<Vector>();
	}

	@Override
	public void write(Vector vector) {
		this.data.add(vector);
	}

	@Override
	public void close() {
        JtwigTemplate template = new JtwigTemplate(new File(this.templatePath), new JtwigConfiguration());
        try {
			String output_compiled = template.output(new JtwigModelMap().withModelAttribute("data", this.data));

			try {
				output.write(output_compiled);
				this.output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
