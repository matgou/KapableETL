package info.kapable.tools.DataWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import info.kapable.tools.Exception.DimensionException;
import info.kapable.tools.MappingModel.IndexedMapModel;
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

	private IndexedMapModel model;

	/**
	 * Initialize Connection
	 */
	private void initConnection() {
		if (this.rs == null) {
			conn = null;
			Statement stmt = null;

			try {
				// STEP 2: Register JDBC driver
				Class.forName(this.JDBCDriver);

				// STEP 3: Open a connection
				conn = DriverManager.getConnection(this.dbURL, this.dbUsername, this.dbPassword);

				conn.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public JDBCDataWriter(IndexedMapModel model) {
		super();
		this.model = model;
	}

	@Override
	public void write(Vector vector) {
		// TODO Prepare stm
		if (this.conn == null) {
			this.initConnection();
		}

		// STEP 4: Execute a query
		try {
	        PreparedStatement stmt = this.conn.prepareStatement(sql);
			for (Dimension dim : this.model.getDimentions()) {
				String string;
				try {
					string = dim.getStringFromVal(vector.get(dim));
					// TODO query.put(colName, string);
					stmt.setString(this.model.getKeyFor(dim), string);
				} catch (DimensionException e) {
					e.printStackTrace();
					return;
				}
			}
			stmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void close() {
		try {
			this.conn.commit();
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

	public IndexedMapModel getModel() {
		return model;
	}

	public void setModel(IndexedMapModel model) {
		this.model = model;
	}

}
