package org.wangfuyuan.sgs.card.kits;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * 抽象锦囊类 提供一些锦囊牌的通用方法
 * 
 * @author user
 * 
 */
public abstract class AbstractKitCard extends AbstractCard {
	// 是否被无懈
	protected boolean isWuXie;
	// 玩家控制栏，与UI的连接点
	protected Panel_Control pc;

	/*
	 * 目标集合 主要是防止出现多线程操作时候，作为参数的玩家列表被强制final后可能引发一些问题，所以临时建立一个集合来存储 也可能是我多虑了
	 */
	protected List<AbstractPlayer> targetPlayers;

	public AbstractKitCard(int id, int number, Colors color) {
		super(id, number, color);
	}

	public AbstractKitCard() {
	}

	/**
	 * 锦囊使用前的初始化
	 */
	protected void initKit() {
		isWuXie = false;
	}

	/**
	 * 重写use
	 */
	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		if (!p.getState().isAI())
			pc = (Panel_Control) p.getPanel();
		targetPlayers = new ArrayList<AbstractPlayer>(players);
		initKit();
	}

	/**
	 * 询问无懈可击
	 * 
	 * 无懈可击的实现方法： 锦囊牌中都有一个boolean值表示是否被无懈 这个方法用来询问场上是否有无懈，如果打出无懈则将boolean值取反
	 * 锦囊最终将在子类具体实现时候根据boolean值判定是否发动效果
	 * 
	 * @param p
	 * @param players
	 */
	public void askWuXieKeJi(AbstractPlayer p, List<AbstractPlayer> players) {
		if (hasWuxiekejiInBattle()) {
			p.refreshView();
			System.out.println("场上有无懈");
			// 询问无懈
			List<AbstractPlayer> askPlayers = ModuleManagement.getInstance()
					.getPlayerList();
			for (int i = 0; i < askPlayers.size(); i++) {
				// 如果有人出无懈
				if (askPlayers.get(i).getRequest().requestWuXie()) {
					isWuXie = true;
					break;
				}
			}
		}
	}

	/*
	 * 场上是否有无懈存在
	 */
	protected boolean hasWuxiekejiInBattle() {
		for (AbstractPlayer p : ModuleManagement.getInstance().getPlayerList()) {
			if (p.hasCard(Const_Game.WUXIEKEJI))
				return true;
		}
		return false;
	}

	/**
	 * 锦囊类的绘制 大部分可以使用这个模板 有些例外的需要重写，如闪电、无懈等
	 */
	@Override
	protected void drawEffect(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		//有目标的锦囊
		if (targetType == CardIF.SELECT) {
			final AbstractPlayer tmp = players.get(0);
			ViewManagement.getInstance().printBattleMsg(
					p.getInfo().getName() + "对" + tmp.getInfo().getName()
							+ "使用了" + this.toString());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (getEffectImage() != null)
						PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.drawLine(p, tmp);
				}
			});
			//无目标的锦囊
		}else{
			ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+"使用了"+getName());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(getEffectImage()!=null)PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.clearAfter(1000);
				}
			});
		
		}
	}
}
