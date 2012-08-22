package cl.mobilLoyalti.geoLocation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.utiles.extern.ParamConf;

public class MySQLConnectionDB extends ConnectionDB {

	private Connection conn;
	private static MySQLConnectionDB connectionDB;
	private static Logger log = Logger.getLogger(MySQLConnectionDB.class);

	/*
	 * Constructor por defecto. Setea inmediatamente los parametros de la base
	 * de datos para su utilización.
	 */
	public MySQLConnectionDB() {
		super();
	}

	/*
	 * Retorna la conexion con la base de datos.
	 * 
	 * @throws DataBaseException Si ocurren errores en la conexion a la base de
	 * datos.
	 */
	public Connection getConnection() {
		if (this.conn == null) {
			this.conn = createConnection();
		}
		return this.conn;
	}

	/*
	 * Crea una conexion con la base de datos y la retorna.
	 * 
	 * @throws DataBaseException Si ocurren errores en la conexion a la base de
	 * datos.
	 */
	public Connection createConnection() {
		ParamConf paramConf = new ParamConf();

		Connection conn = null;
		try {
			Class.forName(paramConf.JDBC_DRIVER);
		} catch (Exception e) {
			log.error(e);
		}
		// String url = "jdbc:mysql://" + paramConf.DB_CONNECTION_MYSQL_IP + ":"
		// + paramConf.DB_CONNECTION_MYSQL_PORT + "/" +
		// paramConf.DB_CONNECTION_MYSQL_SCHEMA;

		String url = "jdbc:sqlserver://" + paramConf.DB_CONNECTION_IP
				+ ":" + paramConf.DB_CONNECTION_PORT + ";databaseName="
				+ paramConf.DB_CONNECTION_SCHEMA + ";";

		try {
			conn = DriverManager.getConnection(url,
					paramConf.DB_CONNECTION_USER,
					paramConf.DB_CONNECTION_PASS);
		} catch (SQLException e) {
			log.error(e);
		}
		return conn;
	}

	/*
	 * Cierra el objeto Connection
	 * 
	 * @param conn Objeto Connection.
	 */
	public void closeConnection() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			} finally {
				this.conn = null;
			}
		}
	}

	public static synchronized MySQLConnectionDB getInstance() {
		if (connectionDB == null) {
			connectionDB = new MySQLConnectionDB();
		}
		return connectionDB;
	}
}