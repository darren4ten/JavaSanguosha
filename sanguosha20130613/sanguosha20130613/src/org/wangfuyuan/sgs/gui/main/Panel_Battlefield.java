package org.wangfuyuan.sgs.gui.main;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.constant.Const_UI;

/**
 * 战场面板
 * 摆放各种牌等
 * @author user
 *
 */
public class Panel_Battlefield extends JPanel implements RefreshbleIF{

	private static final long serialVersionUID = 966033103314447302L;
	//打出牌的模型
	List<AbstractCard> cards = new ArrayList<AbstractCard>();
	//打出的牌的组件
	List<Panel_Card> cardList = new ArrayList<Panel_Card>();
	//打出牌的图像 
	List<Image> cardImgs = new ArrayList<Image>();
	//效果图
	Image effectImg ;
	/*
	 * 单例构造
	 */
	private static Panel_Battlefield instance ;
	private Panel_Battlefield(){
		this.setLayout(null);
		//this.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		this.setOpaque(false);
		this.setSize(Const_UI.FRAME_WIDTH/5*3,Const_UI.PLAYER_PANEL_HEIGHT+20);
		this.setLocation(Const_UI.FRAME_WIDTH/5, Const_UI.PLAYER_PANEL_HEIGHT+40);
	}
	public static Panel_Battlefield getInstance(){
		if(instance==null){
			instance = new Panel_Battlefield(); 
		}
		return instance;
	}
	
	/**
	 * 重绘
	 */
	public void paintComponent1(Graphics g){
		for (int i = 0; i < cards.size(); i++) {
			Image img = cards.get(i).showImg();
			if(img==null)System.out.println("null");
			g.drawImage(img, Const_UI.CARD_WIDTH*i, 20, null);
		}
	}
	
	/**
	 * 向战场丢出一张牌
	 */
	public void addOneCard(AbstractCard c){
		cards.add(c);	
		int i = cards.size()-1;
		Panel_Card pc = new Panel_Card(cards.get(i), null, false);
		this.add(pc);
		pc.setLocation(pc.getWidth()*i, 20);
		repaint();
		//showCard();
	}
	/**
	 * 显示牌
	 */
	public void showCard(){
		//根据cards，创建并绘制
		/*for (int i = 0; i < cards.size(); i++) {
			Panel_Card pc = new Panel_Card(cards.get(i), null, false);
			this.add(pc);
			pc.setLocation(pc.getWidth()*i, 20);
		}*/
		repaint();
	}
	
	/**
	 * 获得当前战场的牌组
	 * @return
	 */
	public List<AbstractCard> getCards() {
		return cards;
	}
	
	/**
	 * 清空战场
	 */
	public void clear(){
		cards.clear();
		this.removeAll();
		repaint();
	}
	
	/**
	 * 刷新
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		showCard();
	}
}
