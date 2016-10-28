package org.wangfuyuan.sgs.skills.active;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.ErrorMessageType;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.MessageManagement;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * ��٢�����ҡ�
 * @author user
 *
 */
public class HuaTuo_qingnang implements Runnable,SkillIF{
	AbstractPlayer player;
	//����������������
	Panel_Control pc;
	Panel_HandCards ph;
	//�Ƿ�ʹ�ù�
	boolean isUsed;
	public HuaTuo_qingnang(AbstractPlayer p){
		this.player = p;
	}
	
	@Override
	public void run() {
		if(player.getFunction().isFullHP()){
			MessageManagement.printErroMsg(ErrorMessageType.cannotUseCause_FullHP);
			synchronized (player.getProcess()) {
				player.getState().setRes(0);
				player.getProcess().notify();
			}
			return;
		}
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
				//������1��
				ph.getSelectedList().get(0).getCard().throwIt(player);
				player.getAction().addHP(1);
				player.refreshView();
				break;
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				break;
			}
		}
		synchronized (player.getProcess()) {
			player.getState().setRes(0);
			player.getProcess().notify();
		}
		isUsed = true;
	}

	@Override
	public String getName() {
		return "����";
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
			ph.setSelectLimit(1);
			ph.unableToUseCard();
			ph.disableClick();
			ph.enableOKAndCancel();
			ph.setTargetCheck(false);
			ph.remindToUseALL();
		}
	};
}
