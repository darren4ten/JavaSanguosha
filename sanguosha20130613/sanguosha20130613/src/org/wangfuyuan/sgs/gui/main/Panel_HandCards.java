package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.DelayKitIF;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.data.types.Target;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;
import org.wangfuyuan.sgs.service.UsableCardCheckService;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 手牌区域
 * 
 * @author user
 * 
 */
public class Panel_HandCards extends JPanel implements RefreshbleIF {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7517229300815663345L;
	// 总面板的引用
	Panel_Main main;
	// 人物模型
	AbstractPlayer player;
	// 牌面板的集合
	List<Panel_Card> cards = new ArrayList<Panel_Card>();
	// 牌的模型的集合
	List<AbstractCard> cardsModule = new ArrayList<AbstractCard>();
	// 当前可出牌的集合
	List<AbstractCard> useList = new ArrayList<AbstractCard>();
	// 当前选中牌
	List<Panel_Card> selectedList = new ArrayList<Panel_Card>();
	// 可选择的牌数
	int selectLimit = 1;
	// 选择出牌的目标
	Target target = new Target(1);
	// 是否开启目标检查
	boolean targetCheck = true;
	// 出牌按钮
	UsePanel useP = new UsePanel("确定", 1);
	UsePanel cancelP = new UsePanel("取消", 2);
	UsePanel skipP = new UsePanel("弃牌", 3);
	// 手牌检测器
	UsableCardCheckService check = new UsableCardCheckService();

	// 构造器
	public Panel_HandCards(AbstractPlayer p, Panel_Main main) {
		this.setLayout(null);
		this.main = main;
		this.player = p;
		updateCards();

		this.add(useP);
		this.add(cancelP);
		this.add(skipP);
		useP.setLocation(Const_UI.CARD_WIDTH * 5 + Const_UI.BUTTON_OFFSET, 20);
		cancelP.setLocation(Const_UI.CARD_WIDTH * 5 + Const_UI.BUTTON_OFFSET,
				useP.getHeight() + useP.getY());
		skipP.setLocation(Const_UI.CARD_WIDTH * 5 + Const_UI.BUTTON_OFFSET,
				useP.getHeight() + cancelP.getHeight()
						+ Const_UI.BUTTON_OFFSET_SKIP);
	}

	/**
	 * 根据模型 刷新牌的组件
	 */
	public synchronized void updateCards() {
		cardsModule = this.player.getState().getCardList();
		// 将原先的标签清空
		for (int i = 0; i < cards.size(); i++) {
			this.remove(cards.get(i));
		}
		cards.clear();
		// 重新创建标签
		for (int i = 0; i < cardsModule.size(); i++) {
			cards.add(new Panel_Card(cardsModule.get(i), this, false));
		}
		for (int i = cardsModule.size() - 1; i >= 0; i--) {
			this.add(cards.get(i));
		}
		this.repaint();
		// 整理牌
		carding();
		// 根据人物当前状态，选择手牌的可用状态和按钮状态
		selectShowCardAndClick();
	}

	/**
	 * 绘制
	 * 
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制判定区
		if (!player.getState().getCheckedCardList().isEmpty()) {
			for (int i = 0; i < player.getState().getCheckedCardList().size(); i++) {
				DelayKitIF d = (DelayKitIF) player.getState()
						.getCheckedCardList().get(i);
				g.setColor(Color.white);
				g.drawString(d.getShowNameInPanel(), useP.getX() + 20 * i, 10);
			}
		}
	}

	/**
	 * 整理牌 如果超过5张就层叠显示
	 */
	public void carding() {
		int interval = Const_UI.CARD_WIDTH;
		if (cards.size() > 5) {
			interval = (Const_UI.CARD_WIDTH * 5 - Const_UI.CARD_WIDTH)
					/ (cards.size() - 1);
		}
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setLocation(i * interval, Const_UI.CARD_UP);

		}
	}

	/**
	 * 弃牌后，禁用所有牌
	 */
	public void unableToUseCard() {
		for (final Panel_Card pc : cards) {
			pc.setEnableToUse(false);
		}
		carding();
	}

	/**
	 * 显示所有能用的牌
	 */
	public void showAvailableCards() {
		// 获取可用手牌集合
		List<AbstractCard> listA = check.getAvailableCard(cardsModule, player);
		// 显示所有可用的牌
		for (Panel_Card pc : cards) {
			if (listA.contains(pc.getCard())) {
				pc.setEnableToUse(true);
			} else {
				pc.setEnableToUse(false);
			}
		}
		if (player.getStageNum() == PlayerIF.STAGE_THROWCRADS) {
			if (player.getState().getCardList().size() > player.getState()
					.getCurHP()) {
				selectLimit = player.getState().getCardList().size()
						- player.getState().getCurHP();
			}
		}
	}

	/**
	 * 选择手牌可用状态
	 */
	public void selectShowCardAndClick() {
		// 若不在自己回合，全禁用；否则显示可用的牌
		/*
		 * if (player.isSkip()) { unableToUseCard(); disableClick(); } else {
		 * showAvailableCards(); skipP.enableToUse(); }
		 */
		showAvailableCards();
		disableClick();
		if (player.getStageNum() == PlayerIF.STAGE_USECARDS)
			skipP.enableToUse();
	}

	/**
	 * 弃牌后 禁止所有按钮
	 */
	public void disableClick() {
		useP.unableToClick();
		cancelP.unableToClick();
		skipP.unableToClick();
		repaint();
	}

	/**
	 * 开放确定和取消
	 */
	public void enableOKAndCancel() {
		useP.enableToUse();
		cancelP.enableToUse();
	}

	/**
	 * 只开放取消
	 */
	public void enableCancel() {
		cancelP.enableToUse();
	}

	/**
	 * 出牌时期，只有取消和弃牌
	 */
	public void enableToClick() {
		// useP.enableToUse();
		cancelP.enableToUse();
		if (player.getStageNum() == PlayerIF.STAGE_USECARDS)
			skipP.enableToUse();
		repaint();
	}

	/**
	 * 提示出牌 无任何限制
	 */
	public void remindToUseALL() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setEnableToUse(true);
		}
		repaint();
	}

	/**
	 * 提示出牌 在响应其他玩家出的牌时，如果存在符合条件的 将牌抬起并且开放按钮 参数：匹配类型
	 */
	public void remindToUse(Integer... type) {
		List<Integer> types = Arrays.asList(type);
		for (int i = 0; i < cards.size(); i++) {
			int t = cards.get(i).getCard().getType();
			if (types.contains(t)) {
				cards.get(i).setEnableToUse(true);
				continue;
			}
		}
		cancelP.enableToUse();
		repaint();
	}

	/**
	 * 提示出牌 在响应其他玩家出的牌时，如果存在符合条件的 将牌抬起并且开放按钮 参数：匹配花色
	 */
	public void remindToUse(Colors... color) {
		List<Colors> listColor = Arrays.asList(color);
		for (int i = 0; i < cards.size(); i++) {
			Colors c = cards.get(i).getCard().getColor();
			if (listColor.contains(c)) {
				cards.get(i).setEnableToUse(true);
				continue;
			}
		}
		repaint();
	}

	/**
	 * 开放选择
	 */
	public void waitSelect(int limit) {
		// 设置可出的牌数量
		this.selectLimit = limit;
		// 显示可选择的牌
		selectShowCardAndClick();
	}

	/**
	 * ------------------------------------------------------------------------
	 * 该类中还包含了内部类按钮以及按钮的内部监听类 之所以这样是因为内部类的形式在变量访问上比较方便
	 * ----------------------------------------------------------------------
	 */

	/**
	 * 内部类 出牌的按钮
	 */
	class UsePanel extends JPanel {
		private static final long serialVersionUID = -5187604468743452501L;
		final int USE = 1;
		final int CANCEL = 2;
		final int SKIP = 3;
		UsePanel me = this;
		JLabel text = new JLabel();
		String name;

		Image imgEnable = ImgUtil.getPngImgByName("bok");
		Image imgUnable = ImgUtil.getPngImgByName("bok2");
		Image imgDown = ImgUtil.getPngImgByName("bend");
		Image curImg;
		MouseListener listener;

		// 构造器
		public UsePanel(String name, int type) {
			this.setSize(100, 45);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// 初始不可用
			this.unableToClick();
			Font f = new Font("楷体", Font.BOLD, 30);
			text.setFont(f);
			text.setForeground(Color.white);
			text.setText(name);
			this.add(text);
			switch (type) {
			case USE:
				listener = new Click();
				break;
			case CANCEL:
				listener = new cancelClick();
				break;
			case SKIP:
				listener = new skipClick();
				break;
			}
			this.addMouseListener(listener);
		}

		/**
		 * 禁用按钮
		 */
		public void unableToClick() {
			this.curImg = imgUnable;
			this.removeMouseListener(listener);
			this.setCursor(Cursor.getDefaultCursor());
			repaint();
		}

		/**
		 * 启用按钮
		 */
		public void enableToUse() {
			this.curImg = imgEnable;
			if (this.getMouseListeners().length == 0)
				this.addMouseListener(listener);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			repaint();
		}

		/**
		 * 绘制
		 */
		public void paint(Graphics g) {
			g.drawImage(curImg, 0, 0, this.getWidth(), this.getHeight(), null);
			super.paintChildren(g);
			g.dispose();
		}

		/**
		 * 内部类的内部类监听
		 * 
		 * @author user
		 * 
		 */
		class Click extends MouseAdapter {

			@Override
			public void mousePressed(MouseEvent e) {
				curImg = imgDown;
				me.repaint();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				curImg = imgEnable;
				me.repaint();
				clicked();
			}

			/*
			 * 鼠标点击事件
			 */
			private void clicked() {
				player.getState().setRes(Const_Game.OK);
				if(player.getState().getRes()==Const_Game.SKILL){
					player.getState().setRes(Const_Game.OK);
					return;
				}
				// 如果是被动响应的
				if (player.getState().isRequest()) {
					//getPlayer().getState().setRequest(false);
					//player.getRequest().clear();
					if (!getSelectedList().isEmpty()) {
						getPlayer().getState().setRes(
								getSelectedList().get(0).getCard().getType());
						return;
					}
				}
				unableToClick();
			}
		}

		/**
		 * 取消按钮的监听类 click的子类
		 * 
		 * @author user
		 * 
		 */
		class cancelClick extends Click {
			/*
			 * 重写点击事件
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				curImg = imgEnable;
				me.repaint();
				player.getState().setRes(Const_Game.CANCEL);
			}
		}

		/**
		 * 弃牌按钮 click的子类
		 * 
		 * @author user
		 * 
		 */
		class skipClick extends Click {
			/*
			 * 重写点击事件
			 */
			@Override
			public void mouseReleased(MouseEvent e) {

				curImg = imgEnable;
				if (selectedList != null) {
					for (Panel_Card p : cards) {
						if (p.isSelected) {
							p.unselect();
						}
					}
					selectedList.clear();
				}
				player.setSkip(true);
				disableClick();
			}
		}
	}

	/**
	 * 实现刷新方法
	 */
	@Override
	public void refresh() {
		// 更新手牌视图
		updateCards();
		// 清空所有状态
		selectedList.clear();
		setSelectLimit(1);
		// 清空选择
		target.setNeedCheck(true);
		target.setLimit(1);
		target.getList().clear();
		// 开启目标检测
		targetCheck = true;
		if (player.getStageNum() == PlayerIF.STAGE_THROWCRADS) {
			targetCheck = false;
		}
		repaint();
	}

	public List<AbstractCard> getUseList() {
		return useList;
	}

	public List<Panel_Card> getSelectedList() {
		return selectedList;
	}

	public AbstractPlayer getPlayer() {
		return player;
	}

	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}

	public int getSelectLimit() {
		return selectLimit;
	}

	public void setSelectLimit(int selectLimit) {
		this.selectLimit = selectLimit;
	}

	public List<Panel_Card> getCards() {
		return cards;
	}

	public void setCards(List<Panel_Card> cards) {
		this.cards = cards;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public UsePanel getUseP() {
		return useP;
	}

	public UsePanel getCancelP() {
		return cancelP;
	}

	public UsePanel getSkipP() {
		return skipP;
	}

	public Panel_Main getMain() {
		return main;
	}

	public boolean isTargetCheck() {
		return targetCheck;
	}

	public void setTargetCheck(boolean targetCheck) {
		this.targetCheck = targetCheck;
	}
}
