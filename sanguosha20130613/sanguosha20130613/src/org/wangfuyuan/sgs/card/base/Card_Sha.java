package org.wangfuyuan.sgs.card.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.card.equipment.AbstractWeaponCard;
import org.wangfuyuan.sgs.card.equipment.ArmorIF;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ��ɱ���Ƶ���
 * 
 * @author user
 * 
 */
public class Card_Sha extends AbstractCard implements EffectCardIF{

	public Card_Sha() {

	}

	/**
	 * ��дuse����
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		System.out.println("Ŀ������"+players.size());
		List<AbstractPlayer> list = new ArrayList<AbstractPlayer>(players);
		for (int i = 0; i < list.size(); i++) {
			final AbstractPlayer tmp = list.get(i);
			// ����ɱ��Ч��
			ViewManagement.getInstance().printBattleMsg(
					p.getInfo().getName() + "��" + tmp.getInfo().getName()
					+ "ʹ����ɱ" );
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						p.refreshView();
						PaintService.drawEffectImage(getEffectImage(),p);
						PaintService.drawLine(p,tmp);
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//drawEffect(p, players);
			executeSha(p, tmp);
			// ˢ��
			tmp.refreshView();

		}
		// ViewManagement.getInstance().refreshAll();
	}

	/**
	 * ִ��ɱ�Ƶ�ɱ����
	 */
	public void executeSha(AbstractPlayer p, AbstractPlayer toP) {
		if (!toP.getAction().avoidSha(p,this)) {
			// ���ʹ���ߴ������������������ɱ
			AbstractWeaponCard awc = (AbstractWeaponCard) p.getState()
					.getEquipment().getWeapons();
			if (awc != null) {
				awc.shaWithEquipment(p, toP, this);
			} else {
				// �ж�����
				ArmorIF am = (ArmorIF) toP.getState().getEquipment().getArmor();
				if (am == null || !am.check(this, toP)) {
					p.getAction().sha(toP);
				}
			}
		}
		p.refreshView();
	}
	
	/**
	 * ʹ��Ŀ����
	 */
	@Override
	public  void targetCheck(Panel_HandCards ph) {

		// ���� ���
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (int i = 0; i < list.size(); i++) {
			Panel_Player pp = list.get(i);
			//�������
			if(pp.getPanelState()==Panel_Player.DEAD||pp.getPanelState()==Panel_Player.DISABLE){
				System.out.println("this is dead");
				continue;
			}
			// �����Ҫ���
			if (!this.isInRange(ph.getPlayer(), pp.getPlayer())) {
				pp.disableToUse();
				continue;
			}
			pp.enableToUse();
		}
	
	}

	/**
	 * �ж�user�Ƿ��ܶ�targetʹ��������
	 */
	@Override
	public boolean targetCheck4SinglePlayer(AbstractPlayer user,
			AbstractPlayer target) {
		boolean b = !user.getState().isUsedSha();
		boolean b2 = isInRange(user, target);
		return b&&b2;
	}

}
