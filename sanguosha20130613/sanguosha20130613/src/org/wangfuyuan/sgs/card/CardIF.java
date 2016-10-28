package org.wangfuyuan.sgs.card;

import java.util.List;

import org.wangfuyuan.sgs.player.AbstractPlayer;


/**
 * 牌的接口
 * 
 * @author user
 * 
 */
public interface CardIF {
	
	/**
	 * 花色图片的文件名常量
	 */
	public static final String HEITAO_FN = "color_heitao";
	public static final String HONGXIN_FN = "color_hongxin";
	public static final String MEIHUA_FN = "color_meihua";
	public static final String FANGKUAI_FN = "color_fangkuai";
	
	/**
	 * 使用目标类型
	 * 
	 */
	public static final int AOE = 0;
	public static final int SELECT = 1;
	public static final int NONE = 2;
	

	/**
	 *  使用
	 * 
	 */
	void use(AbstractPlayer p, List<AbstractPlayer> players);
	/**
	 * 被动响应使用
	 */
	boolean requestUse(AbstractPlayer p, List<AbstractPlayer> players);
	/**
	 * 丢弃
	 */
	void throwIt(AbstractPlayer p);
	/**
	 * 交给玩家
	 */
	void passToPlayer(AbstractPlayer fromP,AbstractPlayer receiverP);
	/**
	 * 是否需要射程
	 */
	boolean isNeedRange();
}
