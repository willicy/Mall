package dao;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.vo.OrderVO;
/**
 * 
 * order持久层接口
 *
 */
public interface OrderDao {
	
	
	/**
	 * 插入订单数据
	 * @param order 订单数据
	 * @return 受影响的行数
	 */
	Integer insertOrder(Order order) throws Exception;
	
	/**
	 * 插入订单商品数据
	 * @param orderItem 订单商品数据
	 * @return 受影响的行数
	 */
	Integer insertOrderItem(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据oid查询订单详情
	 * @param id 订单oid
	 * @return 匹配的订单详情，如果没有匹配的数据，则返回null
	 */
	OrderVO findById(Integer id) throws Exception;
	
	/**
	 * 根据uid查询用户已有订单
	 * @param id 订单uid
	 * @return 匹配的订单id，如果没有匹配的数据，则返回null
	 */
	List<Integer> findByUid(Integer uid) throws Exception;
}
