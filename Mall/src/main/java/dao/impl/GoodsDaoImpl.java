package dao.impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.DBUtils;
import dao.GoodsDao;
import model.Goods;

/**
 * 
 * goods持久层实体类
 *
 */
public class GoodsDaoImpl implements GoodsDao {

	@Override
	public List<Goods> findByPriority(Integer count) throws Exception {
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Goods> goodsList = new ArrayList<Goods>();
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT id,title, price, image "
					+ "FROM goods WHERE status=1 AND num>0 "
					+ "ORDER BY priority DESC LIMIT 0, ?";
			stat = conn.prepareStatement(sql);
			stat.setInt(1, count);
			
			rs = stat.executeQuery();
			while(rs.next()){
				Goods g=new Goods();
				g.setId(rs.getLong("id"));
				g.setTitle(rs.getString("title"));
				g.setImage(rs.getString("image"));
				g.setPrice(rs.getLong("price"));
                   
				goodsList.add(g);
                }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return goodsList;
	
	}

	@Override
	public Goods findById(Long id) throws Exception {

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		
		
		Goods goods =null;
		try {
			conn = DBUtils.getConn();
			String sql = "SELECT "
					+ "id,category_id,item_type, title,"
					+ "sell_point,price,num,"
					+ "barcode,image,status,priority "
					+ "FROM goods "
					+ "WHERE id=?";
			stat = conn.prepareStatement(sql);
			stat.setLong(1, id);
			rs = stat.executeQuery();
			if(rs.next()) {
				
				goods = new Goods();
				goods.setId(
						rs.getLong("id"));
				goods.setCategoryId(
						rs.getLong("category_id"));
				goods.setItemType(
						rs.getString("item_type"));
				goods.setTitle(
						rs.getString("title"));
				
				goods.setSellPoint(
						rs.getString("sell_point"));
				goods.setPrice(
						rs.getLong("price"));
				goods.setNum(
						rs.getInt("num"));
				goods.setBarcode(
						rs.getString("barcode"));
				goods.setImage(
						rs.getString("image"));
				goods.setStatus(
						rs.getInt("status"));
				goods.setPriority(
						rs.getInt("priority"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtils.close(conn, stat, rs);
		}
		return goods;
	}
	
	

	
	
	
	
	
	
	
	
}
