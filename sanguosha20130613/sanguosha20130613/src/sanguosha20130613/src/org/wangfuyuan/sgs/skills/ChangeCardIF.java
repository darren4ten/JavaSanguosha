package org.wangfuyuan.sgs.skills;
/**
 * 变牌接口
 * 一些技能可以将某些手牌视作某种牌型
 * 在如requestShan之类方法中，也会将此技能作为判定是否拥有某种牌型的依据
 * @author user
 *
 */
public interface ChangeCardIF {
	//获取变牌后的结果
	int getResultType();
}
