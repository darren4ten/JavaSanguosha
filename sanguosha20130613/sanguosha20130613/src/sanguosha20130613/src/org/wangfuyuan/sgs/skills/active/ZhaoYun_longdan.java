package org.wangfuyuan.sgs.skills.active;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.changed.Virtual_Sha;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;
import org.wangfuyuan.sgs.skills.ChangeCardIF;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * 赵云【龙胆】
 * 杀==闪
 * @author user
 * 
 */
public class ZhaoYun_longdan implements Runnable, SkillIF ,ChangeCardIF{
	AbstractPlayer player;
	Panel_Control pc;
	public ZhaoYun_longdan(AbstractPlayer p) {
		this.player = p;
	}
	
	/**
	 * 在出牌回合：
	 * 		显示所有杀&闪，调用虚拟杀
	 * 在响应他人：
	 * 		显示所有杀&闪，根据情况返回杀or闪的值
	 */
	@Override
	public void run() {
		 pc = (Panel_Control) player.getPanel();
		//如果在响应请求时候
		if(player.getState().isRequest()){
			useAsRequest();
			return;
		}
		//显示所有杀&闪
		SwingUtilities.invokeLater(showShaAndShan);
		//等待选择
		while(true){
			if(player.getState().getRes()==Const_Game.OK){
				AbstractCard c = pc.getHand().getSelectedList().get(0).getCard();
				//原有的牌丢弃
				c.throwIt(player);
				//新出一张虚拟杀
				new Virtual_Sha(c).use(player, pc.getHand().getTarget().getList().get(0));
				break;
			}
			if(player.getState().getRes()==Const_Game.CANCEL){
				player.refreshView();
				break;
			}
		}
		//如果在回合中，就解回合锁
		if(player.getStageNum()==PlayerIF.STAGE_USECARDS){
			synchronized (player.getProcess()) {
				player.getState().setRes(0);
				player.getProcess().notify();
			}
		}
	}

	/*
	 * 响应阶段的使用
	 */
	private void useAsRequest() {
		// 锁住响应
		player.getState().setRes(Const_Game.SKILL);
		// 显示所有符合的牌
		SwingUtilities.invokeLater(showShaAndShan);
		// 等待选择
		while (true) {
			if (player.getState().getRes() == Const_Game.OK) {
				/*AbstractCard c = pc.getHand().getSelectedList().get(0)
						.getCard();*/
				// 更新手牌
				pc.getHand().updateCards();
				player.getState().setRes(getResultType());
				break;
			}
			if (player.getState().getRes() == Const_Game.CANCEL) {
				player.getState().setRes(Const_Game.REDO);
				break;
			}
		}
		synchronized (player.getRequest()) {
			player.getRequest().notify();
		}
	}
	
	@Override
	public String getName() {
		return "龙胆";
	}

	@Override
	public boolean isEnableUse() {
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getResultType() {
		int res = 0;
		switch(player.getRequest().getCurType()){
		case Const_Game.SHA:
			res= Const_Game.SHA;
			break;
		case Const_Game.SHAN:
			res= Const_Game.SHAN;
			break;
		}
		return res;
	}
	
	Runnable showShaAndShan = new Runnable() {
		
		@Override
		public void run() {
			Panel_HandCards ph = pc.getHand();
			ph.unableToUseCard();
			ph.remindToUse(Const_Game.SHA,Const_Game.SHAN);
			ph.setSelectLimit(1);
			ph.disableClick();
			ph.enableOKAndCancel();
		}
	};
}
