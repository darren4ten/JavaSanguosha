package org.wangfuyuan.sgs.card;

import java.awt.Image;

/**
 * 特效接口
 * 实现该接口的牌提供一个特效image，用于绘制
 * @author user
 *
 */
public interface EffectCardIF {
	//获取特效图片
	Image getEffectImage();
}
