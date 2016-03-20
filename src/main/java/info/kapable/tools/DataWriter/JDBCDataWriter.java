package info.kapable.tools.DataWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public JDBCDataWriter(NamedMapModel model)
	{
		super();
		this.model = model;
	}

	@Override
	public void write(Vector vector) {
		// TODO Prepare stm
		if(this.conn == null) {
			this.initConnection();
		}
		
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

	public String getJDBCDriver() {
		return JDBCDriver;
	}

	public void setJDBCDriver(String jDBCDriver) {
		JDBCDriver = jDBCDriver;
	}

	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public NamedMapModel getModel() {
		return model;
	}

	public void setModel(NamedMapModel model) {
		this.model = model;
	}

}
