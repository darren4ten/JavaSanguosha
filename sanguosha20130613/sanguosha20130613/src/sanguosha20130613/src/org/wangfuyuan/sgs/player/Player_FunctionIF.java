package org.wangfuyuan.sgs.player;

import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.enums.Colors;

/**
 * 玩家的一些操作函数
 * 
 * @author user
 * 
 */
public interface Player_FunctionIF {
	// 获取上家
	AbstractPlayer getLastPlayer();

	// 是否满血
	boolean isFullHP();

	// 是否空血
	void isNullHP();

	// 计算两家之间的距离
	int getDistance(AbstractPlayer p);

	// 获取场上全部玩家
	List<AbstractPlayer> getAllPlayers();

	// 获取全部玩家的手牌
	List<String> getAllPlayersHandCard();

	// 翻开一张判定牌进行花色判定
	boolean checkRollCard(AbstractCard card, Colors... color);

	// 翻开一张判定牌进行数值判定
	boolean checkRollCard(AbstractCard card,int max,int min);
	
	//攻击范围
	int getAttackDistance();

	//防守距离
	int getDefenceDistance();
	
	//锦囊使用范围
	int getKitUseDistance();
	
	boolean isInRange(AbstractPlayer target);
	
	//是否同一国家
	boolean isSameCountry(AbstractPlayer target);
	//是否同性别
	boolean isSameSex(AbstractPlayer target);
}
