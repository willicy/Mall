package dao;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.vo.OrderVO;
/**
 * 
 * order�־ò�ӿ�
 *
 */
public interface OrderDao {
	
	
	/**
	 * ���붩������
	 * @param order ��������
	 * @return ��Ӱ�������
	 */
	Integer insertOrder(Order order) throws Exception;
	
	/**
	 * ���붩����Ʒ����
	 * @param orderItem ������Ʒ����
	 * @return ��Ӱ�������
	 */
	Integer insertOrderItem(OrderItem orderItem) throws Exception;
	
	/**
	 * ����oid��ѯ��������
	 * @param id ����oid
	 * @return ƥ��Ķ������飬���û��ƥ������ݣ��򷵻�null
	 */
	OrderVO findById(Integer id) throws Exception;
	
	/**
	 * ����uid��ѯ�û����ж���
	 * @param id ����uid
	 * @return ƥ��Ķ���id�����û��ƥ������ݣ��򷵻�null
	 */
	List<Integer> findByUid(Integer uid) throws Exception;
}
