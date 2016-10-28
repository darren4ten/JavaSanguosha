package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 玩家控制面板
 * @author user
 *
 */

public class Panel_Control extends JPanel implements RefreshbleIF,PaintEffectIF{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1048990093893639234L;
	//人物模型
	AbstractPlayer player ;
	//头像区域
	Panel_HeadImg img;
	//手牌区域
	Panel_HandCards hand ;
	//装备区域
	Panel_Equipments eq;
	//总面板的引用
	Panel_Main main ;
	//背景图
	BufferedImage bgimg = ImgUtil.getJpgImgByName("bg_control");
	
	/**
	 * 构造器
	 * @param p
	 */
	public Panel_Control(AbstractPlayer p,Panel_Main main){
		this.main = main;
		this.player = p;
		//头像区域
		img = new Panel_HeadImg(p ,this);
		//手牌区域
		hand = new Panel_HandCards(p,main);
		//装备区域
		eq = new Panel_Equipments(p,this,18);
		
		this.setLayout(null);
		this.setSize(Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT/3-30);
		this.setLocation(0, Const_UI.FRAME_HEIGHT-this.getHeight()-30);
		//this.setBorder(BorderFactory.createLineBorder(Color.darkGray, 4));
		this.setOpaque(false);
		
		this.add(eq);
		//eq.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		eq.setSize(Const_UI.FRAME_WIDTH/6-20,this.getHeight()-50);
		eq.setLocation(20, 25);
		eq.setOpaque(false);
		
		this.add(img);
		img.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		img.setSize(this.getWidth()/5,this.getHeight());
		img.setLocation(Const_UI.FRAME_WIDTH - img.getWidth()-10, 0);
		img.setOpaque(false);
		
		this.add(hand);
		//hand.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		hand.setSize(eq.getX()+this.getWidth()-eq.getWidth()-img.getWidth(),this.getHeight());
		hand.setLocation(eq.getWidth()+20, 0);
		hand.setOpaque(false);
	}
	
	public void paint(Graphics g){
		if(bgimg!=null){
			g.drawImage(bgimg, 0, 0,Const_UI.FRAME_WIDTH,getHeight(), null);
		}
		super.paint(g);
	}
	
	public Panel_Control(){

	}
	
	/**
	 * 更新其他玩家面板
	 */
	public void playersRefresh(){
		List<Panel_Player> list = getMain().getPlayers();
		for (Panel_Player pp : list) {
			pp.setNormal();
		}
	}
	/**
	 * 实现刷新方法
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		img.refresh();
		hand.refresh();
		eq.refresh();
	}

	public Panel_HeadImg getImg() {
		return img;
	}

	public void setImg(Panel_HeadImg img) {
		this.img = img;
	}

	public Panel_HandCards getHand() {
		return hand;
	}

	public void setHand(Panel_HandCards hand) {
		this.hand = hand;
	}

	public Panel_Equipments getEq() {
		return eq;
	}

	public void setEq(Panel_Equipments eq) {
		this.eq = eq;
	}

	public Panel_Main getMain() {
		return main;
	}

	public void setMain(Panel_Main main) {
		this.main = main;
	}

	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}

	

	//获取人物模型
	public AbstractPlayer getPlayer() {
		return player;
	}

	@Override
	public Point getPaintPoint() {
		int x = getWidth()/2+getX();
		int y = getY();
		return new Point(x, y);
	}
}
