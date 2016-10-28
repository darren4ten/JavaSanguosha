package org.wangfuyuan.sgs.card.base;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * �������Ƶ���
 * 
 * @author user
 * 
 */
public class Card_Shan extends AbstractCard implements EffectCardIF {
	public Card_Shan() {

	}

	/**
	 * ��Ӧʹ��
	 */
	@Override
	public boolean requestUse(AbstractPlayer p, List<AbstractPlayer> players) {
		boolean flag = super.requestUse(p, players);
		drawEffect(p, players);
		return flag;
	}

	/**
	 * ��д����Ч��
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
