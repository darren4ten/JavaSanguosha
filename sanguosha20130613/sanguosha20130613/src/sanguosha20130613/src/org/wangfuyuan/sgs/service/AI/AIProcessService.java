package org.wangfuyuan.sgs.service.AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;
import org.wangfuyuan.sgs.service.UsableCardCheckService;


/**
 * AI控制类
 * 
 * @author user
 * 
 */
public class AIProcessService {
	AbstractPlayer p;
	UsableCardCheckService check = new UsableCardCheckService();
	/**
	 * 构造器 传入一个人物模型
	 * @param p
	 */
	public AIProcessService(AbstractPlayer p){
		this.p = p;
	}
	
	// 回合开始
	public void stage_begin() {
		p.setStageNum(PlayerIF.STAGE_BEGIN);
	}

	// 判定阶段
	public void stage_check() {
		p.setStageNum(PlayerIF.STAGE_CHECK);
	}

	// 摸牌阶段
	public void stage_addCards() {
		p.setStageNum(PlayerIF.STAGE_ADDCARDS);
		p.getAction().addOneCardFromList();
		p.getAction().addOneCardFromList();
		p.getPanel().refresh();
	}

	/**
	 *  出牌阶段
	 *  随机取1张能用的手牌，检测是否能用，若可用则下一步
	 *  判断牌的使用每目标类型，如果是要选择的，则建立一个可选玩家集合，若该集合非空，则随机选一个；
	 *  TODO
	 */
	public void stage_useCards() {
		p.setStageNum(PlayerIF.STAGE_USECARDS);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//测试阶段 目标暂时设置为下家
		//List<AbstractPlayer> list = new ArrayList<AbstractPlayer>();
		//测试AI目标检测函数
		/*List<AbstractPlayer> list = AITargetCheckService.getEnableTargets(player, c)
		
		list.add(p.getNextPlayer());*/
		//获取1张可用的牌
		List<AbstractCard> listA = check.getAvailableCard(p.getState().getCardList(), p);
		AbstractCard c = null;
		if(listA.size()>0){
			int index = new Random().nextInt(listA.size());
			c = listA.get(index);
			//listA.get(index).use(p, list);
			//p.getState().getCardList().remove(listA.get(index));
			List<AbstractPlayer> listTargets = AITargetCheckService.getEnableTargets(p, c);
			if(listTargets.isEmpty())return;
			List<AbstractPlayer> listArgs = new ArrayList<AbstractPlayer>();
			listArgs.add(listTargets.get(new Random().nextInt(listTargets.size())));
			c.use(p, listArgs);
		}
	}

	// 弃牌阶段
	public void stage_throwCrads() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.setStageNum(PlayerIF.STAGE_THROWCRADS);
		System.out.println(p.getState().getId().toString()+p.getInfo().getName()+"弃牌");
		while(p.getState().getCardList().size()>p.getState().getCurHP()){
			p.getState().getCardList().get(0).throwIt(p);
		}
		p.getPanel().refresh();
	
	}

	// 回合结束
	public void stage_end() {
		p.setStageNum(PlayerIF.STAGE_END);
		//ViewManagement.getInstance().printMsg(p.getState().getId().toString()+p.getInfo().getName()+"结束");
		System.out.println(p.getState().getId().toString()+p.getInfo().getName()+"结束");
	}

}
