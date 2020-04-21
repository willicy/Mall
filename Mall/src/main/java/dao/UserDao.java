package dao;



import model.User;
/**
 * 
 * user持久层接口
 *
 */
public interface UserDao {
	
	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 * @throws Exception 
	 */
	User findByUsername(String username) throws Exception;
	
	/**
	 * 新增用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 * @throws Exception 
	 */
	Integer addnew(User user) throws Exception;
	
	
	/**
	 * 更新密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @return 受影响的行数
	 */
	Integer updatePassword(
		Integer uid,String password) throws Exception;
	
	/**
	 * 更新用户头像
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer updateAvatar(User user)throws Exception;
	/**
	 * 修改用户个人资料
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer changeInfo(User user)throws Exception;
	
	/**
	 * 根据id查询用户数据
	 * @param id 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findById(Integer id) throws Exception;
	
	
}
