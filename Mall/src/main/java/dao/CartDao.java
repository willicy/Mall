package dao;

import java.util.List;

import model.Cart;
import model.vo.CartVO;
/**
 * 
 * cart�־ò�ӿ�
 *
 */
public interface CartDao {
	
	
	/**
	 * �������ﳵ����
	 * @param cart ���ﳵ����
	 * @return ��Ӱ�������
	 * @throws Exception 
	 */
	Integer addnew(Cart cart) throws Exception;
	
	/**
	 * ������Ʒ����
	 * @param id ���ﳵid
	 * @param count ��Ʒ����
	 * @return ��Ӱ������
	 */
	Integer updateCount(Integer id,Integer count) throws Exception;
	/**
	 * �����û�id,����Ʒid������ﳵ����
	 * @param uid �û�id
	 * @param goodsId ��Ʒid
	 * @return ���ﳵid����Ʒ����
	 */
	Cart findByUidAndGid(Integer uid,Long goodsId) throws Exception;
	
	/**
	 * ����id��ȡ���ﳵ����
	 * @param id ����id
	 * @return ���ﳵ����
	 */
	Cart findById(Integer id) throws Exception;
	
	
	/**
	 * �������ɸ�id��ѯƥ��Ĺ��ﳵ���ݵļ���
	 * @param ids ���ɸ�id
	 * @return ���ﳵ���ݵļ���
	 */
	List<CartVO> findByIds(Integer[] ids) throws Exception;
	
	
	
	/**
	 * �����û�id��ѯ���û��Ĺ��ﳵ�����б�
	 * @param uid �û�id
 	 * @return ���û��Ĺ��ﳵ�����б�
	 */
	List<CartVO> findByUid(Integer uid) throws Exception;
	
	/**
	 * ����idɾ���ջ���ַ����
	 * @param id �ջ���ַ���ݵ�id
	 * @return ��Ӱ�������
	 */
	Integer deleteById(Integer id) throws Exception;

	/**
	 * �����Ƿ���ֱ�ӹ���ɾ���ջ���ַ����
	 * @return ��Ӱ�������
	 * @throws Exception
	 */
	Integer deleteByBuy() throws Exception;
}
