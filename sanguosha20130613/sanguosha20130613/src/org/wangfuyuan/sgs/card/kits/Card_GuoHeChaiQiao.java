package org.wangfuyuan.sgs.card.kits;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.gui.main.Panel_SelectCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * 过河拆桥
 * 
 * @author user
 * 
 */
public class Card_GuoHeChaiQiao extends AbstractKitCard implements EffectCardIF{

	Panel_SelectCard ps;

	public Card_GuoHeChaiQiao() {

	}

	/**
	 * 重写use 在目标玩家的所有牌中选择一张 删除
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		// 触发技能
		p.getTrigger().afterMagic();

		// 如果无懈，则return
		askWuXieKeJi(p, players);
		if (isWuXie) {
			ViewManagement.getInstance().printBattleMsg(getName() + "无效");
			ViewManagement.getInstance().refreshAll();
			return;
		}
		if (p.getState().isAI()) {
			AbstractPlayer target = players.get(0);
			if(!target.getState().getCardList().isEmpty()){
				AbstractCard c = target.getState().getCardList().get(0);
				target.getAction().removeCard(c);
				c.gc();
				ModuleManagement.getInstance().getBattle().addOneCard(c);
				p.refreshView();
				target.refreshView();
			}
		
		} else {
			// pc = (Panel_Control)p.getPanel();
			ps = new Panel_SelectCard(p, targetPlayers.get(0),
					Panel_SelectCard.CHAI);
			// 显示选择面板等待处理
			try {
				SwingUtilities.invokeAndWait(run);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			// p.getTrigger().afterMagic();
			// p.refreshView();
		}
	}
	/**
	 * 重写目标检测
	 */
	@Override
	public void targetCheck(Panel_HandCards ph) {
		// 遍历 检测
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (int i = 0; i < list.size(); i++) {
			Panel_Player pp = list.get(i);
			// 如果无手牌或者装备牌
			if (pp.getPlayer().getState().getCardList().isEmpty()
					&& pp.getPlayer().getState().getEquipment().isEmpty()) {
				pp.disableToUse();
				continue;
			}
			pp.enableToUse();
		}
	}
	
	
	Runnable run = new Runnable() {

		@Override
		public void run() {
			pc.getPlayer().refreshView();
			pc.getMain().add(ps, 0);
			pc.getHand().unableToUseCard();
			ps.setLocation(200, pc.getMain().getHeight() / 9);
			pc.getMain().validate();
		}
	};
}
