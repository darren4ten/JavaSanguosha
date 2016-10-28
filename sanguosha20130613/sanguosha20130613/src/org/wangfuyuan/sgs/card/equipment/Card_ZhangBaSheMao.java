package org.wangfuyuan.sgs.card.equipment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.base.Card_Sha;
import org.wangfuyuan.sgs.card.changed.Combo_Sha;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Card;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * �ɰ���ì
 * 
 * @author user
 * 
 */
public class Card_ZhangBaSheMao extends AbstractWeaponCard implements
		ActiveSkillWeaponCardIF {

	boolean isSkilling;

	public Card_ZhangBaSheMao() {
		
	}

	public Card_ZhangBaSheMao(int id, int number, Colors color) {
		super(id, number, color);
	}


	@Override
	public boolean onClick_close(Panel_HandCards ph) {
		disable();
		return false;
	}

	/**
	 * �������¼� �÷��������¼�ί���߳�
	 */
	@Override
	public boolean onClick_open(final Panel_HandCards ph) {
			if(ph.getPlayer().getState().isUsedSha()){
				return false;
			}
			if(!ph.getPlayer().getState().isRequest()){
				showTarget(ph);
			}
			// ��ס
			final AbstractPlayer p = ph.getPlayer();
			p.getProcess().setSkilling(true);
			// ����һ���̵߳ȴ���ɱ
			new Thread(new Runnable() {
				@Override
				public void run() {
					enable();
					while (isSkilling) {
						if (p.getState().getRes() == Const_Game.OK) {
							if (ph.getTarget().isEmpty()
									|| ph.getSelectedList().size() < 2) {
								p.getState().setRes(0);
								ph.enableOKAndCancel();
								continue;
							}
							List<AbstractCard> list = new ArrayList<AbstractCard>();
							for (Panel_Card pc : ph.getSelectedList()) {
								AbstractCard c = pc.getCard();
								list.add(c);
								c.throwIt(p);
							}

							final AbstractPlayer tmp = ph.getTarget().getList()
									.get(0);
							final Combo_Sha combo = new Combo_Sha(list);
							// ����
							ViewManagement.getInstance().printBattleMsg(
									p.getInfo().getName() + "��"
											+ tmp.getInfo().getName()
											+ "ʹ���ˡ��ɰ���ì��");
							try {
								SwingUtilities.invokeAndWait(new Runnable() {
									@Override
									public void run() {
										p.refreshView();
										PaintService.drawEffectImage(combo
												.getEffectImage(), p);
										PaintService.drawLine(p, tmp);
									}
								});
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
							combo.use(p, tmp);
							p.getState().setRes(0);
							break;
						}
					}

					synchronized (p.getProcess()) {
						p.getProcess().setSkilling(false);
						p.getProcess().notify();
					}
				}
			}).start();
			return true;
		
	}

	private void showTarget(Panel_HandCards ph){
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (Panel_Player pp : list) {
			pp.enableToUse();
		}
		new Card_Sha().targetCheck(ph);
		ph.remindToUseALL();
		ph.disableClick();
		ph.setSelectLimit(2);
		ph.setTargetCheck(false);
	}
	@Override
	public void disable() {
		isSkilling = false;
	}

	@Override
	public void enable() {
		isSkilling = true;
	}

}
