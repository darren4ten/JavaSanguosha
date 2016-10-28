package org.wangfuyuan.sgs.card;

import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 虚拟牌接口
 * 提供给一些人物的变牌技能使用
 * @author user
 *
 */
public interface VirtualCardIF {
	//获取真实牌
	AbstractCard getRealCard();
	//获取该虚拟牌的替代值
	int getCardType();
	//主动使用
	void use(AbstractPlayer p,AbstractPlayer toP);
}
