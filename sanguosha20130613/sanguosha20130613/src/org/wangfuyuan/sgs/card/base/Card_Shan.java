package org.wangfuyuan.sgs.card.base;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * “闪”牌的类
 * 
 * @author user
 * 
 */
public class Card_Shan extends AbstractCard implements EffectCardIF {
	public Card_Shan() {

	}

	/**
	 * 响应使用
	 */
	@Override
	public boolean requestUse(AbstractPlayer p, List<AbstractPlayer> players) {
		boolean flag = super.requestUse(p, players);
		drawEffect(p, players);
		return flag;
	}

	/**
	 * 重写绘制效果
	 */
	@Override
	protected void drawEffect(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				PaintService.drawEffectImage(getEffectImage(), p);
				PaintService.clearAfter(1000);
			}
		});
	}
}
