package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 诸葛亮【观星】面板
 * 注：此面板是硬编码空布局
 * @author user
 *
 */
public class Panel_GuanXing extends JPanel{
	private static final long serialVersionUID = 916877278591539946L;
	private static final Border BD_NORMAL = BorderFactory.createLineBorder(Color.darkGray,2);
	private static final Border BD_SELECT = BorderFactory.createLineBorder(Color.red,2);
	// 牌的显示尺寸
	private static final int CARDWIDTH = 100;
	private static final int CARDHEIGHT = 150;
	//需要处理的牌堆
	List<AbstractCard> cardList ;
	//当前人数
	int n;
	//上层的牌面板集合
	List<Panel_Card> upList = new ArrayList<Panel_Card>();
	//下层的面板集合
	List<Panel_Card> downList = new ArrayList<Panel_Card>();
	//当前选中的面板
	Panel_Card curPanel ;
	//主面板引用
	Panel_Main pm;
	public Panel_GuanXing(Panel_Main pm){
		this.pm = pm;
		getCards();
		createUI();
	}
	/*
	 * 抽取N张牌
	 */
	private void getCards(){
		cardList = new ArrayList<AbstractCard>();
		//获取当前人数
		n = ModuleManagement.getInstance().getPlayerList().size();
		n = n>=5?5:n;
		for (int i = 0; i < n; i++) {
			cardList.add(ModuleManagement.getInstance().getOneCard());
		}
	}
	
	/*
	 * 创建UI
	 */
	private void createUI(){
		this.setLayout(null);
		this.setSize(CARDWIDTH * 5 + 50, CARDHEIGHT * 2 + 50);
		//绘制取出的牌
		for (int i = 0; i < n; i++) {
			Panel_Card pc = new Panel_Card(cardList.get(i), CARDWIDTH, CARDHEIGHT-10, true);
			pc.setLocation((CARDWIDTH+5)*i+10, 20);
			pc.addMouseListener(new MouseClick(pc));
			pc.setBorder(BD_NORMAL);
			upList.add(pc);
			add(pc);
		}
		//绘制底层n张
		for (int i = 0; i < n; i++) {
			Panel_Card pc = new Panel_Card(null, CARDWIDTH, CARDHEIGHT-10, true);
			pc.setLocation((CARDWIDTH+5)*i+10, CARDHEIGHT+20);
			pc.addMouseListener(new MouseClick(pc));
			pc.setBorder(BD_NORMAL);
			downList.add(pc);
			add(pc);
		}
	}
	
	/**
	 * 完成观星，将牌按设置好的顺序放入牌堆
	 */
	public void finish(){
		//上层插进牌堆上
		List<AbstractCard> list = new ArrayList<AbstractCard>();
		for (int i = 0; i < upList.size(); i++) {
			AbstractCard c = upList.get(i).getCard();
			if(c!=null){
				list.add(c);
			}
		}
		ModuleManagement.getInstance().getCardList().addAll(0, list);
		//下层填入牌堆
		list = new ArrayList<AbstractCard>();
		for (int i = 0; i < downList.size(); i++) {
			AbstractCard c = downList.get(i).getCard();
			if(c!=null){
				list.add(c);
			}
			ModuleManagement.getInstance().getCardList().addAll(list);
		}
		pm.remove(this);
		pm.validate();
		pm.repaint();
	}
	/**
	 * 绘制背景
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(ImgUtil.getJpgImgByName("bg_selectcard"), 0, 0, getWidth(),
				getHeight(), null);
	}
	
	/**
	 * 内部鼠标监听类
	 * @author user
	 *
	 */
	class MouseClick extends MouseAdapter{
		Panel_Card pc ;
		public MouseClick(Panel_Card pc){
			this.pc = pc;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(curPanel==null){
				curPanel = pc;
				pc.setBorder(BD_SELECT);
			}else{
				//交换2张牌
				AbstractCard c = pc.getCard();
				pc.setCard(curPanel.getCard());
				curPanel.setCard(c);
				//清空状态
				curPanel.setBorder(BD_NORMAL);
				pc.setBorder(BD_NORMAL);
				curPanel =null;
			}
			repaint();
		}
	}
}
