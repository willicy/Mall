package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import dao.DBUtils;
import dao.DistrictDao;


import model.District;


/**
 * 
 * district持久层实体类
 *
 */
public class DistrictDaoImpl implements DistrictDao {
	
	@Override
	public List<District> findByParent(String parent) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<District> districtList = new ArrayList<District>();
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT id, parent, code, name " 
					+ "FROM t_dict_district WHERE parent=? "
					+ "ORDER BY code";
			stat = conn.prepareStatement(sql);
			stat.setString(1, parent);
			
			rs = stat.executeQuery();
			while(rs.next()){
				District d=new District();
				d.setId(rs.getInt("id"));
				d.setParent(rs.getString("parent"));
				d.setCode(rs.getString("code"));
				d.setName(rs.getString("name"));
                   
				districtList.add(d);
                }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return districtList;
		
	}

	@Override
	public District findByCode(String code) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		District dis=null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT id, parent, code, name " 
					+ "FROM t_dict_district WHERE code=? ";
			stat = conn.prepareStatement(sql);
			stat.setString(1, code);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				dis = new District();
				dis.setId(rs.getInt("id"));
				dis.setParent(rs.getString("parent"));
				dis.setCode(rs.getString("code"));
				dis.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return dis;
	}



	
}
