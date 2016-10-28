package org.wangfuyuan.sgs.skills.process;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
/**
 * 吕蒙技能【克己】
 * @author user
 *
 */
public class LvMeng_keji extends P_Process implements LockingSkillIF{
	
	public LvMeng_keji(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 重写弃牌
	 * 如果没出过杀，不用弃牌
	 */
	@Override
	public void stage_throwCrads() {
		if(player.getState().getCurHP()>=player.getState().getCardList().size()){
			//super.stage_throwCrads();
			return;
		}
		if(player.getState().isAI())return;
		if(!player.getState().isUsedSha()){
			//System.out.println(player.getInfo().getName()+"不需要弃牌");
			ViewManagement.getInstance().ask(player, getName());
			while(true){
				if(player.getState().getRes()==Const_Game.OK){
					ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+"发动"+getName());
					ViewManagement.getInstance().getPrompt().clear();
					player.getState().setRes(0);
					return;
				}
				if(player.getState().getRes()==Const_Game.CANCEL){
					player.getState().setRes(0);
					super.stage_throwCrads();
					break;
				}
			}
			
		}else{
			super.stage_throwCrads();
		}
	}

	@Override
	public String getName() {
		return "克己";
	}
}
