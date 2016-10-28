package org.wangfuyuan.sgs.gui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * 牌的面板
 * 
 * @author user
 * 
 */
public class Panel_Card extends JPanel implements RefreshbleIF {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7756121461123099368L;
	// 牌的模型
	AbstractCard card;
	// 手牌区
	Panel_HandCards ph;
	// 是否可用
	boolean enableToUse;
	// 是否被选取
	boolean isSelected = false;
	// 鼠标监听
	MouseListener listener;

	/**
	 * 构造
	 * 
	 * @param card
	 * @param p
	 * @param click
	 */
	public Panel_Card(AbstractCard card, Panel_HandCards p, boolean click) {
		this.ph = p;
		this.card = card;
		this.setSize(Const_UI.CARD_WIDTH, Const_UI.CARD_HEIGHT);
		this.setOpaque(false);
		listener = new Mouse(this);
		if (click) {
			this.setEnableToUse(true);
			// this.addMouseListener(listener);
			this.requestFocus();
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * 重载构造
	 */
	public Panel_Card(AbstractCard c, int width, int height, boolean enable) {
		this.card = c;
		this.setSize(width, height);
		this.enableToUse = enable;
	}

	// 绘制牌面及花色
	public void paintComponent(Graphics g) {
		if (!enableToUse) {
			drawUnable(g);
		}
		if (card != null) {
			g.drawImage(card.showImg(), 0, 0, null);
			Image color = card.getColorImg();
			g.drawImage(color, 5, 5, 20, 20, null);
			if (card.getColor() == Colors.FANGKUAI
					|| card.getColor() == Colors.HONGXIN) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLACK);
			}
			g.setFont(new Font(g.getFont().getName(), Font.BOLD, 18));
			g.drawString(card.getNumberToString(), 7, 40);
		}
	}

	/**
	 * 绘制不可用
	 * 
	 * @param g
	 */
	private void drawUnable(Graphics g) {
		/*
		 * BufferedImage image = new BufferedImage(getWidth(), getHeight(),
		 * BufferedImage.TYPE_INT_ARGB);
		 */
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.darkGray);
		g2.fillRect(0, 0, card.showImg().getWidth(null), card.showImg()
				.getHeight(null));
		//g2.fillRect(0, 0, Const_UI.CARD_WIDTH, Const_UI.CARD_HEIGHT);
		g2.setComposite(AlphaComposite.SrcOver.derive(Const_UI.CARD_UNABLE));
	}

	/**
	 * 设置是否可使用
	 * 
	 * @param b
	 */
	public void setEnableToUse(boolean b) {
		if (this.enableToUse == b)
			return;
		this.enableToUse = b;
		//重绘提前到这里调用，加快界面显示
		repaint();
		//后台相关新启用线程
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (enableToUse) {
					if (getMouseListeners().length == 0)
						addMouseListener(listener);
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				} else {
					if (getMouseListeners().length > 0)
						removeMouseListener(listener);
					setCursor(Cursor.getDefaultCursor());
				}
			}
		}).start();
		
	}

	/**
	 * 牌被点击选择 --牌抬起 --根据牌的使用目标类型，确定目标选择 --如果是aoe 或 无目标锦囊 则开启按钮 --如果需要目标
	 * 则高亮所有可选择的玩家 等选中后再开启按钮
	 */
	public void select() {
		// 添加到选择手牌列表
		ph.getSelectedList().add(this);
		// 超载则先进先出剔除
		if (ph.selectedList.size() > ph.getSelectLimit()) {
			ph.selectedList.get(0).unselect();
		}
		// 清空选择目标玩家列表
		ph.getTarget().getList().clear();
		this.setLocation(this.getX(), this.getY() - 50);
		this.setSelected(true);
		ph.useP.enableToUse();
		// 如果不需要选择目标
		if (!ph.targetCheck) {
			return;
		}
		// 根据ph的target的设置，设置开放选择的玩家
		/*
		 * if (!ph.getTarget().isNeedCheck()) { // 开放选择
		 * SwingUtilities.invokeLater(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * List<Panel_Player> list = ph.main.players; for (Panel_Player pp :
		 * list) { if (!pp.getPlayer().getState().isDead()) { pp.enableToUse();
		 * } }
		 * 
		 * } }); } else { }
		 */

		cardUseTargetCheck(ph.getSelectedList().get(0).getCard());
		card.targetCheck(ph);
		System.out.println("完成目标检测");
	}

	/*
	 * 根据选中的牌，来判断哪些玩家符合被选择
	 */
	private void cardUseTargetCheck(AbstractCard card) {
		// 遍历 检测
		List<Panel_Player> list = ph.main.players;
		for (int i = 0;i<list.size();i++) {
			Panel_Player pp = list.get(i);
			// 如果死了
			if (pp.getPlayer().getState().isDead()) {
				pp.disableToUse();
				continue;
			}
			// 如果免疫
			if (pp.getPlayer().getState().getImmuneCards().contains(
					card.getType())) {
				pp.disableToUse();
				continue;
			}
			/*// 如果需要射程
			if (card.isNeedRange()
					&& !card.isInRange(ph.player, pp.getPlayer())) {
				pp.disableToUse();
				continue;
			}*/
			pp.enableToUse();
		}
	}

	/*
	 * 取消选择
	 */
	public void unselect() {
		// 牌放下
		this.setLocation(this.getX(), this.getY() + 50);
		this.setSelected(false);
		repaint();
		// 清空当前选择
		ph.getSelectedList().remove(this);
		// 清空选择的目标
		// ModuleManagement.getInstance().setTarget(null);
		// 清空提示消息
		ViewManagement.getInstance().getPrompt().clear();
		if (!ph.getSelectedList().isEmpty())
			return;
		
		ph.getTarget().setLimit(1);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<Panel_Player> list = ph.main.players;
				if (ph.targetCheck) {
					// 禁止点击其他玩家
					for (Panel_Player pp : list) {
						pp.setNormal();
					}
				} else {
					// 取消被选择玩家的状态
					for (Panel_Player pp : list) {
						if (pp.getPanelState() == Panel_Player.SELECTED)
							pp.enableToUse();
					}
				}
			}
		});
	
		// 如果选择列表为空则禁止点击
		if (ph.selectedList == null || ph.selectedList.size() == 0) {
			ph.useP.unableToClick();
			// ph.cancelP.unableToClick();
		}
	
		
	}

	// 鼠标监听 内部类
	class Mouse extends MouseAdapter {
		Panel_Card pc;

		public Mouse(Panel_Card p) {
			this.pc = p;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// System.out.println("click");
			if (pc.isSelected()) {
				pc.unselect();
			} else {
				pc.select();
			}
		}
	}

	/**
	 * 刷新
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public AbstractCard getCard() {
		return card;
	}

	public void setCard(AbstractCard card) {
		this.card = card;
	}

}
