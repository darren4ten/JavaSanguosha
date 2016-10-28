package org.wangfuyuan.sgs.skills.trigger;

import java.util.Arrays;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 诸葛【空城】
 * @author user
 *
 */
public class ZhuGe_kongcheng extends P_Trigger implements LockingSkillIF{
	final Integer[] cards = {Const_Game.SHA,Const_Game.JUEDOU};
	public ZhuGe_kongcheng(AbstractPlayer p){
		super(p);
	}
	
	@Override
	public void afterLoseHandCard() {
		super.afterLoseHandCard();
		if(player.getState().getCardList().isEmpty()){
			ViewManagement.getInstance().printBattleMsg("【空城】触发");
			player.getState().getImmuneCards().addAll(Arrays.asList(cards));
		}
	}
	
	@Override
	public void afterGetHandCard() {
		super.afterGetHandCard();
		if(!player.getState().getCardList().isEmpty()){
			player.getState().getImmuneCards().clear();
		}
	}

	@Override
	public String getName() {
		return "空城";
	}

}
