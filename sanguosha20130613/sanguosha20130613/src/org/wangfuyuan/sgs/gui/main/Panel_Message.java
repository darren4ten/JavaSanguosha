package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * 战场消息面板显示
 * 
 * @author user
 * 
 */
public class Panel_Message extends JPanel {
	private static final long serialVersionUID = -2483544218392211358L;
	// private String message = "测试--";
	// 最多显示N条
	//private static final int SIZE = 3;
	private List<String> strList = new ArrayList<String>();
	private Font font;
	// 定时清除的当前秒
	private int time = 0;

	public Panel_Message() {
		// this.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		this.setSize(500, 100);
		this.setOpaque(false);
		this.font = new Font("楷体", Font.BOLD, 30);
		clsThread.start();
	}

	/**
	 * 绘制
	 */
	public void paintComponent(Graphics g) {
		g.setFont(font);
		g.setColor(Color.white);
		for (int i = 0; i < strList.size(); i++) {
			g.drawString(strList.get(i), 10, font.getSize() * i + 30);
		}
		super.paintComponents(g);
	}

	/**
	 * 向面板中添加一条消息 如果超载则先进先出
	 */
	public void addMessage(final String msg) {
		// 重新计时
		time = 0;
		strList.add(msg);
		/*if (strList.size() > SIZE) {
			strList.remove(0);
			//repaint();
		}*/
	}

	/**
	 * 定时清理的线程
	 */
	private Thread clsThread = new Thread() {
		@Override
		public void run() {
			while (true) {
				
				if (time >= 10) {
					if (!strList.isEmpty()) {
						strList.remove(0);
						time = 0;
						repaint();
					}
				}
				try {
					Thread.sleep(100);
					time++;

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	};
}
