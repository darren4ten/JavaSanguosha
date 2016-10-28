package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 曹操技能【奸雄】
 * 受伤后将造成伤害的牌收入收牌
 * 玩家触发类
 * --重写受伤触发事件： 扣血后将造成伤害的牌收入手牌中
 * @author user
 *
 */
public class CaoCao_jianxiong extends P_Trigger implements LockingSkillIF{
	public CaoCao_jianxiong(){}
	public CaoCao_jianxiong(AbstractPlayer p) {
		this.player = p;
	}
	/**
	 * 重写扣血触发
	 */
	@Override
	public void afterLoseHP(AbstractPlayer murderer) {
		if(murderer==null || murderer.getState().getUsedCard().isEmpty())return;
		//打印消息
		ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+"发动技能【"+getName()+"】");
		//将凶手出的牌收走
		for (AbstractCard c  : murderer.getState().getUsedCard()) {
			player.getAction().addCardToHandCard(c);
			ModuleManagement.getInstance().getGcList().remove(c);
		}	
	}
	
	@Override
	public String getName() {
		return "奸雄";
	}
}