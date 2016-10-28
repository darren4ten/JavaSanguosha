package org.wangfuyuan.sgs.skills.action;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Action;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * 吕布技能【无双】 
 * 当出杀/决斗时候，目标需要2张闪/杀
 * 
 * @author user
 * 
 */
public class Lvbu_wushuang extends P_Action implements LockingSkillIF{
	public static final int NEED = 2;

	public Lvbu_wushuang(AbstractPlayer p) {
		super(p);
	}

	/**
	 * 重写杀
	 */
	@Override
	public boolean sha(AbstractPlayer p) {
		boolean flag = true; 
		int done = 0;
		while (done < NEED) {
			if (!p.getRequest().requestShan()) {
				p.getAction().loseHP(1 + p.getState().getExtDamage(), player);
				flag = true;
				break;
			} else {
				done++;
				flag = false;
			}
		}
		// 开关
		player.getState().setUsedSha(true);
		// 调用触发事件
		player.getTrigger().afterSha();
		return flag;
	}

	/**
	 * 重写决斗
	 */
	@Override
	public boolean jueDou(AbstractPlayer p) {
		int done = 0;
		while(done<NEED){
			if(!p.getRequest().requestSha()){
				p.getAction().loseHP(1+player.getState().getExtDamage(), player);
				return false;
			}else{
				done++;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return "无双";
	}
}
