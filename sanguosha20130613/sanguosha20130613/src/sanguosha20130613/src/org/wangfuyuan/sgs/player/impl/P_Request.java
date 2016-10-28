package org.wangfuyuan.sgs.player.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.Player_RequestIF;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.ChangeCardIF;
import org.wangfuyuan.sgs.skills.SkillIF;

/**
 * 玩家响应实现类 封装了玩家响应请求出某种牌的行为
 * 
 * @author user
 * 
 */
public class P_Request implements Player_RequestIF {
	// 人物模型
	AbstractPlayer player;
	//当前需要响应的牌型
	int curType;
	// 构造器
	public P_Request(AbstractPlayer p) {
		this.player = p;
	}

	/**
	 * 询问是否出杀
	 */
	@Override
	public boolean requestSha() {
		//player.getState().setRequest(true);
		player.setRequest(true, Const_Game.SHA);
		player.refreshView();
		List<AbstractCard> listSha = hasCard(Const_Game.SHA);
		if (checkHasCardByType(Const_Game.SHA)|checkHasCardByTypeWithSkill(Const_Game.SHA)) {
			if (player.getState().isAI) {
				sleep(1000);
				if(listSha.isEmpty()){
					//player.getState().setRequest(false);
					clear();
					return false;
				}else{					
					listSha.get(0).requestUse(player, null);
					//player.getState().setRequest(false);
					clear();
					return true;
				}
			} else {
				return waitPlayerDo(listSha, Const_Game.SHA);
			}
		}
		sleep(1000);
		//player.getState().setRequest(false);
		clear();
		player.getState().setRes(0);
		synchronized (player.getProcess()) {
			while (!player.getState().isRequest) {
				player.getProcess().notify();
				break;
			}
		}
		return false;
	}

	/**
	 * 询问是否出闪
	 */
	@Override
	public boolean requestShan() {
		//player.getState().setRequest(true);
		player.setRequest(true, Const_Game.SHAN);
		player.refreshView();
		// 玩家手中所有的闪
		List<AbstractCard> listShan = hasCard(Const_Game.SHAN);
		// 如果有闪则分AI和玩家情况处理，没有则直接返回false
		if (checkHasCardByType(Const_Game.SHAN)
				| checkHasCardByTypeWithSkill(Const_Game.SHAN)) {
			// 如果是AI，则有就出
			if (player.getState().isAI) {
				sleep(1000);
				if (listShan.isEmpty()) {
					//player.getState().setRequest(false);
					clear();
					return false;
				} else {
					listShan.get(0).requestUse(player, null);
					//player.getState().setRequest(false);
					clear();
					return true;
				}
			} else {
				return waitPlayerDo(listShan, Const_Game.SHAN);
			}
		}
		sleep(1000);
		//player.getState().setRequest(false);
		clear();
		player.getState().setRes(0);
		synchronized (player.getProcess()) {
			while (!player.getState().isRequest) {
				player.getProcess().notify();
				break;
			}
		}
		return false;
	}

	/**
	 * 询问是否出桃
	 */
	@Override
	public boolean requestTao() {
		//player.getState().setRequest(true);
		player.setRequest(true, Const_Game.TAO);
		List<AbstractCard> listTao = hasCard(Const_Game.TAO);
		if (checkHasCardByType(Const_Game.TAO)|checkHasCardByTypeWithSkill(Const_Game.TAO)) {
			if (player.getState().isAI()) {
				sleep(1000);
				if (listTao.isEmpty()) {
					clear();
					return false;
				} else {
					listTao.get(0).requestUse(player, null);
					clear();
					return true;
				}
			} else {
				return waitPlayerDo(listTao, Const_Game.TAO);
			}
		}
		//player.getState().setRequest(false);
		clear();
		player.getState().setRes(0);
		synchronized (player.getProcess()) {
			while (!player.getState().isRequest) {
				player.getProcess().notify();
				break;
			}
		}
		return false;
	}

	/**
	 * 询问是否出无懈可击
	 */
	@Override
	public boolean requestWuXie() {
		//player.getState().setRequest(true);
		player.setRequest(true, Const_Game.WUXIEKEJI);
		List<AbstractCard> listWuXie = hasCard(Const_Game.WUXIEKEJI);
		if (!listWuXie.isEmpty()) {
			if (player.getState().isAI()) {
				sleep(1000);
				//player.getState().setRequest(false);
				clear();
				return listWuXie.get(0).requestUse(player, null);
			} else {
				return waitPlayerDo(listWuXie, Const_Game.WUXIEKEJI);
			}
		}
		//player.getState().setRequest(false);
		clear();
		player.getState().setRes(0);
		synchronized (player.getProcess()) {
			while (!player.getState().isRequest) {
				player.getProcess().notify();
				break;
			}
		}
		return false;
	}

	/**
	 * 检测手牌中是否有某种牌的集合
	 */
	private List<AbstractCard> hasCard(int type) {
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		// 遍历手牌 获得所有闪
		for (int i = 0; i < player.getState().getCardList().size(); i++) {
			// 如果是闪则添加到集合中
			if (player.getState().getCardList().get(i).getType() == type) {
				list.add(player.getState().getCardList().get(i));
			}
		}
		return list;
	}

	/**
	 * 等待玩家响应
	 */
	private boolean waitPlayerDo(final List<AbstractCard> list, final int type) {

		// 如果是玩家，则开放选择，并提示出牌
		//player.getState().setRequest(true);
		player.setRequest(true, type);
		final Panel_Control pc = (Panel_Control) player.getPanel();
		// 测试停止当前线程
		// Thread.currentThread().interrupt();
		System.out.println("等待玩家选择：" + "当前线程——"
				+ Thread.currentThread().getName());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Panel_HandCards ph = pc.getHand();
				ph.unableToUseCard();
				ph.remindToUse(type);
				ph.disableClick();
				ph.enableOKAndCancel();
				ph.setTargetCheck(false);
				if (type == Const_Game.WUXIEKEJI) {
					ViewManagement.getInstance().getPrompt().show_Message(
							"是否发动无懈可击");
				} else {
					ViewManagement.getInstance().getPrompt().show_RemindToUse(
							type, 1);
				}
			}
		});
		int res;
		while (true) {
			res = player.getState().getRes();
			if (res == type) {
				player.getState().setRes(0);
				boolean b = false;
				if (!pc.getHand().getSelectedList().isEmpty()) {
					AbstractCard c = pc.getHand().getSelectedList().get(0)
							.getCard();
					b = c.requestUse(player, null);
				}
				ViewManagement.getInstance().getPrompt().clear();
				//player.getState().setRequest(false);
				clear();
				synchronized (player.getProcess()) {
					while (!player.getState().isRequest()) {
						player.getProcess().notify();
						break;
					}
				}
				return b;
			}
			if (res == Const_Game.CANCEL) {
				// 清空提示出牌的消息并返回
				ViewManagement.getInstance().getPrompt().clear();
				break;
			}
			if(res == Const_Game.SKILL){
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (res == Const_Game.REDO) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						pc.getHand().unableToUseCard();
						pc.getHand().remindToUse(type);
						if (type == Const_Game.WUXIEKEJI) {
							ViewManagement.getInstance().getPrompt()
									.show_Message("是否发动无懈可击");
						} else {
							ViewManagement.getInstance().getPrompt()
									.show_RemindToUse(type, 1);
						}
					}
				});
				player.getState().setRes(0);
				//player.getState().setRequest(true);
				player.setRequest(true, type);
				continue;
			}
		}
		//player.getState().setRequest(false);
		clear();
		player.getState().setRes(0);
		synchronized (player.getProcess()) {
			while (!player.getState().isRequest) {
				player.getProcess().notify();
				break;
			}
		}
		return false;
	}

	private void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 检测是否拥有满足某种类型条件的牌
	 */
	private boolean checkHasCardByType(int type) {
		List<AbstractCard> list = hasCard(type);
		return !list.isEmpty();
	}

	/*
	 * 检测是否有变牌技能
	 */
	private boolean checkHasCardByTypeWithSkill(int type) {
		boolean hasChangCradSkill = false;
		for (SkillIF skill : player.getState().getSkill()) {
			if (skill instanceof ChangeCardIF) {
				ChangeCardIF cc = (ChangeCardIF) skill;
				if (cc.getResultType() == type) {
					hasChangCradSkill = true;
					break;
				}
			}
		}
		return hasChangCradSkill;
	}

	/**
	 * 清空响应状态
	 */
	public  void clear(){
		player.getState().setRequest(false);
		curType = 0;
	}
	
	public int getCurType() {
		return curType;
	}

	public void setCurType(int curType) {
		this.curType = curType;
	}
	
	
}
