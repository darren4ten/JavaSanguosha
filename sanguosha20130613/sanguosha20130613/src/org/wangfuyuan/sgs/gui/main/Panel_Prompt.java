package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 显示操作提示信息的面板
 * 
 * @author user
 * 
 */
public  class Panel_Prompt extends JPanel  {
	private static final long serialVersionUID = 8764890627705088325L;
	private static final String[] types = { "杀", "闪", "桃" };
	
	private String message = "测试--提示信息面板";
	private Font font;

	/**
	 * 构造
	 */
	public Panel_Prompt() {
		this.setSize(550, 40);
		this.setOpaque(false);
		this.font = new Font("楷体", Font.BOLD, 30);
	}

	/**
	 * 绘制内容
	 */
	public synchronized void paintComponent(Graphics g) {
		g.setFont(font);
		g.setColor(Color.white);
		if (message != null) {
			g.drawString(message, 10, 30);
		}
	}

	/**
	 * 重写paint
	 *//*
	public void paint1(Graphics g) {
		if (isPainting()) { // 根据当前帧显示当前透明度的内容组件
			float alpha = (float) index / (float) FRAMENUMBER;
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, alpha));
			// Renderer渲染机制
			super.paint(g2d);
		} else {
			// 如果是第一次，启动动画时钟
			index = 0;
			timer = new Timer(INTERVAL, this);
			timer.start();
		}
	}*/

	/**
	 * 提示：需要出某种牌型n张
	 * 
	 * @param type
	 * @param num
	 */
	public void show_RemindToUse(int type, int num) {
		message = "您需要出" + num + "张" + types[type-1];
		repaint();
	}

	/**
	 * 提示弃牌
	 */
	public void show_RemindToThrow(int num) {
		message = "您需要丢弃" + num + "张手牌";
		repaint();
	}

	/**
	 * 显示消息
	 */
	public void show_Message(String msg){
		message = msg;
		repaint();
	}
	/**
	 * 清空提示
	 * 
	 * @return
	 */
	public void clear() {
		message = null;
		repaint();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
