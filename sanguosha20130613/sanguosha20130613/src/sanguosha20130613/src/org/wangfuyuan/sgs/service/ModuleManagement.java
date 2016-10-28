package org.wangfuyuan.sgs.service;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.CardFactory;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.data.enums.Identity;
import org.wangfuyuan.sgs.data.types.Target;
import org.wangfuyuan.sgs.gui.main.Panel_Battlefield;
import org.wangfuyuan.sgs.gui.select.Panel_Select;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.CardUtil;

/**
 * 数据模型管理类 使用单例构造 
 * 用作一个全局变量集合管理器 我也不知道这样做科学否 
 * 有前辈说一个设计良好的系统是不应该出现全局类的
 * 确实，这样会打破面向对象思想的封装性
 * 但我还是这么做了，原因无他，就是感觉方便
 * 
 * @author user
 * 
 */
public class ModuleManagement {
	// 单例构造
	private static ModuleManagement instance;

	public static ModuleManagement getInstance() {
		if (instance == null) {
			instance = new ModuleManagement();
		}
		return instance;
	}

	/**
	 * 重置
	 */
	public static void reset() {
		instance = new ModuleManagement();
	}

	/*
	 * 发牌游标
	 */
	int index = 0;
	/*
	 * 所有玩家列表
	 */
	List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>();
	/*
	 * 牌堆集合
	 */
	List<AbstractCard> cardList = new ArrayList<AbstractCard>(CardUtil
			.createCards());
	/*
	 * 弃牌堆集合
	 */
	List<AbstractCard> gcList = new ArrayList<AbstractCard>();
	/*
	 * 战场面板的引用
	 */
	Panel_Battlefield battle = Panel_Battlefield.getInstance();

	/*
	 * 目标对象
	 */
	Target target;

	/*
	 * 构造器 初始化玩家集合
	 */
	private ModuleManagement() {
		init();
		createCharacter();
	}

	/**
	 * 初始化
	 * 
	 * TODO
	 */
	private void init() {

	}

	/**
	 * 抽取身份
	 * 
	 */
	private void setId() {
		playerList.get(0).getState().setId(Identity.ZHUGONG);
		playerList.get(1).getState().setId(Identity.ZHONGCHEN);
		playerList.get(2).getState().setId(Identity.NEIJIAN);
		playerList.get(3).getState().setId(Identity.FANZEI);
		playerList.get(4).getState().setId(Identity.FANZEI);
	}

	/**
	 * AI就位
	 */
	private void setAI() {
		for (int i = 1; i <= 4; i++) {
			playerList.get(i).getState().setAI(true);
		}
	}

	/**
	 * 创建人物
	 */
	public void createCharacter() {
		playerList = Panel_Select.list;
		// 设置上下家关系
		for (int i = 0; i < playerList.size(); i++) {
			if (i < 4) {
				playerList.get(i).setNextPlayer(playerList.get(i + 1));
			} else {
				playerList.get(i).setNextPlayer(playerList.get(0));
			}
			// 初始给4张牌
			List<AbstractCard> list = new ArrayList<AbstractCard>();
			for (int j = 0; j < 4; j++) {
				list.add(getOneCard());
				// System.out.println(list.get(j).getName());
			}
			playerList.get(i).getState().setCardList(list);
		}
		// 设置身份
		setId();
		// 设置AI
		setAI();
		// 测试给牌
		testSet();
	}

	/**
	 * 测试 给牌
	 */
	private void testSet() {
		playerList.get(4).getState().getCardList().add(CardFactory.newCard(1,1,Colors.HEITAO,Const_Game.WUXIEKEJI));
		// 根据配置 添加测试用牌
		List<AbstractCard> list = CardUtil.createTestCards();
		for (int i = 0; i < list.size(); i++) {
			playerList.get(0).getState().getCardList().add(list.get(i));
		}
	}

	/**
	 * 牌堆中取一张牌
	 * 
	 * @return
	 */
	public AbstractCard getOneCard() {
		AbstractCard c = cardList.get(0);
		/*
		 * index++; if (index >= cardList.size()) { index = 0;
		 * System.out.println("一轮牌用完：当前牌堆数量：" + cardList.size()); }
		 */
		cardList.remove(c);
		if (cardList.isEmpty()) {
			List<AbstractCard> newCards = new ArrayList<AbstractCard>();
			for (AbstractCard card : gcList) {
				newCards.add(card);
			}
			cardList.addAll(newCards);
			gcList.clear();
		}
		return c;
	}

	/**
	 * 翻出一张判定牌
	 * 
	 * @return
	 */
	public AbstractCard showOneCheckCard() {
		// 翻出一张
		AbstractCard check = getOneCard();
		// 显示
		ViewManagement.getInstance().printBattleMsg("判定牌为：" + check.toString());
		ModuleManagement.getInstance().getBattle().addOneCard(check);

		// 牌堆中删除此
		cardList.remove(check);
		// 刷新
		// ModuleManagement.getInstance().getBattle().refresh();
		AbstractCard check2 = check;
		// 询问所有人处理判定
		for (int i = 0; i < playerList.size(); i++) {
			check2 = playerList.get(i).getAction().dealWithCheckCard(check2);
		}
		// 返回最后的判定
		return check2;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<AbstractCard> getCardList() {
		return cardList;
	}

	public void setCardList(List<AbstractCard> cardList) {
		this.cardList = cardList;
	}

	public Panel_Battlefield getBattle() {
		return battle;
	}

	public void setBattle(Panel_Battlefield battle) {
		this.battle = battle;
	}

	public List<AbstractPlayer> getPlayerList() {
		return playerList;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public void setPlayerList(List<AbstractPlayer> playerList) {
		this.playerList = playerList;
	}

	public List<AbstractCard> getGcList() {
		return gcList;
	}

	public void setGcList(List<AbstractCard> gcList) {
		this.gcList = gcList;
	}

}
