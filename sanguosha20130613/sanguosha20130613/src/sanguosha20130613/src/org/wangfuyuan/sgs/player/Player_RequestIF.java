package org.wangfuyuan.sgs.player;
/**
 * 玩家响应动作接口类
 * @author user
 *
 */
public interface Player_RequestIF {
	//询问是否出杀
	boolean requestSha();
	//询问是否出闪
	boolean requestShan();
	//询问是否出桃
	boolean requestTao();
	//询问是否出无懈
	boolean requestWuXie();
	//获取当前响应牌型
	int getCurType();
	//设置当前响应牌型
	void setCurType(int curType);
	//清空状态
	void clear();
	
	
	/*//响应过程是否锁住以发动技能
	void  setSkilling(boolean b);
	boolean isSkilling();*/
}
