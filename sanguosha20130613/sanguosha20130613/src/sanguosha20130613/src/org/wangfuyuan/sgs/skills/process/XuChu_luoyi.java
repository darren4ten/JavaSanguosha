package org.wangfuyuan.sgs.skills.process;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
/**
 * 许褚【裸衣】
 */
public class XuChu_luoyi extends P_Process implements LockingSkillIF{
	final int extDamage = 1 ;
	
	public XuChu_luoyi(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 发动技能则摸1张牌
	 * 伤害+1
	 */
	@Override
	public void stage_addCards() {
		//AI
		if(player.getState().isAI()){
			super.stage_addCards();
			return;
		}
		//询问
		//SwingUtilities.invokeLater(run);
		ViewManagement.getInstance().ask(player, getName());
		while(true){
			if(player.getState().getRes() == Const_Game.OK){				
				ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+"发动裸衣！");
				ViewManagement.getInstance().getPrompt().clear();
				player.getAction().addOneCardFromList();
				player.getState().setExtDamage(extDamage);
				player.getState().setRes(0);
				return;
			}
			if(player.getState().getRes() == Const_Game.CANCEL){
				player.getState().setRes(0);
				ViewManagement.getInstance().getPrompt().clear();
				super.stage_addCards();
				return;
			}
		}
	}

	@Override
	public String getName() {
		return "裸衣";
	}	
}
