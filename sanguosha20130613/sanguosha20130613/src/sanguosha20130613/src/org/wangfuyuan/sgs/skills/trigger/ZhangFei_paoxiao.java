package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 张飞【咆哮】
 * @author user
 *
 */
public class ZhangFei_paoxiao extends P_Trigger implements LockingSkillIF{
	
	public ZhangFei_paoxiao(AbstractPlayer p){
		this.player = p;
	}
	
	/**
	 * 重写杀后触发
	 * 取消开关
	 */
	@Override
	public void afterSha() {
		super.afterSha();
		player.getState().setUsedSha(false);
	}

	@Override
	public String getName() {
		return "咆哮";
	}

}
