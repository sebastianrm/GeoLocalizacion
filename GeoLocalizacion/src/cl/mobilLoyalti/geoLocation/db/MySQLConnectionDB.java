package cl.mobilLoyalti.geoLocation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class MySQLConnectionDB extends ConnectionDB{

	private String user;
	private String password;
	private String ip;
	private String port;
	private String sid;
	private Connection conn;
	private static MySQLConnectionDB connectionDB;
	private static Logger log = Logger.getLogger(MySQLConnectionDB.class);
	/*
	 * Constructor por defecto.
	 * Setea inmediatamente los parametros de la base de datos para su utilización.
	 */	
	public MySQLConnectionDB(){
		this.setUser("root");
		this.setPassword("123456");
		this.setIp("localhost");
		this.setPort("3306");
		this.setSid("bencineras");
	}
	
	/*
	 * Retorna la conexion con la base de datos.
	 * 
	 * @throws DataBaseException Si ocurren errores en la conexion a la base de datos.
	 */
	public Connection getConnection() {
		if(this.conn == null){
			this.conn = createConnection();
		}
		return this.conn;
	}
	
	/*
	 * Crea una conexion con la base de datos y la retorna.
	 * 
	 * @throws DataBaseException Si ocurren errores en la conexion a la base de datos.
	 */	
	public Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://" + getIp() + ":" + getPort() + "/" + getSid();
		try {
			conn = DriverManager.getConnection(url, getUser(), getPassword());
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
	public void closeConnection(){
		if(this.conn != null){
			try {
				this.conn.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage(), e);
			}finally {
				this.conn = null;
			}
		}	
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public static synchronized MySQLConnectionDB getInstance(){
		if(connectionDB == null){
			connectionDB = new MySQLConnectionDB();
		}
		return connectionDB;
	}
}