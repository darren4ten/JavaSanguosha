package org.wangfuyuan.sgs.player;

import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.enums.Colors;

/**
 * ��ҵ�һЩ��������
 * 
 * @author user
 * 
 */
public interface Player_FunctionIF {
	// ��ȡ�ϼ�
	AbstractPlayer getLastPlayer();

	// �Ƿ���Ѫ
	boolean isFullHP();

	// �Ƿ��Ѫ
	void isNullHP();

	// ��������֮��ľ���
	int getDistance(AbstractPlayer p);

	// ��ȡ����ȫ�����
	List<AbstractPlayer> getAllPlayers();

	// ��ȡȫ����ҵ�����
	List<String> getAllPlayersHandCard();

	// ����һ���ж��ƽ��л�ɫ�ж�
	boolean checkRollCard(AbstractCard card, Colors... color);

	// ����һ���ж��ƽ�����ֵ�ж�
	boolean checkRollCard(AbstractCard card,int max,int min);
	
	//������Χ
	int getAttackDistance();

	//���ؾ���
	int getDefenceDistance();
	
	//����ʹ�÷�Χ
	int getKitUseDistance();
	
	boolean isInRange(AbstractPlayer target);
	
	//�Ƿ�ͬһ����
	boolean isSameCountry(AbstractPlayer target);
	//�Ƿ�ͬ�Ա�
	boolean isSameSex(AbstractPlayer target);
}
