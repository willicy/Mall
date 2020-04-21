package dao;

import java.util.List;

import model.Address;
/**
 * 
 * address�־ò�ӿ�
 *
 */
public interface AddressDao {
	
	/**
	 * �����µ��ջ���ַ����
	 * @param address �ջ���ַ����
	 * @return ��Ӱ�������
	 * @throws Exception 
	 */
	Integer addnew(Address address) throws Exception;
	
	/**
	 * ����idɾ���ջ���ַ����
	 * @param id �ջ���ַ���ݵ�id
	 * @return ��Ӱ�������
	 */
	Integer deleteById(Integer id) throws Exception;
	
	/**
	 * ��ĳ�û����ջ���ַȫ������Ϊ��Ĭ��
	 * @param uid �û�id
	 * @return ��Ӱ�������
	 * @throws Exception 
	 */
	Integer updateNonDefault(Integer uid) throws Exception;
	

	/**
	 * ��ָ��id���ջ���ַ����ΪĬ��
	 * @param id �ջ���ַ����id
	 * @return ��Ӱ�������
	 */
	Integer updateDefault(Integer id) throws Exception;
	
	/**
	 * �����û�id��ȡ���û����ջ���ַ���ݵ�����
	 * @param uid �û�id
	 * @return ���û����ջ���ַ���ݵ����������û�����ݣ��򷵻�0
	 * @throws Exception 
	 */
	Integer getCountByUid(Integer uid) throws Exception;
	
	/**
	 * ����id��ѯ�ջ���ַ����
	 * @param id �ջ���ַid
	 * @return ƥ����ջ���ַ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Address findById(Integer id) throws Exception;
	
	/**
	 * ��ȡĳ�û����ջ���ַ�б�
	 * @param uid �û�id
	 * @return �ջ���ַ�б�
	 * @throws Exception 
	 */
	List<Address> findByUid(Integer uid) throws Exception;

	/**
	 * ���µ�ַ����
	 * @param address ��ַ����
	 * @param id �ջ���ַid
	 * @return ��Ӱ������
	 * @throws Exception
	 */
	Integer updateInfo(Address address,Integer id) throws Exception;
}
