package org.wangfuyuan.sgs.skills.process;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.gui.main.Panel_SelectCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 张辽【突袭】
 * 
 * @author user
 * 
 */
public class ZhangLiao_tuxi extends P_Process implements LockingSkillIF {
	Panel_Control pc;

	public ZhangLiao_tuxi(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 重写摸牌阶段
	 */
	@Override
	public void stage_addCards() {
		if(player.getState().isAI())return;
		//询问是否发动技能
		SwingUtilities.invokeLater(ask);
		pc = (Panel_Control) player.getPanel();
		while(true){
			if(player.getState().getRes() == Const_Game.OK){
				//player.getState().setRes(0);
				break;
			}
			if(player.getState().getRes() == Const_Game.CANCEL){
				super.stage_addCards();
				return;
			}
		}
		//开放玩家选择
		SwingUtilities.invokeLater(run);
		while(true){
			if(player.getState().getRes() == Const_Game.OK &!pc.getHand().getTarget().isEmpty()){
				player.getState().setRes(0);
				pc.getHand().enableOKAndCancel();
				break;
			}
			if(player.getState().getRes() == Const_Game.CANCEL){
				super.stage_addCards();
				return;
			}
		}
		
		while (true) {
			if (player.getState().getRes() == Const_Game.OK) {
				ViewManagement.getInstance().printBattleMsg(
						player.getInfo().getName() + "发动" + getName());
				ViewManagement.getInstance().getPrompt().clear();
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							System.out.println("in");
							AbstractPlayer toP = pc.getHand().getTarget().getList()
									.get(0);
							Panel_SelectCard ps = new Panel_SelectCard(player, toP,
									Panel_SelectCard.SHUN, Panel_SelectCard.ONLY_HAND);
							pc.getMain().add(ps, 0);
							pc.getHand().unableToUseCard();
							pc.getHand().disableClick();
							ps.setLocation(200, pc.getMain().getHeight() / 9);
							pc.getMain().validate();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				player.getState().setRes(0);
				break;
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				player.getState().setRes(0);
				super.stage_addCards();
				return;
			}
		}
	}

	@Override
	public String getName() {
		return "突袭";
	}

	Runnable run = new Runnable() {
		@Override
		public void run() {
			pc.getHand().unableToUseCard();
			pc.getHand().disableClick();
			// 开放玩家选择
			List<Panel_Player> list = pc.getHand().getMain().getPlayers();
			for (Panel_Player pp : list) {
				if (!pp.getPlayer().getState().isDead()) {
					pp.enableToUse();
				}
			}
			ViewManagement.getInstance().getPrompt().show_Message("请选择目标");
		}
	};
	
	Runnable ask = new Runnable() {
		@Override
		public void run() {
			ViewManagement.getInstance().getPrompt().show_Message("是否发动"+getName());
			pc.getHand().unableToUseCard();
			pc.getHand().enableOKAndCancel();
		}
	};
}
