package dao.impl;
import java.sql.*;

import dao.DBUtils;
import dao.UserDao;
import model.User;

/**
 * 
 * user持久层实体类
 *
 */
public class UserDaoImpl implements UserDao {
	
	public Integer addnew(User user) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "INSERT INTO user ("
					+ "username, password,gender,"
					+ "phone, email,avatar, is_delete"
					+ ") VALUES ("
					+ "?,?,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setString(1, user.getUsername());
			stat.setString(2, user.getPassword());
			stat.setInt(3, user.getGender());
			stat.setString(4, user.getPhone());
			stat.setString(5, user.getEmail());
			stat.setString(6, user.getAvatar());
			stat.setInt(7, user.getIsDelete());
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}


	

	public Integer updatePassword(Integer uid, String password) throws Exception {
		
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;

		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE user"
					+ " SET password=? WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setString(1, password);
			stat.setInt(2, uid);
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}

	public Integer updateAvatar(User user) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE user"
					+ " SET avatar=?"
					+ " WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setString(1, user.getAvatar());
			stat.setInt(2, user.getId());
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
	}
	
	public Integer changeInfo(User user) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;

		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE user"
					+ " SET gender=?, phone=?,"
					+ "email=? WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, user.getGender());
			stat.setString(2, user.getPhone());
			stat.setString(3, user.getEmail());
			stat.setInt(4, user.getId());
			rs = stat.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		return rs;
		
	}
	
	public User findByUsername(String username) throws Exception{
	
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		User user=null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "id, username, password,"
					+ "avatar,is_delete "
					+ " FROM user "
					+ "WHERE username=?";
			stat = conn.prepareStatement(sql);
			stat.setString(1, username);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(
						rs.getString("username"));
				user.setPassword(
						rs.getString("password"));
				user.setIsDelete(
						Integer.valueOf(rs.getString("is_delete")));
				
				user.setAvatar(
						rs.getString("avatar"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return user;
	

	}
	
	
	public User findById(Integer id) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		User user=null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "username,gender, phone,"
					+ "email,password, is_delete"
					+ " FROM user "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				user = new User();
				user.setUsername(
						rs.getString("username"));
				user.setPassword(
						rs.getString("password"));
				user.setEmail(
						rs.getString("email"));
				user.setIsDelete(
						rs.getInt("is_delete"));
				user.setPhone(
						rs.getString("phone"));
				user.setGender(
						rs.getInt("gender"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return user;
	}




	
	
	
	
	
	
	
	
	
	
	
}
