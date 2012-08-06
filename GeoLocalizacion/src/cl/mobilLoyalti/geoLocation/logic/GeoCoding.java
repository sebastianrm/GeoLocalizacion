/**
 * 
 */
package cl.mobilLoyalti.geoLocation.logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cl.mobilLoyalti.geoLocation.bean.GeoReferencia;

/**
 * @author Sebastian Retamal
 * 
 */
public class GeoCoding {

	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";
	private static Logger log = Logger.getLogger(GeoCoding.class);
	
	public GeoReferencia latLongDirecc(String direccion)
			throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException {

		// prepare a URL to the geocoder
		URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address="
				+ URLEncoder.encode(direccion.trim(), "UTF-8")
				+ "&sensor=false");

		// prepare an HTTP connection to the geocoder
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			// open the connection and get results as InputSource.
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(
					conn.getInputStream());

			// read result and parse into XML Document
			geocoderResultDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		// prepare XPath
		XPath xpath = XPathFactory.newInstance().newXPath();

		// extract the result
		NodeList resultNodeList = null;

		// geoReferencia.setDireccionGoogle("");

		// a) obtain the formatted_address field for every result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result/formatted_address",
				geocoderResultDocument, XPathConstants.NODESET);

		ArrayList<String> arrayList = new ArrayList<String>();

		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			log.info(resultNodeList.item(i).getTextContent());

			// beanLocal.setDireccionGoogle(beanLocal.getDireccionGoogle()+
			// " "+resultNodeList.item(i).getTextContent().trim());
			arrayList.add(resultNodeList.item(i).getTextContent().trim());
		}

		// b) extract the locality for the first result
		resultNodeList = (NodeList) xpath
				.evaluate(
						"/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name",
						geocoderResultDocument, XPathConstants.NODESET);
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			log.info(resultNodeList.item(i).getTextContent());

			// beanLocal.setDireccionGoogle(beanLocal.getDireccionGoogle()+
			// " "+resultNodeList.item(i).getTextContent().trim());
			arrayList.add(resultNodeList.item(i).getTextContent().trim());

		}

		// c) extract the coordinates of the first result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result[1]/geometry/location/*",
				geocoderResultDocument, XPathConstants.NODESET);
		float lat = Float.NaN;
		float lng = Float.NaN;
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if ("lat".equals(node.getNodeName()))
				lat = Float.parseFloat(node.getTextContent());
			if ("lng".equals(node.getNodeName()))
				lng = Float.parseFloat(node.getTextContent());
		}
		log.info("lat/lng=" + lat + "," + lng);

		// c) extract the coordinates of the first result
		resultNodeList = (NodeList) xpath
				.evaluate(
						"/GeocodeResponse/result[1]/address_component[type/text() = 'administrative_area_level_1']/country[short_name/text() = 'US']/*",
						geocoderResultDocument, XPathConstants.NODESET);
		float latitud = Float.NaN;
		float longitud = Float.NaN;
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if ("lat".equals(node.getNodeName()))
				latitud = Float.parseFloat(node.getTextContent());
			if ("lng".equals(node.getNodeName()))
				longitud = Float.parseFloat(node.getTextContent());
		}

		log.info("lat/lng=" + latitud + "," + longitud);

		if (lat == Float.NaN) {
			GeoReferencia geoReferencia2 = new GeoReferencia(latitud, longitud);

			geoReferencia2.setDireccionGoogle(arrayList);
			return geoReferencia2;
		} else {
			GeoReferencia geoReferencia2 = new GeoReferencia(lat, lng);
			geoReferencia2.setDireccionGoogle(arrayList);
			return geoReferencia2;
		}

	}

}
