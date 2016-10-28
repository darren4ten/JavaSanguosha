package org.wangfuyuan.sgs.gui.main;
/**
 * 组件刷新接口
 * 凡是需要刷新的，都实现refresh方法
 * 接受全局管理类的刷新通知
 * @author user
 *
 */
public interface RefreshbleIF {

	void refresh();
	
	void repaint();
	
	int getX();
	
	int getY();
	
}
