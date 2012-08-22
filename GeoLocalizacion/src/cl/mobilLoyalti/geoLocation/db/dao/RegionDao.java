package cl.mobilLoyalti.geoLocation.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cl.mobilLoyalti.geoLocation.bean.Region;
import cl.mobilLoyalti.geoLocation.db.ConnectionDB;
import cl.mobilLoyalti.geoLocation.db.MySQLConnectionDB;

public class RegionDao extends ConnectionDB{

	private static final String SQL_INSERT = "INSERT INTO regiones (nombre) values (?)";
	private static Logger log = Logger.getLogger(RegionDao.class);
	
	public void insert (Region region){
		
		Connection conn = MySQLConnectionDB.getInstance().createConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_INSERT);

		ps.setString(1, region.getNombre());
		
		ps.execute();
		} catch (SQLException e) {
			log.error(e);
		}finally{
			close(ps,conn);
		}
		
	}
	
	
}
