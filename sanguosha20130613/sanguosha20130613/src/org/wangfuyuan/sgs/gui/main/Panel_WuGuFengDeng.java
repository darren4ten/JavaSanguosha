package org.wangfuyuan.sgs.gui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 【五谷丰登】的选择面板
 * 
 * @author user
 * 
 */
public class Panel_WuGuFengDeng extends JPanel {
	private static final long serialVersionUID = 5486612841656799384L;
	// 牌的显示尺寸
	private static final int CARDWIDTH = 100;
	private static final int CARDHEIGHT = 150;
	// 总面板引用
	JPanel me = this;
	// 嵌套面板
	JPanel jp_cards = new JPanel();
	// 牌面板的集合
	List<Card4select> cards = new ArrayList<Card4select>();
	// 选择者
	AbstractPlayer player;

	public Panel_WuGuFengDeng(AbstractPlayer firstPlayer) {
		this.player = firstPlayer;
		cerateUI();
		start();
	}

	/**
	 * 开始选择
	 */
	private void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				AbstractPlayer p = player;
				List<AbstractCard> list = new ArrayList<AbstractCard>();
				for (int i = 0; i < cards.size(); i++) {
					list.add(cards.get(i).c);
				}
				do {
					AbstractCard sc = p.toSelectCard(list);
					removePanel(sc);
					list.remove(sc);
					p.getAction().addCardToHandCard(sc);
					// p.refreshView();
					p = p.getNextPlayer();
					try {
						Thread.sleep(999);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (p != player);
				Panel_Main pm = (Panel_Main) me.getParent();
				pm.remove(me);
				pm.repaint();
				ViewManagement.getInstance().refreshAll();
			}
		}).start();
	}

	/*
	 * 根据牌，将对应的面板删除
	 */
	private void removePanel(AbstractCard c) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).c == c) {
				cards.get(i).beSelected();
			}
		}
	}

	private void cerateUI() {
		this.setLayout(null);
		GridLayout gl = new GridLayout(2, 4);
		gl.setHgap(10);
		jp_cards.setLayout(gl);
		jp_cards.setOpaque(false);
		jp_cards.setLocation(20, 20);
		this.setSize(CARDWIDTH * 5 + 50, CARDHEIGHT * 2 + 50);
		jp_cards.setSize(CARDWIDTH * 5, CARDHEIGHT * 2);

		for (int i = 0; i < 8; i++) {
			Card4select cs = null;
			if (i >= ModuleManagement.getInstance().getPlayerList().size()) {
				cs = new Card4select(null);
			} else {
				cs = new Card4select(ModuleManagement.getInstance()
						.getOneCard());
				cards.add(cs);
			}
			jp_cards.add(cs);
		}
		add(jp_cards);
	}

	/**
	 * 绘制背景
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(ImgUtil.getJpgImgByName("bg_selectcard"), 0, 0, getWidth(),
				getHeight(), null);
	}

	/**
	 * 显示牌的面板
	 */
	class Card4select extends JPanel {
		private static final long serialVersionUID = 4454271542267174856L;
		AbstractCard c;
		boolean finish;

		public Card4select(AbstractCard c) {
			this.setSize(CARDWIDTH, CARDHEIGHT);
			this.c = c;
			if (c != null) {
				this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				this.addMouseListener(ml);
				this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			}
		}

		/**
		 * 禁用
		 */
		public void disableIt() {
			setCursor(Cursor.getDefaultCursor());
			removeMouseListener(ml);
		}

		/**
		 * 已经被选择
		 */
		public void beSelected() {
			finish = true;
			this.repaint();
		}

		/**
		 * 绘制
		 */
		public void paint(Graphics g) {
			// super.paintComponent(g);
			if (finish) {
				drawUnable(g);
			}
			if (c != null) {
				g.drawImage(c.showImg(), 0, 0, getWidth(), getHeight(), null);
				// 绘制花色
				Image color = c.getColorImg();
				g.drawImage(color, 5, 5, 20, 20, null);
				if (c.getColor() == Colors.FANGKUAI
						|| c.getColor() == Colors.HONGXIN) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLACK);
				}
				g.setFont(new Font(g.getFont().getName(), Font.BOLD, 18));
				g.drawString(c.getNumberToString(), 7, 40);
			}
		}

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				finish = true;
				repaint();
				for (Card4select cs : cards) {
					cs.disableIt();
				}
				// player.getAction().addCardToHandCard(c);
				player.getState().setSelectCard(c);
			}

		};

		/**
		 * 绘制不可用
		 * 
		 * @param g
		 */
		private void drawUnable(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.darkGray);
			g2.fillRect(0, 0, CARDWIDTH + 20, CARDHEIGHT + 20);
			g2
					.setComposite(AlphaComposite.SrcOver
							.derive(Const_UI.CARD_UNABLE));
		}
	}
}
