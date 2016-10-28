package org.wangfuyuan.sgs.card.changed;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.VirtualCardIF;
import org.wangfuyuan.sgs.card.base.Card_Sha;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ����ġ�ɱ��
 * @author user
 *
 */
public class Virtual_Sha implements VirtualCardIF{
	AbstractCard realCard;
	public Virtual_Sha(AbstractCard realCard){
		this.realCard = realCard;
	}
	
	/**
	 * �����Ƶ�ʹ��
	 */
	public void use(final AbstractPlayer p,final AbstractPlayer toP){
		final Card_Sha cs = new Card_Sha();
		// ����ɱ
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "��" + toP.getInfo().getName()
				+ "ʹ����ɱ" );
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					p.refreshView();
					PaintService.drawEffectImage(cs.getEffectImage(),p);
					PaintService.drawLine(p,toP);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		cs.executeSha(p, toP);
	}
	
	
	@Override
	public int getCardType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AbstractCard getRealCard() {
		return realCard;
	}

}
