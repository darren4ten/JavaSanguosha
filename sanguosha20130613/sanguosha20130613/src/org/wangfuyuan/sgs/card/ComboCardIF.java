package org.wangfuyuan.sgs.card;

import java.util.List;

import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * ����ƽӿ�
 * @author user
 *
 */
public interface ComboCardIF {

	//��ȡ��ʵ��
	List<AbstractCard> getRealCards();
	//��ȡ�������Ƶ����ֵ
	int getCardType();
	//����ʹ��
	void use(AbstractPlayer p,AbstractPlayer toP);

}
