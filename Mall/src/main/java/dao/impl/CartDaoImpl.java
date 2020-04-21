package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.CartDao;
import dao.DBUtils;
import model.Cart;
import model.vo.CartVO;

/**
 * 
 * cart持久层实体类
 *
 */
public class CartDaoImpl implements CartDao {
	



	@Override
	public Integer addnew(Cart cart) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "INSERT INTO cart ("
					+ "uid, gid,price, count,buy"
					+ ") VALUES ("
					+ "?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, cart.getUid());
			stat.setLong(2, cart.getGid());
			stat.setLong(3, cart.getPrice());
			stat.setInt(4, cart.getCount());
			if(cart.getBuy()!=null){
				stat.setInt(5, cart.getBuy());
			}else{
				stat.setInt(5, 0);
			}
			int affectedRows = stat.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
			rs = stat.getGeneratedKeys();
			
		        if (rs.next()) {
		        	 return rs.getInt(1);
		        } else {
		            throw new SQLException("Creating user failed, no generated key obtained.");
		        }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, null);
		}
		
	}


	@Override
	public Integer deleteById(Integer id) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "DELETE FROM cart "
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
	public Integer deleteByBuy() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "DELETE FROM cart "
					+ "WHERE buy=1";
			stat = conn.prepareStatement(sql);
			
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
	public Integer updateCount(Integer id, Integer count) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		int rs;

		try {
			conn = DBUtils.getConn();
			String sql = "UPDATE cart"
					+ " SET count=? "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, count);
			stat.setInt(2, id);
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
	public Cart findByUidAndGid(Integer uid, Long goodsId) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		Cart cart=null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "id,count "
					+ " FROM cart "
					+ "WHERE uid=? AND gid=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, uid);
			stat.setLong(2, goodsId);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				cart = new Cart();
				cart.setId(
						rs.getInt("id"));
				cart.setCount(
						rs.getInt("count"));				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return cart;
	}




	@Override
	public Cart findById(Integer id) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		Cart cart=null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "uid,count"
					+ " FROM cart "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				cart = new Cart();
				cart.setUid(
						rs.getInt("uid"));
				cart.setCount(
						rs.getInt("count"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return cart;
	}




	@Override
	public List<CartVO> findByIds(Integer[] ids) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<CartVO> cartVOList = new ArrayList<CartVO>();
		String idst =""; 
		for (Integer i : ids) {
			idst+=i+",";
		}
		idst=idst.substring(0,idst.length()-1);
		
		try {
			
			conn = DBUtils.getConn();
			String sql = "SELECT cart.id,uid, gid, cart.price, " 
					+ "count, goods.price,title, image "
					+ "FROM cart INNER JOIN goods "
					+ "ON cart.gid = goods.id WHERE cart.id IN("
					+ idst
					+ ") ORDER BY cart.id DESC";
			stat = conn.prepareStatement(sql);
			
			rs = stat.executeQuery();
			while(rs.next()){
				CartVO a=new CartVO();
				a.setId(rs.getInt("cart.id"));
				a.setUid(rs.getInt("uid"));
				a.setGid(rs.getLong("gid"));
				a.setOldPrice(rs.getLong("cart.price"));
				a.setCount(rs.getInt("count"));
				a.setNewPrice(rs.getLong("goods.price"));
				a.setTitle(rs.getString("title"));
				a.setImage(rs.getString("image"));
                   
				cartVOList.add(a);
                }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return cartVOList;
	}



	@Override
	public List<CartVO> findByUid(Integer uid) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<CartVO> cartVOList = new ArrayList<CartVO>();
		try {
	
			conn = DBUtils.getConn();
			String sql = "SELECT cart.id,uid, gid, cart.price, " 
					+ "count, goods.price,title, image "
					+ "FROM cart INNER JOIN goods "
					+ "ON cart.gid = goods.id WHERE uid=? "
					+ "ORDER BY id DESC";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, uid);
			
			rs = stat.executeQuery();
			while(rs.next()){
				CartVO a=new CartVO();
				a.setId(rs.getInt("cart.id"));
				a.setUid(rs.getInt("uid"));
				a.setGid(rs.getLong("gid"));
				a.setOldPrice(rs.getLong("cart.price"));
				a.setCount(rs.getInt("count"));
				a.setNewPrice(rs.getLong("goods.price"));
				a.setTitle(rs.getString("title"));
				a.setImage(rs.getString("image"));
                   
				cartVOList.add(a);
                }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return cartVOList;
	}




	




	
	
	
	
	
	
	
	
	
	
	
}
