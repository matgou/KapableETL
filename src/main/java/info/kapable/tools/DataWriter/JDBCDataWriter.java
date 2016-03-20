package info.kapable.tools.DataWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class JDBCDataWriter extends AbstractDataWriter {

	private String JDBCDriver = "";  
	private String dbURL = "";
	private String sql = "";
	private ResultSet rs;
	

	private NamedMapModel dataModel;
	private String dbPassword;
	private String dbUsername;
	
	private Connection conn;
	
	private NamedMapModel model;
	

	/**
	 * Initialize Connection
	 */
	private void initConnection()
	{
		if(this.rs == null)
		{
			conn = null;
			Statement stmt = null;

			try {
				//STEP 2: Register JDBC driver
				Class.forName(this.JDBCDriver);

				//STEP 3: Open a connection
				conn = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);
				//STEP 4: Execute a query
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public JDBCDataWriter(OutputStream output, NamedMapModel model)
	{
		super();
		this.model = model;
	}
	@Override
	public void write(Vector vector) {
		// TODO Prepare stm
		
		for(Dimension dim: this.model.getDimentions())
		{
			String string;
			try {
				string = dim.getStringFromVal(vector.get(dim));
				String colName = model.getName(dim);
				// TODO query.put(colName, string);
			} catch (DimensionException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
