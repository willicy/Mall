package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DBUtils;
import dao.OrderDao;
import model.Order;
import model.OrderItem;
import model.vo.OrderVO;

/**
 * 
 * order持久层实体类
 *
 */
public class OrderDaoImpl implements OrderDao {
	






	@Override
	public Integer insertOrder(Order order) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "INSERT INTO orders ("
					+ "uid, recv_name, recv_phone,"
					+ "recv_district, recv_address,"
					+ " pay, order_time, status"
					+ ") VALUES ("
					+ "?,?,?,?,?,?,?,?)";
			
			stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    stat.setInt(1, order.getUid());
			stat.setString(2, order.getRecvName());
			stat.setString(3, order.getRecvPhone());
			stat.setString(4, order.getRecvDistrict());
			stat.setString(5, order.getRecvAddress());
			stat.setLong(6, order.getPay());
			stat.setDate(7, order.getOrderTime());
			stat.setInt(8, order.getStatus());

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
	public Integer insertOrderItem(OrderItem orderItem) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		int rs;
		
		try {
			conn = DBUtils.getConn();
			String sql = "INSERT INTO order_item ("
					+ "oid, goods_id, goods_image,"
					+ "goods_title,goods_price,"
					+ " goods_count) VALUES ("
					+ "?,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, orderItem.getOid());
			stat.setLong(2, orderItem.getGoodsId());
			stat.setString(3, orderItem.getGoodsImage());
			stat.setString(4, orderItem.getGoodsTitle());
			stat.setLong(5, orderItem.getGoodsPrice());
			stat.setInt(6, orderItem.getGoodsCount());

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
	public OrderVO findById(Integer id) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		OrderVO orderVO = new OrderVO();
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		
		
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "orders.id,uid, recv_name,"
					+ " recv_phone,recv_district,"
					+ " recv_address, pay, status,order_time,"
					+ "goods_id,goods_image, goods_title,"
					+ "goods_price, goods_count "
					+ "FROM orders INNER JOIN order_item "
					+ "ON orders.id=order_item.oid "
					+ "WHERE orders.id=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				
				if(orderVO.getId()==null){
				orderVO.setId(rs.getInt("orders.id"));
				orderVO.setUid(rs.getInt("uid"));
				orderVO.setOrderDate(rs.getDate("order_time"));
				orderVO.setRecvName(rs.getString("recv_name"));
				orderVO.setRecvPhone(rs.getString("recv_phone"));
				orderVO.setRecvAddress(rs.getString("recv_address"));
				orderVO.setRecvDistrict(rs.getString("recv_district"));
				orderVO.setPay(rs.getLong("pay"));
				orderVO.setStatus(rs.getInt("status"));
				}
				OrderItem orderItem = new OrderItem();
				orderItem.setGoodsId(rs.getLong("goods_id"));
				orderItem.setGoodsImage(rs.getString("goods_image"));
				orderItem.setGoodsTitle(rs.getString("goods_title"));
				orderItem.setGoodsPrice(rs.getLong("goods_price"));
				orderItem.setGoodsCount(rs.getInt("goods_count"));
				
				orderList.add(orderItem);
			}
			orderVO.setItems(orderList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return orderVO;
	}




	@Override
	public List<Integer> findByUid(Integer id) throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		List<Integer> orderList = new ArrayList<Integer>();
		
		
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "id FROM orders WHERE uid=?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			rs = stat.executeQuery();
			
			while(rs.next()) {
				orderList.add(rs.getInt("id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return orderList;
	}




	
	
	
	
	
	
	
	
	
	
	
}
