package dao;

import java.util.List;

import model.Address;
/**
 * 
 * address持久层接口
 *
 */
public interface AddressDao {
	
	/**
	 * 增加新的收货地址数据
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 * @throws Exception 
	 */
	Integer addnew(Address address) throws Exception;
	
	/**
	 * 根据id删除收货地址数据
	 * @param id 收货地址数据的id
	 * @return 受影响的行数
	 */
	Integer deleteById(Integer id) throws Exception;
	
	/**
	 * 将某用户的收货地址全部设置为非默认
	 * @param uid 用户id
	 * @return 受影响的行数
	 * @throws Exception 
	 */
	Integer updateNonDefault(Integer uid) throws Exception;
	

	/**
	 * 将指定id的收货地址设置为默认
	 * @param id 收货地址数据id
	 * @return 受影响的行数
	 */
	Integer updateDefault(Integer id) throws Exception;
	
	/**
	 * 根据用户id获取该用户的收货地址数据的数量
	 * @param uid 用户id
	 * @return 该用户的收货地址数据的数量，如果没有数据，则返回0
	 * @throws Exception 
	 */
	Integer getCountByUid(Integer uid) throws Exception;
	
	/**
	 * 根据id查询收货地址数据
	 * @param id 收货地址id
	 * @return 匹配的收货地址数据，如果没有匹配的数据，则返回null
	 */
	Address findById(Integer id) throws Exception;
	
	/**
	 * 获取某用户的收货地址列表
	 * @param uid 用户id
	 * @return 收货地址列表
	 * @throws Exception 
	 */
	List<Address> findByUid(Integer uid) throws Exception;

	/**
	 * 更新地址数据
	 * @param address 地址资料
	 * @param id 收货地址id
	 * @return 受影响行数
	 * @throws Exception
	 */
	Integer updateInfo(Address address,Integer id) throws Exception;
}
