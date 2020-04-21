package dao;

import java.util.List;

import model.District;
/**
 * 
 * district�־ò�ӿ�
 *
 */
public interface DistrictDao {
	
	/**
	 * ���ݸ������Ż�ȡ�Ӽ���ʡ/��/�����б�
	 * @param parent �������ţ������Ҫ��ȡʡ���б��򸸼�����Ϊ86
	 * @return ʡ/��/�����б�
	 * @throws Exception 
	 */
	List<District> findByParent(String parent) throws Exception;

	/**
	 * ���ݴ��Ż�ȡʡ/��/��������
	 * @param code ʡ/��/���Ĵ���
	 * @return ʡ/��/�������飬���û��ƥ������ݣ��򷵻�null
	 * @throws Exception 
	 */
	District findByCode(String code) throws Exception;
	
}
