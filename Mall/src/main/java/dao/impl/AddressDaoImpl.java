package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.AddressDao;
import dao.DBUtils;
import model.Address;

/**
 * 
 * address持久层实体类
 *
 */
public class AddressDaoImpl implements AddressDao {
	
	public Integer addnew(Address address) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
	
		try {
			conn = DBUtils.getConn();
			String sql = "INSERT INTO address ("
					+ "uid, name,province, city,"
					+ "area, district,zip, address,"
					+ "phone, tag, is_default"
					+ ") VALUES ("
					+ "?,?,?,?,?,?,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, address.getUid());
			stat.setString(2, address.getName());
			stat.setString(3, address.getProvince());
			stat.setString(4, address.getCity());
			stat.setString(5, address.getArea());
			stat.setString(6, address.getDistrict());
			stat.setString(7, address.getZip());
			stat.setString(8, address.getAddress());
			stat.setString(9, address.getPhone());
			stat.setString(10, address.getTag());
			stat.setInt(11, address.getIsDefault());
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}


	@Override
	public Integer deleteById(Integer id)  throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "DELETE FROM address "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}

	@Override
	public Integer updateNonDefault(Integer uid) throws Exception {
		
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE address "
					+ "SET is_default=0 "
					+ "WHERE uid=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, uid);
			
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}

	@Override
	public Integer updateDefault(Integer id)  throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE address "
					+ "SET is_default=1 "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}

	
	
	@Override
	public Integer updateInfo(Address address ,Integer id)  throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
		
			conn = DBUtils.getConn();
			String sql = "UPDATE address "
					+ "SET name=?,province=?,city=?, "
					+ "area=?,district=?,zip=?, address=?,"
					+ "phone=?, tag=? "
					+ "WHERE id=?";
			//district
			stat = conn.prepareStatement(sql);
			stat.setString(1, address.getName());
			stat.setString(2, address.getProvince());
			stat.setString(3, address.getCity());
			stat.setString(4, address.getArea());
			stat.setString(5, address.getDistrict());
			stat.setString(6, address.getZip());
			stat.setString(7, address.getAddress());
			stat.setString(8, address.getPhone());
			stat.setString(9, address.getTag());
			stat.setInt(10, id);
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}
	@Override
	public Address findById(Integer id)  throws Exception {
	
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Address address = new Address();
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT uid ,name,phone, "
					+ "district,province,city,area,"
					+ "tag,zip,address,is_default " 
					+ "FROM address WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			if(rs.next()){
				
				address.setUid(rs.getInt("uid"));
				address.setName(rs.getString("name"));
				address.setPhone(rs.getString("phone"));
				address.setDistrict(rs.getString("district"));
				address.setProvince(rs.getString("province"));
				address.setCity(rs.getString("city"));
				address.setArea(rs.getString("area"));
				address.setTag(rs.getString("tag"));
				address.setZip(rs.getString("zip"));
				address.setAddress(rs.getString("address"));
				address.setIsDefault(rs.getInt("is_default"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return address;
	}

	@Override
	public Integer getCountByUid(Integer uid) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		Integer rss = null ;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT COUNT(id) " 
					+ "FROM address WHERE uid=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, uid);
			rs = stat.executeQuery();
			if(rs.next()){
				rss=rs.getInt("COUNT(id)");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return rss;
	}

	@Override
	public List<Address> findByUid(Integer uid) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Address> addressList = new ArrayList<Address>();
		try {
	
			conn = DBUtils.getConn();
			String sql = "SELECT id,name,phone, district, " 
					+ "address, tag,is_default "
					+ "FROM address WHERE uid=? "
					+ "ORDER BY is_default DESC";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, uid);
			
			rs = stat.executeQuery();
			while(rs.next()){
				Address a=new Address();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setPhone(rs.getString("phone"));
				a.setDistrict(rs.getString("district"));
				a.setAddress(rs.getString("address"));
				a.setTag(rs.getString("tag"));
				a.setIsDefault(rs.getInt("is_default"));
                   
				addressList.add(a);
                }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return addressList;
	}

	


	
}
