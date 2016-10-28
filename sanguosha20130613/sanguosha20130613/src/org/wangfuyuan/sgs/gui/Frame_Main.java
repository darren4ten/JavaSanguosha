package org.wangfuyuan.sgs.gui;

import java.util.List;

import javax.swing.JFrame;

import org.wangfuyuan.sgs.Main;
import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.GameOver;
import org.wangfuyuan.sgs.data.enums.Identity;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Main;
import org.wangfuyuan.sgs.gui.select.Panel_Select;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;

/**
 * 游戏主窗体
 * 
 * @author user
 * 
 */
public class Frame_Main extends JFrame {
	
	private static final long serialVersionUID = -6905419069196737546L;
	public static Frame_Main me;
	public static GameThread gt;
	public static boolean isGameOver;
	// 游戏主面板
	public static Panel_Main main;
	
	//选人面板
	Panel_Select select;
	AbstractPlayer boss;

	public Frame_Main() {
		me = this;
		createSelectUI();
		createUI();
	}
	public void startThread(){
		gt = new GameThread();
		gt.start();
	}
	
	//加载选人面板
	private void createSelectUI(){
		System.out.println("开始创建面板");
		select = new Panel_Select();
	}
	//加载主面板
	public void loadMain(){
		remove(select);
		repaint();
		init();
		//createUI();
		// 创建主战斗面板
		main = new Panel_Main();
		add(main);
		startThread();
	}
	// 创建UI界面
	private void createUI() {
		System.out.println("开始创建窗体");
		this.setTitle("三国杀"+"--"+Main.version);
		this.setSize(Const_UI.FRAME_WIDTH, Const_UI.FRAME_HEIGHT);
		this.setDefaultCloseOperation(3);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(select);
		System.out.println("准备显示窗体");
		Main.isFinished = true;
		
		this.setVisible(true);
		System.out.println("完成");
	}

	private void init() {
		List<AbstractPlayer> players = ModuleManagement.getInstance()
				.getPlayerList();
		// 寻找主公并初始化
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getState().getId() == Identity.ZHUGONG) {
				boss = players.get(i);
				initBoss(players.get(i));
				break;
			}
		}

	}

	/**
	 * 开始进入游戏的回合
	 */
	public void startGame() {
		isGameOver = false;
		boss.process();
	}

	/**
	 * 初始化主公
	 */
	private void initBoss(AbstractPlayer boss) {
		// 加1点血
		boss.getInfo().setMaxHP(boss.getInfo().getMaxHP() + 1);
		boss.getState().setCurHP(boss.getInfo().getMaxHP());
		// 加载主公技

	}

	/**
	 * 游戏结束
	 */
	public  void gameOver(final GameOver type) {
		isGameOver = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(gt.getState());
				while(gt.getState()!=Thread.State.TERMINATED){					
					PaintService.paintGameOver(type);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					me.remove(main);
					me.repaint();
				
					/*main = new Panel_Main();
					me.add(main);*/
					//me.validate();
					/*me.repaint();*/
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//startThread();
					createSelectUI();
					createUI();
					//ModuleManagement.reset();
					//ViewManagement.reset();
					break;
				}
				
				System.out.println(gt.getState());				
			}
		}).start();
	}

	/**
	 * 游戏主线程
	 */
	class GameThread extends Thread {
		public void run() {
			startGame();
			System.out.println("游戏线程结束");
		}

	}
}
