package org.wangfuyuan.sgs.gui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.card.DelayKitIF;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.types.Target;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.PlayerIF;
import org.wangfuyuan.sgs.util.ImgUtil;


/**
 * 其他玩家的显示面板
 * 
 * @author user
 * 
 */
public class Panel_Player extends JPanel implements RefreshbleIF,PaintEffectIF {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6279313069197776880L;
	// 总面板的引用
	Panel_Main main;
	// 对应的玩家模型
	AbstractPlayer player;
	//监听
	MouseListener listener ;
	// 血条
	Panel_HP hp;
	// 人物肖像
	JLabel jl_img = new JLabel();
	// 身份板
	Panel_Id pn_id = new Panel_Id(this);
	// 手牌板
	CardNum num;
	// 装备栏
	Panel_Equipments pn_eq ;
	// 人物图像
	BufferedImage img;
	BufferedImage bfimg;
	// 面板状态
	public static final int DEAD = -1;
	public static final int NORMAL = 0;
	public static final int DISABLE = 1;
	public static final int SELECTED = 2;
	int PanelState;
	// 效果状态
	public static final int DOING = 3;
	public static final int HURT = 4;
	
	int effectState;

	/**
	 * 构造器
	 * 
	 * @param p
	 * @param main
	 */
	public Panel_Player(AbstractPlayer p, Panel_Main main) {
		this.main = main;
		this.player = p;
		this.setSize(Const_UI.PLAYER_PANEL_WIDTH, Const_UI.PLAYER_PANEL_HEIGHT);
		this.setLayout(null);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//this.setBackground(Color.gray);
		this.setOpaque(false);
		//注册监听
		this.listener = new ClickPlayer();
		//this.addMouseListener(new Click());
		img = (BufferedImage) p.getInfo().getHeadImg();
		bfimg = img.getSubimage(0, 0, img.getWidth(), img.getHeight() / 2);
		//身份面板
		pn_id.setLocation(getWidth() - pn_id.getWidth(), 0);
		this.add(pn_id);
		//血条
		hp = new Panel_HP(p);
		this.add(hp);
		hp.setLocation(this.getWidth() - hp.getWidth(), this.getHeight() / 3);
		//人物肖像
		this.add(jl_img);
		//装备栏
		pn_eq = new Panel_Equipments(p,null,16);
		pn_eq.setSize(getWidth()-35, getHeight()/2);
		pn_eq.setLocation(18, getHeight()/2-10);
		this.add(pn_eq);
		//手牌数
		num = new CardNum();
		this.add(num);
		//初始可用
		enableToUse();
	}
	/**
	 * 绘制
	 */
	public void paint(Graphics g) {
		//super.paint(g);
		//如果死亡
		if(player.getState().isDead()){
			drawDead(g,Const_UI.PANEL_UNABLE);
			PanelState = DEAD;
			pn_id.showAfterDead();
		}
		//如果在回合中
		if(player.getStageNum()!=PlayerIF.STAGE_END){
			g.drawImage(ImgUtil.getPngImgByName("bd_cur"), 0, 0, this.getWidth(),this.getHeight(),null);
		}
		//如果在响应
		if(player.getState().isRequest()){
			g.drawImage(ImgUtil.getPngImgByName("bd_select"), 0, 0, this.getWidth(),this.getHeight(),null);
		}
		//绘制选择边框
		switch (PanelState) {
		case NORMAL:
			break;
		case SELECTED:
			g.drawImage(ImgUtil.getPngImgByName("bd_select"), 0, 0, this.getWidth(),this.getHeight(),null);
			break;
		case DEAD:
			g.drawImage(ImgUtil.getPngImgByName("bd_dead"), 0, 0, this.getWidth(),this.getHeight(),null);
		case DISABLE:
			drawDead(g,.5f);
		}
		//绘制人物图像
		g.drawImage(bfimg, 10, 20, this.getWidth() - 20, this.getHeight() / 3,
				null);
		g.drawImage(ImgUtil.getPngImgByName("headborder"), 0, 0, this
				.getWidth(), this.getHeight(), null);
		//绘制子组件
		super.paintChildren(g);
		//绘制判定区
		if(!player.getState().getCheckedCardList().isEmpty()){
			for (int i = 0; i < player.getState().getCheckedCardList().size(); i++) {
			DelayKitIF d = (DelayKitIF) player.getState().getCheckedCardList().get(i);
			g.setColor(Color.white);
			g.drawString(d.getShowNameInPanel(), 30+getFont().getSize()*i, getHeight()-3);
			}
		}
		
		g.dispose();
	}
	/**
	 * 禁用
	 */
	public void disableToUse(){
		if(PanelState==DEAD)return;
		this.PanelState = DISABLE;
		this.removeMouseListener(listener);
		this.setCursor(Cursor.getDefaultCursor());
		repaint();
	}
	/**
	 * 常态化
	 */
	public void setNormal(){
		if(PanelState==DEAD)return;
		this.PanelState = NORMAL;
		this.removeMouseListener(listener);
		this.setCursor(Cursor.getDefaultCursor());
		repaint();
	}
	/**
	 * 可用
	 */
	public void enableToUse(){
		if(PanelState==DEAD)return;
		this.PanelState = NORMAL;
		if(getMouseListeners().length==0)this.addMouseListener(listener);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		repaint();
	}
	/**
	 * 实现刷新方法
	 */
	@Override
	public void refresh() {
		this.PanelState = NORMAL;
		repaint();
		hp.refresh();
		num.repaint();
		pn_eq.refresh();
		//disableToUse();
	}
	
	/**
	 * 绘制死亡状态
	 * @return
	 */
	public void drawDead(Graphics g,float f){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.darkGray);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.setComposite(AlphaComposite.SrcOver.derive(f));
	}
	// 获取人物模型
	public AbstractPlayer getPlayer() {
		return player;
	}

	/*
	 * 内部类 鼠标点击监听
	 */
	class ClickPlayer extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			main.contrl.hand.getTarget().add(player);
			//ModuleManagement.getInstance().getTarget().add(player);
			System.out.println("select" + player.getInfo().getName());
			for (Panel_Player p : main.players) {
				if(p.PanelState==DISABLE){
					continue;
				}
				p.PanelState = NORMAL;
				p.repaint();
				//p.disableToUse();
			}
			Target tg = main.contrl.hand.getTarget();
			for (int i = 0; i <tg.getList().size() ; i++) {
				Panel_Player pp = (Panel_Player) tg.getList().get(i).getPanel();
				pp.setPanelState(SELECTED);
				pp.repaint();
			}
		}
	}

	/*
	 * 内部类 显示玩家手牌数
	 */
	class CardNum extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 592012384557111860L;
		Image backImg = ImgUtil.getPngImgByName("cardNum");
		int num = player.getState().getCardList().size();
		JLabel jl = new JLabel(String.valueOf(num));

		public CardNum() {
			this.setSize(20, 30);
			this
					.setLocation(0, Const_UI.PLAYER_PANEL_HEIGHT-this.getHeight()
							);
			this.add(jl);
			jl.setFont(new Font(Font.DIALOG, Font.BOLD, 21));
			jl.setForeground(Color.RED);
		}

		public void paint(Graphics g) {
			super.paint(g);
			// 绘制背景
			g.drawImage(backImg, 0, 0, this.getWidth(), this.getHeight(), null);
			// 更新手牌数
			num = player.getState().getCardList().size();
			jl.setText(String.valueOf(num));
			super.paintChildren(g);
		}
	}

	public int getPanelState() {
		return PanelState;
	}
	public void setPanelState(int panelState) {
		PanelState = panelState;
	}
	
	@Override
	public Point getPaintPoint() {
		int x = getWidth()/2+getX();
		int y = getHeight()/2+getY();
		return new Point(x, y);
	}
	
}
