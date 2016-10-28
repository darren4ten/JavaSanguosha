package org.wangfuyuan.sgs.card.equipment;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.WeaponCardIF;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 抽象武器类
 * 
 * @author user
 * 
 */
public abstract class AbstractWeaponCard extends AbstractEquipmentCard
		implements WeaponCardIF {

	public AbstractWeaponCard() {
		super();
	}

	public AbstractWeaponCard(int id, int number, Colors color) {
		super(id, number, color);
	}

	/**
	 * 带装备出杀 包含一系列流程 各个子类武器将重写其中的部分的流程 来实现其技能特效
	 */
	@Override
	public void shaWithEquipment(AbstractPlayer p, AbstractPlayer target,
			AbstractCard card) {
		// 杀结算之前的触发技能
		useSkillBeforeSha(p, target);
		// 如果对方有防具且判定为生效则返回
		if (checkArmor(p, target)) {
			falseTrigger(p, target);
			return;
		}
		// 造成伤害或者被闪掉调用对应的触发事件
		if (callSha(p, target)) {
			damageTrigger(p, target);
		} else {
			System.out.println("chufa");
			falseTrigger(p, target);
		}
		// 结算完后的触发事件
		afterSha(p, target);
		
	}

	@Override
	public void afterSha(AbstractPlayer p, AbstractPlayer target) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean callSha(AbstractPlayer p, AbstractPlayer target) {
		return p.getAction().sha(target);
	}

	/**
	 * 防具判定
	 */
	@Override
	public boolean checkArmor(AbstractPlayer p, AbstractPlayer target) {
		// 判定防具
		ArmorIF am = (ArmorIF) target.getState().getEquipment().getArmor();
		if (am==null || !am.check(this,target)) {
			return false;
		}
		return true;
	}

	@Override
	public void damageTrigger(AbstractPlayer p, AbstractPlayer target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void falseTrigger(AbstractPlayer p, AbstractPlayer target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useSkillBeforeSha(AbstractPlayer p, AbstractPlayer target) {
		// TODO Auto-generated method stub

	}
}
