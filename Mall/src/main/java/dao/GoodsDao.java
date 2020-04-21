package dao;


import java.util.List;

import model.Goods;
/**
 * 
 * goods持久层接口
 *
 */
public interface GoodsDao {
	
	/**
	 * 根据优先级获取商品数据的列表
	 * @param count 获取的商品的数量
	 * @return 优先级最高的几个商品数据的列表
	 * @throws Exception 
	 */
	List<Goods> findByPriority(Integer count) throws Exception;
	
	/**
	 * 根据id查询商品详情
	 * @param id 商品的id
	 * @return 商品详情,没匹配的数据返回null
	 * @throws Exception 
	 */
	Goods findById(Long id) throws Exception;
	
}
