package org.wangfuyuan.sgs.player;

import org.wangfuyuan.sgs.card.AbstractCard;

/**
 * 一些触发事件
 * @author user
 *
 */
public interface Player_TriggerIF {

	//使用杀触发
	void afterSha();
	
	//使用闪触发
	void afterShan();
	
	//使用桃触发
	void afterTao();
	
	//加血触发
	void afterAddHP();
	
	//扣血触发
	void afterLoseHP(AbstractPlayer murderer);
	
	//获得手牌触发
	void afterGetHandCard();
	
	//丢失手牌触发
	void afterLoseHandCard();
	
	//获得装备触发
	void afterLoadEquipmentCard();
	
	//丢失装备触发
	void afterUnloadEquipmentCard();
	
	//没有手牌触发
	void afterNullCards();
	
	//翻出判定牌后触发
	void afterCheck(AbstractCard c,boolean result);
	
	//使用锦囊触发
	void afterMagic();
	
	//死亡触发
	void afterDead();
	
}
