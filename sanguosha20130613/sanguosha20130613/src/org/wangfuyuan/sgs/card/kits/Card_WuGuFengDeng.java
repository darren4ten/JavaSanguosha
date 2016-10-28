package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_WuGuFengDeng;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 五谷丰登
 * 
 * @author user
 * 
 */
public class Card_WuGuFengDeng extends AbstractKitCard {
	public Card_WuGuFengDeng(){}
	
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		if(p.getState().isAI()){
			//TODO
		}else{
			//显示选择面板等待处理
			//线程暂停
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Panel_Control pc = (Panel_Control)p.getPanel();
					Panel_WuGuFengDeng pw = new Panel_WuGuFengDeng(p);
					pc.getMain().add(pw,0);
					pc.getHand().unableToUseCard();
					pw.setLocation(200,pc.getMain().getHeight()/9);
					pc.getMain().validate();
				
				}
			});
			
		}
	
	}
}
