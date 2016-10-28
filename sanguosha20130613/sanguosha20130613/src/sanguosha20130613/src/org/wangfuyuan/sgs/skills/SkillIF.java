package org.wangfuyuan.sgs.skills;
/**
 * 主动技能的接口
 * @author user
 *
 */
public interface SkillIF {
	//技能初始化
	void init();
	//技能使用允许
	boolean isEnableUse();
	//获取技能名称
	String getName();
}
