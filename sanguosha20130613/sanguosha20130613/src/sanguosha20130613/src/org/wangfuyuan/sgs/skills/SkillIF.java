package org.wangfuyuan.sgs.skills;
/**
 * �������ܵĽӿ�
 * @author user
 *
 */
public interface SkillIF {
	//���ܳ�ʼ��
	void init();
	//����ʹ������
	boolean isEnableUse();
	//��ȡ��������
	String getName();
}
