package info.kapable.tools.DataReader;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import info.kapable.tools.Exception.NotCompatibleMappingException;
import info.kapable.tools.MappingModel.AbstractModel;
import info.kapable.tools.MappingModel.NamedMapModel;
import info.kapable.tools.pojo.Dimension;
import info.kapable.tools.pojo.Vector;

public class JDBCDataReader extends AbstractDataReader {

	private String JDBCDriver = "";  
	private String dbURL = "";
	private String sql = "";
	private ResultSet rs;
	

	private NamedMapModel dataModel;
	private String dbPassword;
	private String dbUsername;
	  
	public void setModel(AbstractModel model) throws NotCompatibleMappingException {
		if(!NamedMapModel.class.isInstance(model))
		{
			throw new NotCompatibleMappingException();
		}
		this.dataModel = (NamedMapModel) model;
	}
	/**
	 * Initialize Connection
	 */
	private void initConnection()
	{
		if(this.rs == null)
		{
			Connection conn = null;
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
	
	@Override
	public Vector doRead() throws IOException {
		Vector vector = new Vector();
		this.initConnection();
		try {
			if(rs.next())
			{
				for(Dimension dim: this.dataModel.getDimentions())
				{
					//Object resultVal = rs.getObject(this.dataModel.getName(dim)), Class.forName(dim.getType()));
					Object resultVal = rs.getObject(this.dataModel.getName(dim));
					if(resultVal instanceof java.math.BigDecimal) {
						java.math.BigDecimal d = (BigDecimal) resultVal;
						resultVal = d.doubleValue();
					}
					vector.addDimension(dim, resultVal);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return vector only if vector contains value
		if(vector.hasDimension())
			return vector;
		return null;
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
	

}
