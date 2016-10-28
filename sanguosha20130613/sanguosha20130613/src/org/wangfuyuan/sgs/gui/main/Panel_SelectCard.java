package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.VirtualCardIF;
import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 牌的选择面板
 * 在【过河拆桥】【顺手牵羊】等发动时候供玩家选择
 * 或者其他需要玩家选择的时候
 * @author user
 * 
 */
public class Panel_SelectCard extends JPanel {
	private static final long serialVersionUID = 7833183629398050914L;
	//牌的显示尺寸
	private static final int CARDWIDTH = 100;
	private static final int CARDHEIGHT = 150;
	//面板的类型，拆还是顺
	public static final int CHAI = 0;
	public static final int SHUN = 1;
	
	public static final int ONLY_EQ=3;
	public static final int ONLY_CHECK=4;
	public static final int ONLY_HAND=5;
	AbstractPlayer p_select ;
	AbstractPlayer p_target;
	JPanel jp_handCard = new JPanel();
	JPanel jp_eqCard = new JPanel();
	JPanel jp_checkCard = new JPanel();
	//类型拆还是顺
	int type ;
	/**
	 * 构造
	 * @param p_select
	 * @param p_target
	 */
	public Panel_SelectCard(AbstractPlayer p_select , AbstractPlayer p_target,int type) {
		this.p_select = p_select;
		this.p_target = p_target;
		this.type = type;
		this.setLayout(new GridLayout(3, 1));
		this.setSize(CARDWIDTH*4+30, CARDHEIGHT*3+50);
		createUI_hand();
		createUI_eq();
		createUI_check();
	}

	
	/**
	 * 重载构造，只需要手牌
	 */
	public Panel_SelectCard(AbstractPlayer p_select , AbstractPlayer p_target,int type,int cardType){
		this.p_select = p_select;
		this.p_target = p_target;
		this.type = type;
		this.setLayout(new GridLayout(1, 1));
		this.setSize(CARDWIDTH*4+30, CARDHEIGHT+50);
		switch(cardType){
		case ONLY_HAND:
			createUI_hand();
			break;
		case ONLY_EQ:
			createUI_eq();
			break;
		case ONLY_CHECK:
			break;
		}
	}
	/*
	 * 创建面板
	 */
	private void createUI_hand() {
		
		//手牌区域布局
		jp_handCard.setLayout(null);
		jp_handCard.setOpaque(false);
		int interval = CARDWIDTH;
		int size = p_target.getState().getCardList().size();
		if ( size > 4) {
			interval = (Const_UI.CARD_WIDTH * 4 - Const_UI.CARD_WIDTH)
					/ (size - 1);
		}
		for (int i = 0; i < size; i++) {
			UnknowCard uc = new UnknowCard(i);
			uc.setLocation(i * interval+10, 20);
			if(i>0){				
				jp_handCard.add(uc,0);
			}else{
				jp_handCard.add(uc);	
			}
		}
		this.add(jp_handCard);
		
	}
	//判定牌
	private void createUI_check() {
		jp_checkCard.setLayout(null);
		jp_checkCard.setOpaque(false);
		if(p_target.getState().getCheckedCardList().isEmpty()){
			this.setLayout(new GridLayout(2, 1));
			this.setSize(CARDWIDTH*4+30, CARDHEIGHT*2+50);
			return;
		}
		for (int i = 0; i<p_target.getState().getCheckedCardList().size(); i++) {
			Card4Select c4s = new Card4Select(p_target.getState().getCheckedCardList().get(i),false);
			c4s.setLocation(i*CARDWIDTH+10, 0);
			jp_checkCard.add(c4s);
		}
		this.add(jp_checkCard);
	}
	private void createUI_eq(){
		//装备牌布局
		jp_eqCard.setLayout(null);
		jp_eqCard.setOpaque(false);
		Card4Select[] ec = new Card4Select[4];
		AbstractCard c0 = p_target.getState().getEquipment().getWeapons();
		AbstractCard c1 = p_target.getState().getEquipment().getArmor();
		AbstractCard c2 = p_target.getState().getEquipment().getAttHorse();
		AbstractCard c3 = p_target.getState().getEquipment().getDefHorse();
		ec[0] = new Card4Select(c0,true);
		ec[1] = new Card4Select(c1,true);
		ec[2] = new Card4Select(c2,true);
		ec[3] = new Card4Select(c3,true);
		for (int i = 0; i < ec.length; i++) {
			ec[i].setLocation(i*CARDWIDTH+10, 10);
			jp_eqCard.add(ec[i]);
		}
		this.add(jp_eqCard);
	}
	/**
	 * 绘制背景
	 */
	public void paintComponent(Graphics g){
		g.drawImage(ImgUtil.getJpgImgByName("bg_selectcard"), 0, 0, getWidth(), getHeight(), null);
	}
	
	/**
	 * 未知手牌的显示面板
	 * @author user
	 *
	 */
	class UnknowCard extends JPanel {
		private static final long serialVersionUID = 2125016833830841675L;
		int index;
		public UnknowCard(int index){
			this.index = index;
			this.setSize(CARDWIDTH,CARDHEIGHT);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.addMouseListener(new Mouse() );
			this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		
		public void paintComponent(Graphics g){
			g.drawImage(ImgUtil.getJpgImgByName("bg_card"),0, 0, getWidth(), getHeight(), null);
		}
		
		class Mouse extends MouseAdapter{
			@Override
			public void mousePressed(MouseEvent e) {
				AbstractCard c = p_target.getState().getCardList().get(index);
				if(type==CHAI){
					ModuleManagement.getInstance().getBattle().addOneCard(c);
					c.gc();
				}else if(type == SHUN){
					p_select.getAction().addCardToHandCard(c);
				}
				p_target.getAction().removeCard(index);
				Panel_Main pm = (Panel_Main) getParent().getParent().getParent();
				pm.remove(getParent().getParent());
				pm.validate();
				pm.repaint();
				p_select.refreshView();
				p_target.refreshView();
				synchronized (p_select.getProcess()) {
					p_select.getProcess().notify();
				}
			}
		}
	}
	//---------------------------------
	/**
	 * 可选牌面板
	 */
	class Card4Select extends JPanel{
		private static final long serialVersionUID = -1524971965915633649L;
		boolean isEquipment;
		AbstractCard c;
		
		public Card4Select(AbstractCard c,boolean isEquipment){
			this.c = c;
			if(c instanceof VirtualCardIF){
				VirtualCardIF vc = (VirtualCardIF)c;
				this.c = vc.getRealCard();
			}
			this.setSize(CARDWIDTH,CARDHEIGHT);
			this.isEquipment = isEquipment;
			if(c!=null){
				this.addMouseListener(ml);
				this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		}
		//绘制
		public void paintComponent(Graphics g){
			if(c!=null){
				g.drawImage(c.showImg(),0, 0, getWidth(), getHeight(), null);
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
				Panel_Main pm = (Panel_Main) getParent().getParent().getParent();
				pm.remove(getParent().getParent());
				pm.validate();
				pm.repaint();
				if(type==CHAI){
					//此处与卸载事件重复所以判断下
					if(!(c instanceof AbstractEquipmentCard)){	
						sleep(300);
						ModuleManagement.getInstance().getBattle().addOneCard(c);						
						c.gc();
						ViewManagement.getInstance().printBattleMsg(p_select.getInfo().getName()+"丢弃"+c.toString());
					}
				}else if(type == SHUN){
					sleep(300);
					p_select.getAction().addCardToHandCard(c);
				}
				if(isEquipment){
					p_target.getAction().unloadEquipmentCard(c);
				}else{
					p_target.getState().getCheckedCardList().remove(c);
				}
				p_select.refreshView();
				p_target.refreshView();
			}
		};
	}
	
	private void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
