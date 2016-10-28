package org.wangfuyuan.sgs.skills.active;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * 曹操【护驾】
 * @author user
 *
 */
public class CaoCao_HuJia_Boss implements Runnable,SkillIF{
	AbstractPlayer player;
	Panel_Control pc;
	
	public CaoCao_HuJia_Boss(AbstractPlayer player){
		this.player = player;
	}
	@Override
	public void run() {
		pc = (Panel_Control) player.getPanel();
		if(!player.getState().isRequest()){
			synchronized (player.getProcess()) {
				player.getState().setRes(0);
				player.getProcess().notify();
			}
			return;
		}
		SwingUtilities.invokeLater(ask);
		while(true){

			if (player.getState().getRes() == Const_Game.OK) {
				// 如果有同势力
				for (AbstractPlayer p : player.getSameCountryPlayers()) {
					if (p.getRequest().requestShan()) {
						AbstractCard c = p.getState().getUsedCard().get(0);
						player.getState().getUsedCard().add(0, c);
						
							player.getState().setRes(Const_Game.SHAN);
							try {
								//暂停1秒确保信号被接受到
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						
						break;
					}
				}
				//如果没人闪
				player.getState().setRes(Const_Game.REDO);
				return;
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				break;
			}
		
		}
	}
	@Override
	public String getName() {
		return "护驾";
	}
	
	@Override
	public void init() {
	}
	@Override
	public boolean isEnableUse() {
		return false;
	}
	
	Runnable ask = new Runnable() {
		
		@Override
		public void run() {
			Panel_HandCards ph = pc.getHand();
			ph.unableToUseCard();
			ph.enableOKAndCancel();
			
		}
	};
}
