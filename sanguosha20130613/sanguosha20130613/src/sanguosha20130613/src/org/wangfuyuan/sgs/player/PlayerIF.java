package org.wangfuyuan.sgs.player;


/**
 * 玩家类的接口
 * @author user
 *
 */
public interface PlayerIF {
	/**
	 * 6阶段状态常量
	 * 用以表示玩家处于哪个阶段
	 */
	public static final int STAGE_BEGIN = 1; 
	public static final int STAGE_CHECK = 2; 
	public static final int STAGE_ADDCARDS = 3; 
	public static final int STAGE_USECARDS = 4; 
	public static final int STAGE_THROWCRADS = 5; 
	public static final int STAGE_END = 6; 
	
	//载入技能
	void loadSkills(String name) ;
	//执行回合
	void process();
	//刷新关联的面板
	void refreshView();
	
}
