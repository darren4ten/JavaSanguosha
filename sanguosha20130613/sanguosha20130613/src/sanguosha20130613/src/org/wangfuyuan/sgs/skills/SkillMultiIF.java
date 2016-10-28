package org.wangfuyuan.sgs.skills;

import java.util.List;

/**
 * 技能类多重效果的接口
 * 主要针对一个类中出现多个技能实现的情况
 * 比如郭嘉的两个技能，都是写在触发类中的
 * 
 * @author user
 *
 */
public interface SkillMultiIF {
	//获取技能名称列表
	List<String> getNameList();
}
