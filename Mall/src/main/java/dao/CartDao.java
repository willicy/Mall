package dao;

import java.util.List;

import model.Cart;
import model.vo.CartVO;
/**
 * 
 * cart持久层接口
 *
 */
public interface CartDao {
	
	
	/**
	 * 新增购物车数据
	 * @param cart 购物车数据
	 * @return 受影响的行数
	 * @throws Exception 
	 */
	Integer addnew(Cart cart) throws Exception;
	
	/**
	 * 更改商品数量
	 * @param id 购物车id
	 * @param count 商品数量
	 * @return 受影响行数
	 */
	Integer updateCount(Integer id,Integer count) throws Exception;
	/**
	 * 根据用户id,和商品id查出购物车数据
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 购物车id和商品数量
	 */
	Cart findByUidAndGid(Integer uid,Long goodsId) throws Exception;
	
	/**
	 * 根据id获取购物车数据
	 * @param id 数据id
	 * @return 购物车数据
	 */
	Cart findById(Integer id) throws Exception;
	
	
	/**
	 * 根据若干个id查询匹配的购物车数据的集合
	 * @param ids 若干个id
	 * @return 购物车数据的集合
	 */
	List<CartVO> findByIds(Integer[] ids) throws Exception;
	
	
	
	/**
	 * 根据用户id查询该用户的购物车数据列表
	 * @param uid 用户id
 	 * @return 该用户的购物车数据列表
	 */
	List<CartVO> findByUid(Integer uid) throws Exception;
	
	/**
	 * 根据id删除收货地址数据
	 * @param id 收货地址数据的id
	 * @return 受影响的行数
	 */
	Integer deleteById(Integer id) throws Exception;

	/**
	 * 根据是否是直接购买删除收货地址数据
	 * @return 受影响的行数
	 * @throws Exception
	 */
	Integer deleteByBuy() throws Exception;
}
