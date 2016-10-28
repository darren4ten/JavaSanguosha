package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 夏侯惇技能【刚烈】
 * 受伤后，伤害来源判定，若不为红则扣1点血，或者丢弃2张手牌
 * @author user
 *
 */
public class XiaHouDun_ganglie extends P_Trigger implements LockingSkillIF{

	public XiaHouDun_ganglie(AbstractPlayer p) {
		this.player = p;
	}
	/**
	 * 重写受伤触发
	 */
	@Override
	public void afterLoseHP(AbstractPlayer murderer) {
		AbstractCard cc = ModuleManagement.getInstance().showOneCheckCard();
		if(player.getFunction().checkRollCard(cc, Colors.HEITAO,Colors.FANGKUAI,Colors.MEIHUA)){
			murderer.getAction().loseHP(1, null);
			ViewManagement.getInstance().printChatMsg("["+player.getInfo().getName()+"]"+"鼠辈，竟敢伤我");
		}
	}
	@Override
	public String getName() {
		return "刚烈";
	}
	
	
}
