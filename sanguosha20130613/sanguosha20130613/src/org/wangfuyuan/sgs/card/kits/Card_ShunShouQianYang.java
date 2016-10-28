package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.gui.main.Panel_SelectCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ˳��ǣ��
 * 
 * @author user
 * 
 */
public class Card_ShunShouQianYang extends AbstractKitCard implements EffectCardIF {
	Panel_Control pc;
	Panel_SelectCard ps;

	public Card_ShunShouQianYang() {
	}

	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		// ��������
		p.getTrigger().afterMagic();
		// �����и����return
		askWuXieKeJi(p, players);
		if (isWuXie) {
			ViewManagement.getInstance().printBattleMsg(getName() + "��Ч");
			ViewManagement.getInstance().refreshAll();
			return;
		}
		if (p.getState().isAI()) {
			AbstractPlayer target = players.get(0);
			if(!target.getState().getCardList().isEmpty()){
				AbstractCard c = target.getState().getCardList().get(0);
				target.getAction().removeCard(c);
				p.getAction().addCardToHandCard(c);
				p.refreshView();
				target.refreshView();
			}
		} else {
			pc = (Panel_Control) p.getPanel();
			ps = new Panel_SelectCard(p, players.get(0), Panel_SelectCard.SHUN);
			// ��ʾѡ�����ȴ�����
			// �߳���ͣ
			SwingUtilities.invokeLater(run);

		}
	}

	Runnable run = new Runnable() {

		@Override
		public void run() {
			pc.getMain().add(ps, 0);
			pc.getHand().refresh();
			pc.getHand().unableToUseCard();
			ps.setLocation(200, pc.getMain().getHeight() / 9);
			pc.getMain().validate();

		}
	};

	@Override
	public void targetCheck(Panel_HandCards ph) {
		// ���� ���
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (int i = 0; i < list.size(); i++) {
			Panel_Player pp = list.get(i);
			// �������
			if (pp.getPanelState() == Panel_Player.DEAD
					|| pp.getPanelState() == Panel_Player.DISABLE) {
				// System.out.println("this is dead");
				continue;
			}
			// �����Ҫ���
			if (!this.isInRange(ph.getPlayer(), pp.getPlayer())) {
				pp.disableToUse();
				continue;
			}
			// ��������ƻ���װ����
			if (pp.getPlayer().getState().getCardList().isEmpty()
					&& pp.getPlayer().getState().getEquipment().isEmpty()) {
				pp.disableToUse();
				continue;
			}
			pp.enableToUse();
		}
	}

	/**
	 * ��дĿ�������
	 */
	@Override
	public boolean isInRange(AbstractPlayer user, AbstractPlayer target) {
		int p2p = user.getFunction().getDistance(target);
		int att = user.getFunction().getKitUseDistance();// �˴���д
		int def = target.getFunction().getDefenceDistance();
		return (att - def) >= p2p;

	}

}
