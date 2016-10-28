package org.wangfuyuan.sgs.skills.other;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 陆逊【谦逊】
 * 免疫顺手牵羊和乐不思蜀
 * 这个类只是作为一个标识用
 * 具体的实现是在配置文件中<immuneCard>里面填写牌的具体数值
 * @author user
 *
 */
public class LuXun_qianxun implements LockingSkillIF{
	AbstractPlayer player;
	public LuXun_qianxun(AbstractPlayer p){
		this.player = p;
	}
	@Override
	public String getName() {
		return "谦逊";
	}

}
