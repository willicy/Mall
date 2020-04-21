package dao;



import model.User;
/**
 * 
 * user�־ò�ӿ�
 *
 */
public interface UserDao {
	
	/**
	 * �����û�����ѯ�û�����
	 * @param username �û���
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 * @throws Exception 
	 */
	User findByUsername(String username) throws Exception;
	
	/**
	 * �����û�����
	 * @param user �û�����
	 * @return ��Ӱ�������
	 * @throws Exception 
	 */
	Integer addnew(User user) throws Exception;
	
	
	/**
	 * ��������
	 * @param uid �û���id
	 * @param password ������
	 * @return ��Ӱ�������
	 */
	Integer updatePassword(
		Integer uid,String password) throws Exception;
	
	/**
	 * �����û�ͷ��
	 * @param user �û�����
	 * @return ��Ӱ�������
	 */
	Integer updateAvatar(User user)throws Exception;
	/**
	 * �޸��û���������
	 * @param user �û�����
	 * @return ��Ӱ�������
	 */
	Integer changeInfo(User user)throws Exception;
	
	/**
	 * ����id��ѯ�û�����
	 * @param id �û�id
	 * @return ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User findById(Integer id) throws Exception;
	
	
}
