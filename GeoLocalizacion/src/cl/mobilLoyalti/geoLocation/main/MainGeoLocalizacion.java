/**
 * 
 */
package cl.mobilLoyalti.geoLocation.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import cl.mobilLoyalti.geoLocation.bean.GeoReferencia;
import cl.mobilLoyalti.geoLocation.bean.ServiCentro;
import cl.mobilLoyalti.geoLocation.db.dao.GeoReferenciaDao;
import cl.mobilLoyalti.geoLocation.db.dao.PreciosDao;
import cl.mobilLoyalti.geoLocation.db.dao.ServiCentroDao;
import cl.mobilLoyalti.geoLocation.logic.GeoCoding;

/**
 * @author sebastian
 * 
 */
public class MainGeoLocalizacion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(MainGeoLocalizacion.class);
		
		MainGeoLocalizacion main = new MainGeoLocalizacion();
		ArrayList<ServiCentro> recuperaServicentro = main
				.recuoperaServicentro();

		Iterator<ServiCentro> iterator = recuperaServicentro.iterator();

		while (iterator.hasNext()) {
			ServiCentro next = iterator.next();

			try {
				// next.setDireccion(next.getDireccion().replaceAll("/", ""));
				GeoCoding geo = new GeoCoding();

				GeoReferencia latLongDirecc = geo.latLongDirecc(next
						.getDireccion().replaceAll("/", "")+", Chile");

				ServiCentroDao scDao = new ServiCentroDao();
				PreciosDao precioDao = new PreciosDao();
				
				next.setGeoRef(latLongDirecc);

				scDao.update(next);

				GeoReferenciaDao gro = new GeoReferenciaDao();

				gro.insert(next);
				
				precioDao.update(next);
				
				

			} catch (XPathExpressionException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} catch (ParserConfigurationException e) {
				log.error(e);
			} catch (SAXException e) {
				log.error(e);
			}

		}

	}

	private ArrayList<ServiCentro> recuoperaServicentro() {
		ServiCentroDao serviCentroDao = new ServiCentroDao();
		
//		return serviCentroDao.selectAll();
		
		return serviCentroDao.selectLatLogCero();
	}

}
