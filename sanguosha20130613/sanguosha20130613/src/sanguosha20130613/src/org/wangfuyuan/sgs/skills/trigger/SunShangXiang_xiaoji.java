package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 孙尚香的【枭姬】
 * 丢失装备后，获得2张手牌
 * @author user
 *
 */
public class SunShangXiang_xiaoji extends P_Trigger implements LockingSkillIF{
	private static final int NUMBER = 2;
	public SunShangXiang_xiaoji(AbstractPlayer p) {
		this.player = p;
	}
	
	/**
	 * 重写卸载装备触发
	 */
	@Override
	public void afterUnloadEquipmentCard() {
		ViewManagement.getInstance().printBattleMsg("【枭姬】技能触发");
		for (int i = 0; i < NUMBER; i++) {
			player.getAction().addOneCardFromList();			
		}
	}

	@Override
	public String getName() {
		return "枭姬";
	}
	
}
