package org.wangfuyuan.sgs.card.kits;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.base.Card_Sha;
import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_SelectCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * �赶ɱ��
 * 
 * @author user
 * 
 */
public class Card_JieDaoShaRen extends AbstractKitCard {
	Panel_Control pc;
	Panel_SelectCard ps;
	public Card_JieDaoShaRen(){}
	public Card_JieDaoShaRen(int id, int number, Colors color) {
		super(id, number, color);
		this.name = "�赶ɱ��";
		this.type = Const_Game.JIEDAOSHAREN;
		this.targetType = SELECT;
	}

	@Override
	public Image showImg() {
		return ImgUtil.getPngImgByName("J_jiedaosharen");
	}

	/**
	 * ��дĿ����
	 */
	@Override
	public void targetCheck(Panel_HandCards ph) {
		ph.getTarget().setLimit(2);

	}

	/**
	 * ��дuse
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		if (players.size() < 2
				|| !players.get(0).getState().getEquipment().hasWeapons()) {
			p.refreshView();
			return;
		}
		super.use(p, players);
		final AbstractPlayer p1 = players.get(0);
		final AbstractPlayer p2 = players.get(1);
		
		//��ҵ���Ӧ
		AbstractCard res ;
		//AI��ѡ��
		if(p1.getState().isAI()){
			res = null;
		}else{
			//��ҵ�ѡ��
			//��ʱû��
			res = null;
		}
		//����res������
		if(res!=null){
			Card_Sha c = (Card_Sha) res;
			c.executeSha(p1, p2);
		}else{
			AbstractEquipmentCard c = p1.getState().getEquipment().getWeapons();
			c.unload(p1);
			ModuleManagement.getInstance().getGcList().remove(c);
			p.getAction().addCardToHandCard(c);
			p.refreshView();
			p1.refreshView();
			p2.refreshView();
		}
	}

	@Override
	public Image getEffectImage() {
		return ImgUtil.getPngImgByName("ef_jiedaosharen");
	}

	@Override
	protected void drawEffect(final AbstractPlayer p, List<AbstractPlayer> players) {
		//���ƺ���Ϣ
		final AbstractPlayer p1 = players.get(0);
		final AbstractPlayer p2 = players.get(1);
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "��"
				+ p1.getInfo().getName() + "ʹ��"
				+ getName());
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					p.refreshView();
					PaintService.drawLine(p, p1);
					PaintService.drawLine(p1, p2);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}}

	
}
