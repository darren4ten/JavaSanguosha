package org.wangfuyuan.sgs.player;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.base.Card_Sha;

/**
 * 玩家动作行为接口
 * @author user
 *
 */
public interface Player_ActionIF {


	//使用杀
	boolean sha(AbstractPlayer p);
	
	//带技能杀
	void shaWithSkill();
	
	//带装备杀
	void shaWithEquipment();
	
	//使用闪
	void shan();
	
	//使用桃
	void tao();
	
	//是否回避杀
	boolean avoidSha(AbstractPlayer murder,Card_Sha card); 
	
	//决斗
	boolean jueDou(AbstractPlayer p);
	
	//救人
	void taoSave(AbstractPlayer p); 
	
	//加血
	void addHP(int num);
	
	//扣血
	void loseHP(int num,AbstractPlayer murderer);
	
	//将制定牌添加到手牌中
	void addCardToHandCard(AbstractCard c);
	
	//从牌堆摸1张牌
	void addOneCardFromList();
	
	//丢失手牌
	void loseHandCard(int num);
	
	//删除手牌
	void removeCard(int index);
	
	//删除指定手牌
	void removeCard(AbstractCard c);
	
	//装载装备
	void loadEquipmentCard(AbstractCard c);
	
	//丢失装备
	void unloadEquipmentCard(AbstractCard c);
	
	//发动锦囊效果
	void magic();
	
	//处理判定牌
	AbstractCard dealWithCheckCard(AbstractCard c);
	
	//死亡
	void dead(AbstractPlayer murderer);

}
