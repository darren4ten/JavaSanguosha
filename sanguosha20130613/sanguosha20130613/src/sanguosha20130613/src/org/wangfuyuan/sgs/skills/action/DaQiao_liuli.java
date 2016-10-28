package org.wangfuyuan.sgs.skills.action;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.base.Card_Sha;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Action;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 大乔【流离】
 * 
 * @author user
 * 
 */
public class DaQiao_liuli extends P_Action implements LockingSkillIF {
	// 丢弃的牌
	AbstractCard cardThrow;
	// 流离的目标
	AbstractPlayer target;
	// 凶手
	AbstractPlayer pSha;
	Panel_Control pc;
	Panel_HandCards ph;

	public DaQiao_liuli(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 重写回避杀的判断
	 */
	@Override
	public boolean avoidSha(AbstractPlayer murder, Card_Sha card) {
		//无手牌则退出
		if(player.getState().getCardList().isEmpty())return false;
		pSha = murder;
		// 询问是否流离
		// 选人 确定
		if (player.getState().isAI()) {
			cardThrow = player.getState().getCardList().get(0);
			target = player.getNextPlayer();
			execute(murder, card);
			return true;
		} else {
			pc = (Panel_Control) player.getPanel();
			ph = pc.getHand();
			//SwingUtilities.invokeLater(ask);
			ViewManagement.getInstance().ask(player, getName());
			while (player.getState().getRes() == 0) {
				// 如果确认技能发动
				if (player.getState().getRes() == Const_Game.OK) {
					player.getProcess().setSkilling(true);
					player.getState().setRes(0);
					break;
				}
				if (player.getState().getRes() == Const_Game.CANCEL) {
					player.getState().setRes(0);
					return false;
				}
			}
			// 进入技能 选择参数
			SwingUtilities.invokeLater(run);
			while (true) {
				if (player.getState().getRes() == Const_Game.OK) {
					if ((!ph.getSelectedList().isEmpty())
							&& !(ph.getTarget().isEmpty())) {
						cardThrow = ph.getSelectedList().get(0).getCard();
						target = ph.getTarget().getList().get(0);
						
						ViewManagement.getInstance().getPrompt().clear();
						execute(murder, card);
						player.getState().setRes(0);
						synchronized (player.getProcess()) {
							player.getProcess().notify();
						}
						return true;
					} else {
						ph.enableOKAndCancel();
						player.getState().setRes(0);
						continue;
					}
				}
				if (player.getState().getRes() == Const_Game.CANCEL) {
					player.getState().setRes(0);
					return false;
				}
			}
		}
		// return false;
	}

	private void execute(AbstractPlayer murder, Card_Sha csha) {
		cardThrow.throwIt(player);
		// 信息
		ViewManagement.getInstance().printBattleMsg(
				player.getInfo().getName() + "将"
						+ csha.toString() + "流离给"
						+ target.getInfo().getName());
		// 测试画线
		SwingUtilities.invokeLater(draw);
		csha.executeSha(murder, target);
	}

	@Override
	public String getName() {
		return "流离";
	}


	Runnable run = new Runnable() {

		@Override
		public void run() {
			ViewManagement.getInstance().getPrompt().show_Message(
					"请选择丢弃的牌，并选择目标");
			ph.remindToUseALL();
			ph.enableOKAndCancel();
			ph.getTarget().setLimit(1);
			ph.setTargetCheck(false);
			// 开放射程内的人物
			List<Panel_Player> list = ph.getMain().getPlayers();
			for (Panel_Player pp : list) {
				if (!pp.getPlayer().getState().isDead()
						&& player.getFunction().isInRange(pp.getPlayer())
						&& pp.getPlayer() != pSha) {
					pp.enableToUse();
				} else {
					pp.disableToUse();
				}
			}
		}
	};

	Runnable draw = new Runnable() {

		@Override
		public void run() {
			PaintService.drawLine(player, target);

		}
	};
}
