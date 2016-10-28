package org.wangfuyuan.sgs.skills.process;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
/**
 * ���ҡ����¡�
 */
public class XuChu_luoyi extends P_Process implements LockingSkillIF{
	final int extDamage = 1 ;
	
	public XuChu_luoyi(AbstractPlayer p) {
		super(p);
	}

	/**
	 * ������������1����
	 * �˺�+1
	 */
	@Override
	public void stage_addCards() {
		//AI
		if(player.getState().isAI()){
			super.stage_addCards();
			return;
		}
		//ѯ��
		//SwingUtilities.invokeLater(run);
		ViewManagement.getInstance().ask(player, getName());
		while(true){
			if(player.getState().getRes() == Const_Game.OK){				
				ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+"�������£�");
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
		return "����";
	}	
}
