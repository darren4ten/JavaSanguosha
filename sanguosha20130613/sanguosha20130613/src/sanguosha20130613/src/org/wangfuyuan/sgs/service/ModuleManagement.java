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
 * ����ģ�͹����� ʹ�õ������� 
 * ����һ��ȫ�ֱ������Ϲ����� ��Ҳ��֪����������ѧ�� 
 * ��ǰ��˵һ��������õ�ϵͳ�ǲ�Ӧ�ó���ȫ�����
 * ȷʵ������������������˼��ķ�װ��
 * ���һ�����ô���ˣ�ԭ�����������Ǹо�����
 * 
 * @author user
 * 
 */
public class ModuleManagement {
	// ��������
	private static ModuleManagement instance;

	public static ModuleManagement getInstance() {
		if (instance == null) {
			instance = new ModuleManagement();
		}
		return instance;
	}

	/**
	 * ����
	 */
	public static void reset() {
		instance = new ModuleManagement();
	}

	/*
	 * �����α�
	 */
	int index = 0;
	/*
	 * ��������б�
	 */
	List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>();
	/*
	 * �ƶѼ���
	 */
	List<AbstractCard> cardList = new ArrayList<AbstractCard>(CardUtil
			.createCards());
	/*
	 * ���ƶѼ���
	 */
	List<AbstractCard> gcList = new ArrayList<AbstractCard>();
	/*
	 * ս����������
	 */
	Panel_Battlefield battle = Panel_Battlefield.getInstance();

	/*
	 * Ŀ�����
	 */
	Target target;

	/*
	 * ������ ��ʼ����Ҽ���
	 */
	private ModuleManagement() {
		init();
		createCharacter();
	}

	/**
	 * ��ʼ��
	 * 
	 * TODO
	 */
	private void init() {

	}

	/**
	 * ��ȡ���
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
	 * AI��λ
	 */
	private void setAI() {
		for (int i = 1; i <= 4; i++) {
			playerList.get(i).getState().setAI(true);
		}
	}

	/**
	 * ��������
	 */
	public void createCharacter() {
		playerList = Panel_Select.list;
		// �������¼ҹ�ϵ
		for (int i = 0; i < playerList.size(); i++) {
			if (i < 4) {
				playerList.get(i).setNextPlayer(playerList.get(i + 1));
			} else {
				playerList.get(i).setNextPlayer(playerList.get(0));
			}
			// ��ʼ��4����
			List<AbstractCard> list = new ArrayList<AbstractCard>();
			for (int j = 0; j < 4; j++) {
				list.add(getOneCard());
				// System.out.println(list.get(j).getName());
			}
			playerList.get(i).getState().setCardList(list);
		}
		// �������
		setId();
		// ����AI
		setAI();
		// ���Ը���
		testSet();
	}

	/**
	 * ���� ����
	 */
	private void testSet() {
		playerList.get(4).getState().getCardList().add(CardFactory.newCard(1,1,Colors.HEITAO,Const_Game.WUXIEKEJI));
		// �������� ��Ӳ�������
		List<AbstractCard> list = CardUtil.createTestCards();
		for (int i = 0; i < list.size(); i++) {
			playerList.get(0).getState().getCardList().add(list.get(i));
		}
	}

	/**
	 * �ƶ���ȡһ����
	 * 
	 * @return
	 */
	public AbstractCard getOneCard() {
		AbstractCard c = cardList.get(0);
		/*
		 * index++; if (index >= cardList.size()) { index = 0;
		 * System.out.println("һ�������꣺��ǰ�ƶ�������" + cardList.size()); }
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
	 * ����һ���ж���
	 * 
	 * @return
	 */
	public AbstractCard showOneCheckCard() {
		// ����һ��
		AbstractCard check = getOneCard();
		// ��ʾ
		ViewManagement.getInstance().printBattleMsg("�ж���Ϊ��" + check.toString());
		ModuleManagement.getInstance().getBattle().addOneCard(check);

		// �ƶ���ɾ����
		cardList.remove(check);
		// ˢ��
		// ModuleManagement.getInstance().getBattle().refresh();
		AbstractCard check2 = check;
		// ѯ�������˴����ж�
		for (int i = 0; i < playerList.size(); i++) {
			check2 = playerList.get(i).getAction().dealWithCheckCard(check2);
		}
		// ���������ж�
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
