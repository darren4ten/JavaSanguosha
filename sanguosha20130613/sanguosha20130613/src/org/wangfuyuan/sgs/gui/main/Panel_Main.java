package org.wangfuyuan.sgs.gui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.gui.Frame_Debug;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 主面板
 * @author user
 *
 */
public class Panel_Main extends JPanel {
	private static final long serialVersionUID = 5373403541536774127L;
	
	//玩家管理类
	ModuleManagement pmgr = ModuleManagement.getInstance();
	//控制栏面板
	Panel_Control contrl = new Panel_Control(pmgr.getPlayerList().get(0),this);
	//其他玩家的面板组
	List<Panel_Player>  players = new ArrayList<Panel_Player>();
	//战场面板
	Panel_Battlefield bf = Panel_Battlefield.getInstance();
	//背景图
	BufferedImage bgimg = ImgUtil.getJpgImgByName("bg");
	//游戏消息面板
	JTextArea msg = new JTextArea();
	JScrollPane jsp = new JScrollPane(msg);
	//聊天信息面板
	JTextArea msgChat = new JTextArea();
	JScrollPane jspChat = new JScrollPane(msgChat);
	//提示信息面板
	Panel_Prompt prompt = new Panel_Prompt();
	//战场消息显示面板
	Panel_Message message = new Panel_Message();
	//debug窗口
	Frame_Debug fDebug ;
	//是否结束
	boolean isGameOver;

	/*
	 * 构造
	 */
	public Panel_Main() {
		this.setSize(Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT);
		this.setLocation(0,0);
		this.setLayout(null);
		
		this.add(contrl);
		pmgr.getPlayerList().get(0).setPanel(contrl);
		//添加到全局刷新列表
		ViewManagement.getInstance().getRefreshList().add(contrl);
		//创建其他玩家面板
		for (int i = 0; i < pmgr.getPlayerList().size()-1; i++) {
			Panel_Player pp = new Panel_Player(pmgr.getPlayerList().get(i+1),this);
			players.add(pp);
			//设置玩家关联的面板
			pmgr.getPlayerList().get(i+1).setPanel(pp);
			this.add(players.get(i));
			//将玩家面板添加到管理类用来获取刷新通知
			ViewManagement.getInstance().getRefreshList().add(players.get(i));
			
		}
		setPosition();
		setMsgPanels();
		
		this.add(jsp);
		this.add(jspChat);
		this.add(prompt);
		ViewManagement.getInstance().setPrompt(prompt);
		this.add(message);
		this.add(bf);
		ViewManagement.getInstance().setMessage(message);
		
		PaintService.createMain(this);
		validate();
		
		//【看这里】
		//debug窗口，可以方便的查看牌堆中的牌和场上所有人的手牌
		//如果您有兴趣着手修改和扩展本项目，为了更方便的测试，建议启用下面这句
		//if(fDebug==null)fDebug = new Frame_Debug();
	}
	/**
	 * 设置各个消息面板
	 */
	private void setMsgPanels() {
		ViewManagement.getInstance().setMsg(msg);
		ViewManagement.getInstance().setMsgChat(msgChat);
		msg.setForeground(Color.white);
		msg.setEditable(false);
		msg.setOpaque(false);
		msg.setLineWrap(true);
		msgChat.setForeground(Color.white);
		msgChat.setEditable(false);
		msgChat.setOpaque(false);
		msgChat.setLineWrap(true);
	
		jsp.setLocation(Const_UI.FRAME_WIDTH/5*4+4, 5);
		jsp.setSize(180, Const_UI.PLAYER_PANEL_HEIGHT);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		TitledBorder tb1 =BorderFactory.createTitledBorder("游戏信息：");
		tb1.setTitleColor(Color.white);
		jsp.setBorder(tb1);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspChat.setLocation(Const_UI.FRAME_WIDTH/5*4+4, bf.getY());
		jspChat.setSize(180, Const_UI.PLAYER_PANEL_HEIGHT);
		jspChat.setOpaque(false);
		jspChat.getViewport().setOpaque(false);
		TitledBorder tb2 = BorderFactory.createTitledBorder("聊天信息：");
		tb2.setTitleColor(Color.white);
		jspChat.setBorder(tb2);
		jspChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		prompt.setLocation(contrl.getHand().getX(), contrl.getY()-prompt.getHeight());
		message.setLocation(bf.getX()+Const_UI.CARD_WIDTH-100, bf.getY()-20);
	}

	/**
	 * 其他面板的布局
	 */
	private void setPosition() {
		if(players.get(3)!=null)players.get(3).setLocation(0, 230);
		if(players.get(2)!=null)players.get(2).setLocation(Const_UI.FRAME_WIDTH/5*1, 0);
		if(players.get(1)!=null)players.get(1).setLocation(Const_UI.FRAME_WIDTH/5*2, 0);
		//if(players.get(0)!=null)players.get(0).setLocation(Const_UI.FRAME_WIDTH-Const_UI.PLAYER_PANEL_WIDTH-20, 200);
		if(players.get(0)!=null)players.get(0).setLocation(Const_UI.FRAME_WIDTH/5*3, 0);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(bgimg, 0, 0, this.getWidth(), this
				.getHeight(), null);
	}

	public void paint(Graphics g){
		if(isGameOver){
			drawUnable(g);
		}
		super.paint(g);
		
	}
	/*
	 * 绘制不可用
	 * 
	 * @param g
	 */
	private static void drawUnable(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.darkGray);
		g2.fillRect(0,0, Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT);
		g2.setComposite(AlphaComposite.SrcOver.derive(Const_UI.CARD_UNABLE));
	}
	/**
	 * 返回战场区域的引用
	 * @return
	 */
	public Panel_Battlefield getBf() {
		return bf;
	}
	public List<Panel_Player> getPlayers() {
		return players;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	
}
