package cl.mobilLoyalti.geoLocation.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.bean.ServiCentro;
import cl.mobilLoyalti.geoLocation.db.ConnectionDB;
import cl.mobilLoyalti.geoLocation.db.MySQLConnectionDB;

public class GeoReferenciaDao extends ConnectionDB {

	private static final String SQL_INSERT = "INSERT INTO direcciones_google (fkempresa,fkdireccion,fkregion,dirGooogle) values (?,?,?,?)";
	private static Logger log = Logger.getLogger(GeoReferenciaDao.class);
	
	public void insert(ServiCentro sc) {

		Connection conn = MySQLConnectionDB.getInstance().createConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_INSERT);

			ps.setString(1, sc.getEmpresa());
			ps.setString(2, sc.getDireccion());
			ps.setString(3, sc.getRegion().getNombre());
			Iterator<String> iterator = sc.getGeoRef().getDireccionGoogle()
					.iterator();

			while (iterator.hasNext()) {
				String next = iterator.next();
				ps.setString(4, next);
				ps.execute();
			}

		} catch (SQLException e) {
			log.error(e);
		} finally {
			close(ps, conn);
		}

	}

}
