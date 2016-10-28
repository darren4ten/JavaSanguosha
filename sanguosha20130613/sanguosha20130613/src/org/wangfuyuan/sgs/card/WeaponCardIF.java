package org.wangfuyuan.sgs.card;

import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 武器接口
 * 拥有一个带武器出杀的方法
 * @author user
 *
 */
public interface WeaponCardIF {
	//带武器出杀，包含以下几个流程
	void shaWithEquipment(AbstractPlayer p,AbstractPlayer target,AbstractCard card);
	
	//杀前的技能
	void useSkillBeforeSha(AbstractPlayer p,AbstractPlayer target);
	//对方防具判定
	boolean checkArmor (AbstractPlayer p,AbstractPlayer target);
	//调用人物的杀
	boolean callSha(AbstractPlayer p,AbstractPlayer target);
	//如果杀成功触发
	void damageTrigger(AbstractPlayer p,AbstractPlayer target);
	//如果被闪触发
	void falseTrigger(AbstractPlayer p,AbstractPlayer target);
	//杀完后的触发
	void afterSha(AbstractPlayer p,AbstractPlayer target);
	
}
