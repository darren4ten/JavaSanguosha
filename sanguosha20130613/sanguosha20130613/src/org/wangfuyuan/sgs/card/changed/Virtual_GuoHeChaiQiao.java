package org.wangfuyuan.sgs.card.changed;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.card.VirtualCardIF;
import org.wangfuyuan.sgs.card.kits.Card_GuoHeChaiQiao;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_SelectCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * ����Ĺ��Ӳ���
 * @author user
 *
 */
public class Virtual_GuoHeChaiQiao extends Card_GuoHeChaiQiao implements VirtualCardIF,EffectCardIF{
	AbstractCard realCard;
	
	public Virtual_GuoHeChaiQiao(AbstractCard real){
		this.realCard = real;
		this.name = "���Ӳ���";
		this.effectImage = ImgUtil.getPngImgByName("ef_guohechaiqiao");
	}
	
	@Override
	public int getCardType() {
		return Const_Game.GUOHECHAIQIAO;
	}

	@Override
	public AbstractCard getRealCard() {
		return realCard;
	}

	//ʹ��
	@Override
	public void use(final AbstractPlayer p, final AbstractPlayer toP) {
		System.out.println("���Ӳ����̣߳�"+Thread.currentThread().getName());
		//����
		drawEffect(p, toP);
		// �����и����return
		askWuXieKeJi(p, null );
		if (isWuXie) {
			ViewManagement.getInstance().printBattleMsg(getName() + "��Ч");
			ViewManagement.getInstance().refreshAll();
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Panel_Control pc = (Panel_Control)p.getPanel();
				Panel_SelectCard ps = new Panel_SelectCard(p, toP,Panel_SelectCard.CHAI);
				pc.getMain().add(ps,0);
				pc.getHand().unableToUseCard();
				ps.setLocation(200,pc.getMain().getHeight()/9);
				pc.getMain().validate();
			}
		});
	
	}

	//����
	protected void drawEffect(final AbstractPlayer p, final AbstractPlayer toP) {
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "��" + toP.getInfo().getName()
						+ "ʹ����"+getName());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (getEffectImage() != null)
					PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.drawLine(p, toP);
			}
		});
	}
}
