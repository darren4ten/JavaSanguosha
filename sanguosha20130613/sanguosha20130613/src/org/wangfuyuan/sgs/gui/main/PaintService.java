package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import org.wangfuyuan.sgs.data.enums.GameOver;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 绘制特效类
 * 
 * @author user
 * 
 */
public class PaintService {
	public static Panel_Main main;
	public static final int SLEEPTIME= 1000;
	// 初始化
	public static void createMain(Panel_Main pm) {
		main = pm;
	}

	/**
	 * 玩家之间画一条线
	 * 默认时间秒后清除
	 * @param p
	 * @param p2
	 */
	public static void drawLine(AbstractPlayer p, AbstractPlayer toP) {
		drawLineOnly(p,toP);
		clearAfter(0);
	}
	
	/**
	 * 从玩家到玩家组之间画线
	 */
	public static void drawLine(AbstractPlayer p, List<AbstractPlayer> players) {
		for (int i = 0; i < players.size(); i++) {
			drawLineOnly(p, players.get(i));
		}
		clearAfter(0);
	}
	
	/**
	 * 【暂时没找到合适的素材，所以本方法未使用到】
	 * 绘制受伤动画
	 */
	public static void drawHurt(AbstractPlayer p){
		Graphics g = main.getGraphics();
		Image img = ImgUtil.getPngImgByName("hurt");
		g.drawImage(img, p.getPanel().getX(), p.getPanel().getY(), null);
		clearAfter(1500);
	}
	
	/**
	 * 绘制牌的效果图
	 * @param img
	 */
	public static void drawEffectImage(Image img,AbstractPlayer p){
		Graphics g = main.getGraphics();
		g.drawImage(img, main.getWidth()/2, main.getHeight()/2-img.getHeight(null),250,250, null);
	}
	
	
	/*
	 * 内部方法 画线
	 * @param p
	 * @param toP
	 */
	private static  void drawLineOnly(AbstractPlayer p, AbstractPlayer toP){
		Graphics g = main.getGraphics();
		g.setColor(Color.red);
		// g.draw3DRect(0, 0, 400, 400, true);
		Point p1 = ((PaintEffectIF) p.getPanel()).getPaintPoint();
		Point p2 = ((PaintEffectIF) toP.getPanel()).getPaintPoint();
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2
				.getY());
	}
	
	/**
	 * 绘制结束
	 */
	public static void paintGameOver(GameOver go){
		main.removeAll();
		main.add(new Panel_GameOver(go),0);
		main.validate();
		main.repaint();
	}
	
	/**
	 * N秒后清除
	 * 如果n<=0，则使用默认设置
	 */
	public static void clearAfter(int n){
		int sleep = n>0?n:SLEEPTIME;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		main.repaint();
	}
}
