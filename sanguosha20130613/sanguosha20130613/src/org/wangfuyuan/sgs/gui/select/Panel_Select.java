package org.wangfuyuan.sgs.gui.select;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.gui.Frame_Main;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.Player;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.util.ConfigFileReadUtil;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 选择武将人物的界面
 * 
 * @author user
 * 
 */
public class Panel_Select extends JPanel {

	private static final long serialVersionUID = -8505197231593797314L;
	final int grid = 5;
	//出战者
	public static List<AbstractPlayer> list = new ArrayList<AbstractPlayer>();
	// 背景图
	BufferedImage bgimg = ImgUtil.getJpgImgByName("bg");
	// 当前选择的边框
	Border border = BorderFactory.createLineBorder(Color.green, 5);
	// 显示区域
	JPanel showPanel = new JPanel();
	JScrollPane jsp;
	// 选择区
	MySelectPanel selectPanel = new MySelectPanel();
	Pane_ProxyPlayer[] pps = new Pane_ProxyPlayer[5];
	Pane_ProxyPlayer curPP;
	// 选择游标
	int index;
	//按钮面板
	ClickPanel cp1 = new ClickPanel("撤销",0);
	ClickPanel cp2 = new ClickPanel("出战",1);
	
	public Panel_Select() {
		this.setSize(Const_UI.FRAME_WIDTH, Const_UI.FRAME_HEIGHT);
		this.setLocation(0, 0);
		this.setLayout(null);
		// 可影响高度
		showPanel.setPreferredSize(new Dimension(Const_UI.PROXYWIDTH * 5,
				Const_UI.PROXYHEIGHT * 1));
		//可影响宽度
		showPanel.setLayout(new GridLayout(0, 7, 2, 2));
		showPanel.setBackground(Color.black);
		loadProxyPlayers();
		jsp = new JScrollPane(showPanel);
		jsp.setSize(Const_UI.PROXYWIDTH * 5 + 10, Const_UI.FRAME_HEIGHT - 300);
		jsp.setLocation(Const_UI.PROXYWIDTH/2+10, 10);
		jsp
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setOpaque(false);
		jsp.getVerticalScrollBar().setUnitIncrement(20);
		jsp.getHorizontalScrollBar().setBlockIncrement(80);
		TitledBorder tb = BorderFactory.createTitledBorder("点将台");
		tb.setTitlePosition(TitledBorder.TOP);
		Font font = new Font("楷体", Font.BOLD, 30);
		tb.setTitleFont(font);
		tb.setTitleColor(Color.white);
		jsp.setBorder(tb);
		System.out.println(jsp.getHorizontalScrollBar().getMinimum());
		jsp.getViewport().setOpaque(false);
		add(jsp);

		selectPanel.setSize(Const_UI.PROXYWIDTH * 6+20, Const_UI.PROXYHEIGHT + 50);
		selectPanel.setLocation(10, jsp.getHeight() + 20);
		selectPanel.setOpaque(false);
		TitledBorder tb2 = BorderFactory.createTitledBorder("参战者");
		tb2.setTitleFont(font);
		tb2.setTitleColor(Color.white);
		tb2.setBorder(null);
		//selectPanel.setBorder(tb2);
		selectPanel.setLayout(null);
		for (int i = 0; i < pps.length; i++) {
			pps[i] = new Pane_ProxyPlayer(null, this);
			selectPanel.add(pps[i]);
			if (i == 4) {
				pps[i].setLocation(selectPanel.getWidth() - pps[i].getWidth()
						- 20, 35);
			} else {
				pps[i].setLocation(Const_UI.PROXYWIDTH * i + 18, 35);
			}
		}

		
		cp1.setLocation(Const_UI.PROXYWIDTH * 4 + 18, 40);
		cp2.setLocation(Const_UI.PROXYWIDTH * 4 + 18,  selectPanel.getHeight()-cp2.getHeight()-20);
		cp2.disableUse();
		selectPanel.add(cp1);
		selectPanel.add(cp2);
		paintCurPP();
		add(selectPanel);
	}

	/*
	 * 绘制当前选择
	 */
	private void paintCurPP() {
		for (Pane_ProxyPlayer pp : pps) {
			pp.resetBorder();
		}
		pps[index].setBorder(border);
	}

	/*
	 * 加载武将
	 */
	private void loadProxyPlayers() {
		List<ProxyPlayer> list = ConfigFileReadUtil.getProxyPlayersFromXML();
		for (int i = 0; i < list.size(); i++) {
			Pane_ProxyPlayer pp = new Pane_ProxyPlayer(list.get(i), this);
			pp.addListener();
			showPanel.add(pp);
		}
	}

	/**
	 * 绘制
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(bgimg, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	/**
	 * 选择1个
	 */
	public void selectOne(ProxyPlayer player) {
		pps[index].setPp(player);
		pps[index].repaint();
		if (index < 4) {
			index++;
			paintCurPP();
		} else {
			// 开放确定
			cp2.enbaleUse();
		}
	}

	public Pane_ProxyPlayer getCurPP() {
		return curPP;
	}

	public void setCurPP(Pane_ProxyPlayer curPP) {
		this.curPP = curPP;
	}

	ActionListener redo2 = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {}
	};

	/**
	 * 按钮面板
	 */
	class ClickPanel extends JPanel {
		private static final long serialVersionUID = 6842906804807502608L;
		JLabel text = new JLabel();
		Image imgEnable = ImgUtil.getPngImgByName("bok");
		Image imgUnable = ImgUtil.getPngImgByName("bok2");
		Image imgDown = ImgUtil.getPngImgByName("bend");
		Image curImg;
		MouseListener ml;
		String name;

		public ClickPanel(String name, int type) {
			this.setSize(140, 60);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			Font f = new Font("楷体", Font.BOLD, 40);
			text.setFont(f);
			text.setForeground(Color.white);
			text.setText(name);
			this.add(text);
			if(type==0){
				ml = redo;
			}else{
				ml = finish;
			}
			enbaleUse();
		}

		public void enbaleUse() {
			curImg = imgEnable;
			if(getMouseListeners().length==0)addMouseListener(ml);
			repaint();
		}

		public void disableUse() {
			curImg = imgUnable;
			if(getMouseListeners().length>0)removeMouseListener(ml);
			repaint();
		}

		public void paint(Graphics g) {
			g.drawImage(curImg, 0, 0, this.getWidth(), this.getHeight(), null);
			super.paintChildren(g);
		}

		MouseListener redo = new MyClickListen() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (index == 0)
					return;
				if (index == 4) {
					if (pps[index].getPp() != null) {
						pps[index].setPp(null);
						pps[index].repaint();
					} else {
						index--;
						pps[index].setPp(null);
						pps[index].repaint();
						paintCurPP();
					}
					cp2.disableUse();
					return;
				}
				index--;
				pps[index].setPp(null);
				pps[index].repaint();
				paintCurPP();
			
			}
			
		};

		MouseListener finish = new MyClickListen() {

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				list=new ArrayList<AbstractPlayer>();
				for (int i = 0; i < pps.length; i++) {
					Pane_ProxyPlayer pp = pps[i];
					String id = pp.getPp().getId();
					AbstractPlayer p = new Player(id);
					list.add(0,p);
					System.out.println("加载"+p.getInfo().getName());
				}
				ModuleManagement.reset();
				Frame_Main.me.loadMain();
			}
			
		};

		/*
		 * 监听
		 */
		class MyClickListen extends MouseAdapter {

			@Override
			public void mouseReleased(MouseEvent e) {
				curImg = imgEnable;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				curImg = imgDown;
				repaint();
			}
		}
	}

	class MySelectPanel extends JPanel{
		private static final long serialVersionUID = -3707416772344129876L;

		public void paintComponent(Graphics g){
			g.drawImage(ImgUtil.getJpgImgByName("bg_control"),0, 0,this.getWidth(),this.getHeight(),null);
		}
	}
}
