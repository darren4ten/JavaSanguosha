package org.wangfuyuan.sgs.service.AI;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * AI在准备出某张牌的时候进行的可用目标检测
 * @author user
 *
 */
public class AITargetCheckService {

	/**
	 * 根据准备出的牌
	 * 获得可以对其使用的目标列表
	 * @param player
	 * @param c
	 * @return
	 */
	public static List<AbstractPlayer> getEnableTargets(AbstractPlayer player,AbstractCard c){
		List<AbstractPlayer> list = player.getFunction().getAllPlayers();
		List<AbstractPlayer> result = new ArrayList<AbstractPlayer>();
		for (int i = 0; i < list.size(); i++) {
			if(c.targetCheck4SinglePlayer(player, list.get(i))){
				result.add(list.get(i));
			}
		}
		return result;
	}
	
}
