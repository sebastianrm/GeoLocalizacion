/**
 * 
 */
package cl.mobilLoyalti.geoLocation.utiles.extern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @@author Sebastian Retamal
 * 
 */
public class ParamConf {

	private static final String CONF_CONF_PROPERTIES = "./conf/conf.properties";

	public ParamConf() {
		if (props == null) {
			props = new Properties();
		}
		try {

			FileInputStream fileInputStream = new FileInputStream(
					CONF_CONF_PROPERTIES);

			props.load(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger(ParamConf.class).error(e);
		} catch (IOException e) {
			Logger.getLogger(ParamConf.class).error(e);
		}
	}

	private static Properties props;
	public String VERSION = getProps("VERSION");
	public String FECHA_DEPLOY = getProps("FECHA_DEPLOY");

	public String JDBC_DRIVER = getProps("JDBC_DRIVER");
	public String DB_CONNECTION_IP = getProps("DB_CONNECTION_IP");
	public String DB_CONNECTION_USER = getProps("DB_CONNECTION_USER");
	public String DB_CONNECTION_PASS = getProps("DB_CONNECTION_PASS");
	public String DB_CONNECTION_PORT = getProps("DB_CONNECTION_PORT");
	public String DB_CONNECTION_SCHEMA = getProps("DB_CONNECTION_SCHEMA");

	/**
	 * 
	 * @@param prop
	 * @@return
	 */
	private static String getProps(String prop) {

		if (props == null) {
			crearPropiedades();
		}
		return props.getProperty(prop);
	}

	private static void crearPropiedades() {

		if (props == null) {
			props = new Properties();
		}
		try {

			FileInputStream fileInputStream = new FileInputStream(
					CONF_CONF_PROPERTIES);

			props.load(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			Logger logger = Logger.getLogger(ParamConf.class);
			logger.error(e);
		} catch (IOException e) {
			Logger logger = Logger.getLogger(ParamConf.class);
			logger.error(e);
		}
	}
}