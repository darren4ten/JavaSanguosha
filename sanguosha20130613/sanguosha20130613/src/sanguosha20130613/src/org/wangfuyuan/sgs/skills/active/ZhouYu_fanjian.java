package org.wangfuyuan.sgs.skills.active;

import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * 周瑜【反间】
 * 
 * @author user
 * 
 */
public class ZhouYu_fanjian implements Runnable, SkillIF {
	final Colors[] colors = {Colors.HEITAO,Colors.HONGXIN,Colors.MEIHUA,Colors.FANGKUAI};
	AbstractPlayer player;
	Panel_Control pc;
	Panel_HandCards ph;
	public ZhouYu_fanjian(AbstractPlayer p) {
		this.player = p;
	}

	@Override
	public void run() {
		 pc = (Panel_Control) player.getPanel();
		 ph = pc.getHand();
		SwingUtilities.invokeLater(run);
		while (true) {
			if (player.getState().getRes() == Const_Game.OK) {
				if(ph.getTarget().isEmpty()){
					player.getState().setRes(0);
					ph.enableOKAndCancel();
					System.out.println("无目标返回");
					continue;
				}else{
					AbstractPlayer toP = ph.getTarget().getList().get(0);
					ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+"反间"+toP.getInfo().getName());
					sleep(1000);
					//AI选择花色
					int indexAI = new Random().nextInt(4);
					Colors c_AI = colors[indexAI]; 
					ViewManagement.getInstance().printBattleMsg(toP.getInfo().getName()+"猜花色为："+c_AI.toString());
					sleep(300);
					//AI选择手牌
					int n = new Random().nextInt(player.getState().getCardList().size());
					AbstractCard card_AI =  player.getState().getCardList().get(n);
					//ViewManagement.getInstance().printBattleMsg(toP.getInfo().getName()+"选择了"+card_AI.toString());
					//给牌
					card_AI.passToPlayer(player, toP);
					sleep(300);
					//根据花色结算
					if(c_AI != card_AI.getColor()){
						ViewManagement.getInstance().printBattleMsg(toP.getInfo().getName()+"猜错");
						toP.getAction().loseHP(1, null);
					}
					toP.refreshView();
					player.getState().setRes(0);
					break;
				}
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				ViewManagement.getInstance().getPrompt().clear();
				player.getState().setRes(0);
				break;
			}
		}
		synchronized (player.getProcess()) {
			player.getProcess().notify();
			player.refreshView();
		}
	}

	@Override
	public String getName() {
		return "反间";
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEnableUse() {
		// TODO Auto-generated method stub
		return false;
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			
			ph.unableToUseCard();
			ph.disableClick();
			ph.enableOKAndCancel();
			// 开放选择
			List<Panel_Player> list = ph.getMain().getPlayers();
			for (Panel_Player pp : list) {
				if (!pp.getPlayer().getState().isDead()) {
					pp.enableToUse();
				}
			}
			ViewManagement.getInstance().getPrompt().show_Message("请选择目标");
		}
	};
	
	/*
	 * 停顿间隔
	 */
	private void sleep(int t){
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
