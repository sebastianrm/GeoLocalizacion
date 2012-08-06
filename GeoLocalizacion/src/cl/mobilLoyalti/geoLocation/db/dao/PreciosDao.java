package cl.mobilLoyalti.geoLocation.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.bean.Bencinas;
import cl.mobilLoyalti.geoLocation.db.ConnectionDB;
import cl.mobilLoyalti.geoLocation.db.MySQLConnectionDB;

public class PreciosDao extends ConnectionDB {

	private static final String SQL_INSERT = "INSERT INTO precios (precio,fkbencina,fkempresa,fkdireccion,fkregion,fecha_actualizacion) values (?,?,?,?,?,?)";
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

}
