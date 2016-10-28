package org.wangfuyuan.sgs.skills.process;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_GuanXing;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 诸葛亮【观星】
 * @author user
 *
 */
public class ZhuGe_GuanXing extends P_Process implements LockingSkillIF {
	//玩家控制栏引用
	Panel_Control pc;
	//观星面板引用
	Panel_GuanXing pg;
	
	public ZhuGe_GuanXing(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 重写回合开始
	 */
	@Override
	public void stage_begin() {
		if(player.getState().isAI())return;
		pc = (Panel_Control) player.getPanel();
		super.stage_begin();
		//询问是否发动技能
		SwingUtilities.invokeLater(ask);
		while(true){
			if(player.getState().getRes() == Const_Game.OK){
				player.getState().setRes(0);
				ViewManagement.getInstance().getPrompt().clear();
				break;
			}
			if(player.getState().getRes() == Const_Game.CANCEL){
				player.getState().setRes(0);
				ViewManagement.getInstance().getPrompt().clear();
				return;
			}
		}
		//显示面板
		SwingUtilities.invokeLater(showPanel);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(true){
			if(player.getState().getRes() == Const_Game.OK){
				//确定
				if(pg==null){
					player.getState().setRes(0);
					continue;
				}
				pg.finish();
				player.getState().setRes(0);
				break;
			}
		}
	}


	@Override
	public String getName() {
		return "观星";
	}
	
	Runnable ask = new Runnable() {
		@Override
		public void run() {
			ViewManagement.getInstance().getPrompt().show_Message("是否发动"+getName());
			pc.getHand().unableToUseCard();
			pc.getHand().enableOKAndCancel();
		}
	};
	
	Runnable showPanel = new Runnable() {
		
		@Override
		public void run() {
			pg = new Panel_GuanXing(pc.getMain());
			pc.getMain().add(pg, 0);
			pc.getHand().unableToUseCard();
			pc.getHand().disableClick();
			pg.setLocation(200, pc.getMain().getHeight() / 9);
			//开放确定按钮
			pc.getHand().enableOKAndCancel();
			player.getState().setRes(0);
		}
	};
}
