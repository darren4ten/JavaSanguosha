package org.wangfuyuan.sgs.service;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;


/**
 * 检测可用牌 
 * 根据玩家状态和手牌 返回一组可用的手牌集合 
 * AI和玩家都将以返回的这组手牌集合为参考进行操作
 * 
 * @author user
 * 
 */
public class UsableCardCheckService {
	public UsableCardCheckService() {

	}

	/**
	 * 返回可用牌集合
	 */
	public List<AbstractCard> getAvailableCard(List<AbstractCard> list,
			AbstractPlayer p) {
		List<AbstractCard> list2 = new ArrayList<AbstractCard>(list);
		// 根据玩家的阶段判定
		switch (p.getStageNum()) {
		case PlayerIF.STAGE_BEGIN:
			break;
		case PlayerIF.STAGE_CHECK:
			break;
		case PlayerIF.STAGE_ADDCARDS:
			break;
		case PlayerIF.STAGE_USECARDS:
			// 剔除闪
			removeAllCardsByType(list2, Const_Game.SHAN);
			// 如果满血则剔除桃
			if (p.getFunction().isFullHP()) {
				removeAllCardsByType(list2, Const_Game.TAO);
			}
			//根据开关剔除杀
			if(p.getState().isUsedSha()){
				removeAllCardsByType(list2, Const_Game.SHA);
			}
			break;
		case PlayerIF.STAGE_THROWCRADS:
			if(p.getState().getCardList().size()<=p.getState().getCurHP()){
				list2.clear();
			}
			break;
		case PlayerIF.STAGE_END:
			list2.clear();
			break;
		}
		return list2;
	}

	// 剔除列表中的某种牌型
	private void removeAllCardsByType(List<AbstractCard> list, int type) {
		List<AbstractCard> listType = new ArrayList<AbstractCard>();
		// 获得所有满足剔除条件的牌
		for (AbstractCard c : list) {
			if (c.getType() == type) {
				listType.add(c);
			}
		}
		// 剔除指定类型的所有
		list.removeAll(listType);
	}

}
