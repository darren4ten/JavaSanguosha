package org.wangfuyuan.sgs.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.DelayKitIF;
import org.wangfuyuan.sgs.command.Command_ThrowCards;
import org.wangfuyuan.sgs.command.Command_UseCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.Frame_Main;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;
import org.wangfuyuan.sgs.player.Player_ProcessIF;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.service.AI.AIProcessService;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * 封装玩家的6个回合的动作
 * 
 * @author user
 * 
 */
public class P_Process implements Player_ProcessIF {
	protected AbstractPlayer player;
	protected AIProcessService AIProcess;
	protected boolean isSkilling;
	protected boolean canUseCard = true;
	public P_Process(AbstractPlayer p) {
		this.player = p;
		AIProcess = new AIProcessService(player);
	}

	/**
	 * 进入
	 */
	public void start() {
		init();
		stage_begin();
		stage_check();
		stage_addCards();
		stage_useCards();
		stage_throwCrads();
		stage_end();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pass();
	}
	//初始化
	private void init(){
		player.setSkip(false);
		player.getState().setExtDamage(0);
		player.getState().setUsedSha(false);
		player.getState().getEquipment().initAll();
		// 技能初始化
		List<SkillIF> skills = player.getState().getSkill();
		if (!skills.isEmpty()) {
			for (int i = 0; i < skills.size(); i++) {
				skills.get(i).init();
			}
		}
	}
	// 回合开始
	public void stage_begin() {
		player.setStageNum(PlayerIF.STAGE_BEGIN);
		ModuleManagement.getInstance().getBattle().clear();
		ViewManagement.getInstance().printMsg( player.getInfo().getName() + "开始");
		player.refreshView();
	}

	// 判定阶段
	public void stage_check() {
		player.setStageNum(PlayerIF.STAGE_CHECK);
		ViewManagement.getInstance().printMsg( player.getInfo().getName() + "判定");
		//获取判定牌集合
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		for (int i = 0; i <player.getState().getCheckedCardList().size(); i++) {
			list.add(player.getState().getCheckedCardList().get(i));
		}
		for (int i = 0; i < list.size(); i++) {
			DelayKitIF d =  (DelayKitIF) list.get(i);
			d.doKit();
			//System.out.println(player.toString()+","+player.getState().getCheckedCardList().get(i).toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 摸牌阶段
	public void stage_addCards() {
		player.setStageNum(PlayerIF.STAGE_ADDCARDS);
		ViewManagement.getInstance().printMsg( player.getInfo().getName() + "摸牌");
		/*System.out.println(player.getState().getId().toString()
				+ player.getInfo().getName() + "摸牌");*/
		//绘制动画
		/*SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				PaintService.drawGetCards(player, 2);
			}
		});*/
		player.getAction().addOneCardFromList();
		player.getAction().addOneCardFromList();

		player.refreshView();
	}

	// 出牌阶段
	public void stage_useCards() {
		if(!canUseCard){
			canUseCard = true;
			return;
		}
		if (player.getState().isAI) {
			AIProcess.stage_useCards();
			return;
		}
		player.setStageNum(PlayerIF.STAGE_USECARDS);
		player.refreshView();
		ViewManagement.getInstance().printMsg( player.getInfo().getName() + "出牌");
		/*
		System.out.println(player.getState().getId().toString()
				+ player.getInfo().getName() + "出牌");*/
		while (!player.isSkip()) {
			// 当按下确定时候
			if (player.getState().getRes() == Const_Game.OK) {
				Panel_Control pc = (Panel_Control) player.getPanel();
				Panel_HandCards ph = pc.getHand();
				Thread t = new Thread(new Command_UseCard(ph), "出牌线程");
				t.start();
				player.getState().setRes(0);

			}
			// 如果发动技能，暂停当前线程，等技能施放完毕，再继续
			if (player.getState().getRes() == Const_Game.SKILL) {
				Thread t = new Thread((Runnable) player.getState().getSkill()
						.get(0));
				t.start();
				player.getState().setRes(0);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// 其他中断
			if (isSkilling||player.getState().isRequest) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			//游戏结束
			if(Frame_Main.isGameOver){
				//pass();
				break;
			}
		}
	}

	// 弃牌阶段
	public void stage_throwCrads() {
		if (player.getState().isAI) {
			AIProcess.stage_throwCrads();
			return;
		}
		Panel_Control pc = (Panel_Control) player.getPanel();
		player.setStageNum(PlayerIF.STAGE_THROWCRADS);
		ViewManagement.getInstance().printMsg( player.getInfo().getName() + "弃牌");
		/*System.out.println(player.getState().getId().toString()
				+ player.getInfo().getName() + "弃牌");*/
		player.refreshView();
		// 检测是否需要弃牌
		if (player.getState().getCardList().size() <= player.getState()
				.getCurHP())
			return;
		ViewManagement.getInstance().getPrompt().show_RemindToThrow(
				player.getState().getCardList().size()
						- player.getState().getCurHP());
		pc.getHand().setSelectLimit(
				player.getState().getCardList().size()
						- player.getState().getCurHP());
		while (true) {
			/*pc.getHand().setSelectLimit(
					player.getState().getCardList().size()
							- player.getState().getCurHP());*/
			if (player.getState().getRes() == Const_Game.OK) {

				new Thread(new Command_ThrowCards(pc.getHand())).start();
				/*MyThread t = ModuleManagement.getThreadUseCard();
				t.setTarget(new Command_ThrowCards(pc.getHand()));
				t.start();*/
				player.getState().setRes(0);
			}
			if(player.getState().getCardList().size() > player.getState()
					.getCurHP()){
				continue;
			}else{
				break;
			}
		}
		ViewManagement.getInstance().getPrompt().clear();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 回合结束
	public void stage_end() {
		/*if (player.getState().isAI) {
			AIProcess.stage_end();
			return;
		}*/
		player.setStageNum(PlayerIF.STAGE_END);
		ViewManagement.getInstance().printMsg(player.getInfo().getName() + "结束");
		/*System.out.println(player.getState().getId().toString()
				+ player.getInfo().getName() + "结束");
		player.refreshView();*/
	}
	//下家开始
	private void pass(){
		if(Frame_Main.isGameOver){
			return;
		}
		System.out.println(player.getInfo().getName()+"pass");
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ViewManagement.getInstance().refreshAll();
		player.getNextPlayer().process();		
	}
	public boolean isSkilling() {
		return isSkilling;
	}

	public void setSkilling(boolean isSkilling) {
		this.isSkilling = isSkilling;
	}

	public boolean isCanUseCard() {
		return canUseCard;
	}

	public void setCanUseCard(boolean canUseCard) {
		this.canUseCard = canUseCard;
	}

	
}
