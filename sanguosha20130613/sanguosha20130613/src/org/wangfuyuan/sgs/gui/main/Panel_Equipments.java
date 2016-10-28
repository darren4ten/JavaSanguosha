package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;
import org.wangfuyuan.sgs.card.equipment.ActiveSkillWeaponCardIF;
import org.wangfuyuan.sgs.data.types.EquipmentStructure;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 装备区域面板 -- 武器 -- 防具 -- +1马 -- -1马
 * 
 * @author user
 * 
 */
public class Panel_Equipments extends JPanel implements RefreshbleIF {
	private static final long serialVersionUID = -5043769932933498212L;
	// private static final String[] NUMBER= {"一","二","三","四","五"};
	AbstractPlayer p;
	Panel_Control pc;
	EquipmentStructure eqs;
	// int fontSize;
	CardEquipment att;
	CardEquipment dun;
	CardEquipment ma;
	CardEquipment _ma;
	BufferedImage img = ImgUtil.getJpgImgByName("bd_eq");

	public Panel_Equipments(AbstractPlayer p, Panel_Control pc, int fontSize) {
		this.p = p;
		this.pc = pc;
		eqs = this.p.getState().getEquipment();
		this.setLayout(new GridLayout(4, 1));
		this.setOpaque(false);
		att = new CardEquipment(eqs.getWeapons(), 1, fontSize);
		dun = new CardEquipment(eqs.getArmor(), 2, fontSize);
		ma = new CardEquipment(eqs.getAttHorse(), 3, fontSize);
		_ma = new CardEquipment(eqs.getDefHorse(), 4, fontSize);
		this.add(att);
		this.add(dun);
		this.add(_ma);
		this.add(ma);
	}

	/**
	 * 实现刷新方法
	 */
	@Override
	public void refresh() {
		update();
		repaint();
	}

	/**
	 * 更新数据
	 */
	private void update() {
		att.card = p.getState().getEquipment().getWeapons();
		dun.card = p.getState().getEquipment().getArmor();
		ma.card = p.getState().getEquipment().getDefHorse();
		_ma.card = p.getState().getEquipment().getAttHorse();
		enableUseWeapons();
	}

	/**
	 * 开启武器
	 */
	public void enableUseWeapons() {
		if (att.card != null) {
			att.enbaleUse();
		}
	}

	/**
	 * 绘制背景
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(ImgUtil.getJpgImgByName("bg_eq"), 0, -1, getWidth(),
				getHeight(), null);
	}

	/**
	 * 单张装备牌在装备栏的显示板
	 * 
	 * @author user
	 * 
	 */
	class CardEquipment extends JPanel {
		private static final long serialVersionUID = 3981675201544613463L;
		AbstractEquipmentCard card;
		Font font;
		Border border = BorderFactory.createLineBorder(Color.gray, 1);
		Border border_select = BorderFactory.createLineBorder(Color.red, 1);
		int type;
		boolean isSelected;

		/**
		 * 构造
		 */
		public CardEquipment(AbstractEquipmentCard c, int type, int size) {
			this.type = type;
			// this.setBorder(BorderFactory.createLineBorder(Color.green, 1));
			/*
			 * Border inner = BorderFactory.createEtchedBorder(1); Border boder
			 * = BorderFactory.createCompoundBorder(null, inner);
			 */
			this.setBorder(border);
			this.setOpaque(false);
			font = new Font("楷体", Font.BOLD, size);
		}

		/**
		 * 绘制
		 */
		public void paint(Graphics g) {
			// g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
			if (card == null)
				return;
			g.setFont(font);
			g.setColor(Color.gray);
			// 绘制花色
			// g.drawImage(card.getColorImg(), this.getWidth()/5,
			// 10,this.getHeight()/2,this.getHeight()/2, null);
			g.drawImage(card.getColorImg(), this.getWidth() / 5, 8, font
					.getSize(), font.getSize(), null);
			// 绘制数值
			g.drawString(card.getNumberToString(), this.getWidth() / 5 * 2 - 8,
					this.getHeight() / 3 * 2 + 5);
			// 绘制名称
			g.drawString(card.getName(), this.getWidth() / 2 - 8, this
					.getHeight() / 3 * 2 + 5);
			super.paintBorder(g);
		}

		/**
		 * 开启
		 */
		public void enbaleUse() {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (getMouseListeners().length == 0)
				addMouseListener(ml);
			setBorder(border);
			isSelected = false;
		}

		/*
		 * 监听
		 */
		MouseListener ml = new MouseAdapter() {
			ActiveSkillWeaponCardIF asc;
			@Override
			public void mousePressed(MouseEvent e) {
				if(!(card instanceof ActiveSkillWeaponCardIF)){
					return;
				}else{
					asc = (ActiveSkillWeaponCardIF) card;
				}
				if (!isSelected) {
					if(!asc.onClick_open(pc.hand))return;
					setBorder(border_select);
					isSelected = true;
				} else {
					setBorder(border);
					isSelected = false;
					asc.onClick_close(pc.hand);
					pc.refresh();
				}
				repaint();
			}

		};
	}
}
