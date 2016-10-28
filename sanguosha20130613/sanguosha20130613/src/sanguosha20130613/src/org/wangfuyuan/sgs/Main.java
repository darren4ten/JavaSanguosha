package org.wangfuyuan.sgs;

import org.wangfuyuan.sgs.gui.Frame_Main;
import org.wangfuyuan.sgs.gui.start.Frame_Load;
/**
 * 程序入口
 * 
 * @author wangfuyuan
 * @authorID 水月风霜…
 * @version alpha-0.5.11
 */
public class Main {
	public static String version = "alpha-0.5.11";
	//是否完成加载
	public static boolean isFinished;
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {				
				//启动界面
				new Frame_Load();
			}
		}).start();
		//主界面
		new Frame_Main();
	}
}
