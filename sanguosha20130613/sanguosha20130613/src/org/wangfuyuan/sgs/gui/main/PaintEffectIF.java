package org.wangfuyuan.sgs.gui.main;

import java.awt.Point;

/**
 * 动画特效接口
 * 实现该接口的组件，可以参加特效绘制
 * 包括画线，受伤动画等
 * @author user
 *
 */
public interface PaintEffectIF {
	//获取绘制连线的节点
	Point getPaintPoint();
	
	int getX();
	int getY();
}
