package cl.mobilLoyalti.geoLocation.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.bean.Bencinas;
import cl.mobilLoyalti.geoLocation.bean.ServiCentro;
import cl.mobilLoyalti.geoLocation.db.ConnectionDB;
import cl.mobilLoyalti.geoLocation.db.MySQLConnectionDB;

public class PreciosDao extends ConnectionDB {

	private static final String SQL_INSERT = "INSERT INTO precios (precio,fkbencina,fkempresa,fkdireccion,fkregion,fecha_actualizacion) values (?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE bencineras.precios SET fklatitud = ?,fklongitud=? WHERE fkempresa = ? and fkdireccion = ? and fkregion = ?";
	private static Logger log = Logger.getLogger(PreciosDao.class);

	public void insert(Bencinas precios) {

		Connection conn = MySQLConnectionDB.getInstance().createConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_INSERT);

			ps.setFloat(1, precios.getPrecios());
			ps.setString(2, precios.getDescripcion());
			ps.setString(3, precios.getServiCentro().getEmpresa());
			ps.setString(4, precios.getServiCentro().getDireccion());
			ps.setString(5, precios.getServiCentro().getRegion().getNombre());
			ps.setTimestamp(6, precios.getFechaUlmtimaModificacion());

			ps.execute();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			close(ps, conn);
		}

	}

	/**
	 * 
	 * @param nextBencina
	 * @param nextSc
	 * @param region
	 */
	public void update(ServiCentro sc) {

		Connection conn = MySQLConnectionDB.getInstance().createConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_UPDATE);

			if (Float.valueOf(sc.getGeoRef().getLatitud()).isNaN()) {
				ps.setFloat(1, 0);
			} else {
				ps.setFloat(1, sc.getGeoRef().getLatitud());
			}

			if (Float.valueOf(sc.getGeoRef().getLongitud()).isNaN()) {
				ps.setFloat(2, 0);

			} else {
				ps.setFloat(2, sc.getGeoRef().getLongitud());
			}

			ps.setString(3, sc.getEmpresa());
			ps.setString(4, sc.getDireccion());
			ps.setString(5, sc.getRegion().getNombre());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
	}

}
