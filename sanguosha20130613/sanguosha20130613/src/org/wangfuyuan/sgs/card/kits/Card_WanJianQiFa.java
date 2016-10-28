package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;


/**
 * 万箭齐发
 * @author user
 *
 */
public class Card_WanJianQiFa extends AbstractKitCard{
	public Card_WanJianQiFa(){}
	
	/**
	 * 重写use方法
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		//触发
		p.getTrigger().afterMagic();
		//将所有玩家作为目标
		players = p.getFunction().getAllPlayers();
		AbstractPlayer tmp;
		//遍历目标，询问出闪
		for (int i = 0; i < players.size(); i++) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tmp = players.get(i);
			//绘制边框
			if(tmp.getState().isAI()){
				Panel_Player pp = (Panel_Player) tmp.getPanel();
				pp.setPanelState(Panel_Player.SELECTED);
				pp.repaint();
			}
			//询问无懈可击
			askWuXieKeJi(p, players);
			if(isWuXie){
				isWuXie = false;
				tmp.refreshView();
				continue;
			}
			//如果不出闪
			if(!tmp.getRequest().requestShan()){
				//扣1点血
				tmp.getAction().loseHP(1, p);
				tmp.refreshView();
			}
		//ViewManagement.getInstance().refreshAll();
		}
	}
	
	/**
	 * 重写效果绘制
	 */
	@Override
	protected void drawEffect(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		final List<AbstractPlayer> list = p.getFunction().getAllPlayers();
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "使用" + this.name);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				p.refreshView();
				PaintService.drawEffectImage(getEffectImage(), p);
				PaintService.drawLine(p, list);
			}
		});
	}
}
