package org.wangfuyuan.sgs.card.equipment;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 防具接口
 * @author user
 *
 */
public interface ArmorIF {
	//是否免疫该牌
	boolean check(AbstractCard card,AbstractPlayer player);
}
