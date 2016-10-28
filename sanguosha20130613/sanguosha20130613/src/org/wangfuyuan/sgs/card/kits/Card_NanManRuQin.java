package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ��������
 * 
 * @author user
 * 
 */
public class Card_NanManRuQin extends AbstractKitCard {
	public Card_NanManRuQin() {

	}

	/**
	 * ��дuse����
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		// ����
		p.getTrigger().afterMagic();

		// �����������ΪĿ��
		players = p.getFunction().getAllPlayers();

		AbstractPlayer tmp = null;
		// ����Ŀ�꣬ѯ�ʳ�ɱ
		for (int i = 0; i < players.size(); i++) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tmp = players.get(i);
			//���Ʊ߿�
			if(tmp.getState().isAI()){
				Panel_Player pp = (Panel_Player) tmp.getPanel();
				pp.setPanelState(Panel_Player.SELECTED);
				pp.repaint();
			}
			//ѯ����и�ɻ�
			askWuXieKeJi(p, players);
			if(isWuXie){
				isWuXie = false;
				tmp.refreshView();
				continue;
			}
			// �������
			if (!tmp.getRequest().requestSha()) {
				// ��1��Ѫ
				tmp.getAction().loseHP(1, p);
				tmp.refreshView();
			}
			// ViewManagement.getInstance().refreshAll();
		}
	}

	/**
	 * ��дЧ������
	 */
	@Override
	protected void drawEffect(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		final List<AbstractPlayer> list = p.getFunction().getAllPlayers();
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "ʹ��" + this.name);
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
