package org.wangfuyuan.sgs.card;

import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * �����ƽӿ�
 * �ṩ��һЩ����ı��Ƽ���ʹ��
 * @author user
 *
 */
public interface VirtualCardIF {
	//��ȡ��ʵ��
	AbstractCard getRealCard();
	//��ȡ�������Ƶ����ֵ
	int getCardType();
	//����ʹ��
	void use(AbstractPlayer p,AbstractPlayer toP);
}
