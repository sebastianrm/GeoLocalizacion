/**
 * 
 */
package cl.mobilLoyalti.geoLocation.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.utiles.extern.ParamConf;

/**
 * @author Sebastian Retamal
 * 
 */
public class PoolingDataSource {

	private Connection conn;
	private static PoolingDataSource connectionDB;
	private static Logger log = Logger.getLogger(PoolingDataSource.class);

	public static synchronized PoolingDataSource getInstance() {
		if (connectionDB == null) {
			connectionDB = new PoolingDataSource();
		}
		return connectionDB;
	}

	public Connection getConnection() {
		if (this.conn == null) {
			this.conn = createConnection();
		}
		return this.conn;
	}

	public Connection createConnection() {
		ParamConf paramConf = new ParamConf();

		//
		// First we load the underlying JDBC driver.
		// You need this if you don't use the jdbc.drivers
		// system property.
		//
		System.out.println("Loading underlying JDBC driver.");
		try {
			Class.forName(paramConf.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");

		//
		// Then, we set up the PoolingDataSource.
		// Normally this would be handled auto-magically by
		// an external configuration, but in this example we'll
		// do it manually.
		//
		System.out.println("Setting up data source.");
		ConnectionFactory setupDataSource = setupDataSource("jdbc:sqlserver://"
				+ paramConf.DB_CONNECTION_IP + ":"
				+ paramConf.DB_CONNECTION_PORT + ";databaseName="
				+ paramConf.DB_CONNECTION_SCHEMA + ";");

		try {
			return setupDataSource.createConnection();
		} catch (SQLException e) {
			log.error(e);
		}
		return conn;

	}

	public static ConnectionFactory setupDataSource(String connectURI) {
		ParamConf paramConf = new ParamConf();
		//
		// First, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string passed in the command line
		// arguments.
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, paramConf.DB_CONNECTION_USER,
				paramConf.DB_CONNECTION_PASS);

		//
		// Next we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		// PoolableConnectionFactory poolableConnectionFactory = new
		// PoolableConnectionFactory(connectionFactory, null, null, connectURI,
		// false, false);

		//
		// Now we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		// ObjectPool connectionPool = new
		// GenericObjectPool(poolableConnectionFactory);

		//
		// Finally, we create the PoolingDriver itself,
		// passing in the object pool we created.
		//
		// PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

		return connectionFactory;
	}
}
