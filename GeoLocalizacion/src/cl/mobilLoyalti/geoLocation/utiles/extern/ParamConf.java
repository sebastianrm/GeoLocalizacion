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

	public ParamConf() {
		if (props == null) {
			props = new Properties();
		}
		try {

			FileInputStream fileInputStream = new FileInputStream(
					"./config/conf.properties");

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

	public String DB_CONNECTION_MYSQL_IP = getProps("DB_CONNECTION_MYSQL_IP");
	public String DB_CONNECTION_MYSQL_USER = getProps("DB_CONNECTION_MYSQL_USER");
	public String DB_CONNECTION_MYSQL_PASS = getProps("DB_CONNECTION_MYSQL_PASS");
	public String DB_CONNECTION_MYSQL_PORT = getProps("DB_CONNECTION_MYSQL_PORT");
	public String DB_CONNECTION_MYSQL_SCHEMA = getProps("DB_CONNECTION_MYSQL_SCHEMA");


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
					"./config/conf.properties");

			props.load(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger(ParamConf.class).error(e);
		} catch (IOException e) {
			Logger.getLogger(ParamConf.class).error(e);
		}
	}
}