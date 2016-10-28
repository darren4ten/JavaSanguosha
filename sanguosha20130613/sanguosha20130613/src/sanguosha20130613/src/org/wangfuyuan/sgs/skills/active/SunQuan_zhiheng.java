package org.wangfuyuan.sgs.skills.active;

import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.ErrorMessageType;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.MessageManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * ��Ȩ���ƺ⡿
 * @author user
 *
 */
public class SunQuan_zhiheng implements Runnable ,SkillIF{
	AbstractPlayer player;
	//����������������
	Panel_Control pc;
	Panel_HandCards ph;
	//����
	boolean isUsed;
	public SunQuan_zhiheng(AbstractPlayer p){
		this.player = p;
	}
	
	@Override
	public void run() {
		if(isUsed){
			MessageManagement.printErroMsg(ErrorMessageType.hasUsed);
			synchronized (player.getProcess()) {
				player.getState().setRes(0);
				player.getProcess().notify();
			}
			return;
		}
		pc = (Panel_Control) player.getPanel();
		ph = pc.getHand();
		SwingUtilities.invokeLater(run);
		while (true) {
			if (player.getState().getRes() == Const_Game.OK) {
				if(ph.getSelectedList().isEmpty())break;
				pc.getMain().getBf().clear();
				//��ѡ�е��ƶ���,������һ��
				int n = ph.getSelectedList().size();
				for (int i = 0; i < n; i++) {
					AbstractCard c = ph.getSelectedList().get(i).getCard();
					player.getState().getCardList().remove(c);
					pc.getMain().getBf().addOneCard(c);
					c.gc();
				}
				player.refreshView();
				for (int i = 0; i < n; i++) {
					player.getAction().addOneCardFromList();
				}
				ViewManagement.getInstance().getPrompt().clear();
				player.refreshView();
				break;
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				ViewManagement.getInstance().getPrompt().clear();
				player.refreshView();
				break;
			}
		}
		synchronized (player.getProcess()) {
			player.getState().setRes(0);
			player.getProcess().notify();
		}
		//1�غ�ֻ��1��
		isUsed = true;
	}

	@Override
	public String getName() {
		return "�ƺ�";
	}

	@Override
	public void init() {
		isUsed = false;
	}

	@Override
	public boolean isEnableUse() {
		// TODO Auto-generated method stub
		return false;
	}
	
	Runnable run = new Runnable() {
		@Override
		public void run() {
			ph.getSelectedList().clear();
			//ph.unableToUseCard();
			ph.disableClick();
			ph.enableOKAndCancel();
			// ��ʾ���е���
			ph.remindToUseALL();
			ph.setSelectLimit(99);
			ph.setTargetCheck(false);
			ViewManagement.getInstance().getPrompt().show_Message("��ѡ��Ҫ�û�����");
			List<Panel_Player> list = pc.getMain().getPlayers();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).disableToUse();
			}
		}
	};
}
