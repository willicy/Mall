package dao;


import java.util.List;

import model.Goods;
/**
 * 
 * goods�־ò�ӿ�
 *
 */
public interface GoodsDao {
	
	/**
	 * �������ȼ���ȡ��Ʒ���ݵ��б�
	 * @param count ��ȡ����Ʒ������
	 * @return ���ȼ���ߵļ�����Ʒ���ݵ��б�
	 * @throws Exception 
	 */
	List<Goods> findByPriority(Integer count) throws Exception;
	
	/**
	 * ����id��ѯ��Ʒ����
	 * @param id ��Ʒ��id
	 * @return ��Ʒ����,ûƥ������ݷ���null
	 * @throws Exception 
	 */
	Goods findById(Long id) throws Exception;
	
}
