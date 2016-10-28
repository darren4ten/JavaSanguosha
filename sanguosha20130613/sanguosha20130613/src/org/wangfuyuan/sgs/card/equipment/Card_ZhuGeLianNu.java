package org.wangfuyuan.sgs.card.equipment;

import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 诸葛连弩
 * @author user
 *
 */
public class Card_ZhuGeLianNu extends AbstractWeaponCard{
	//临时存储杀的开关
	boolean useSha ;
	public Card_ZhuGeLianNu(){}
	public Card_ZhuGeLianNu(int id, int number, Colors color) {
		super(id, number, color);
	}
	
	/**
	 * 重写装载
	 */
	@Override
	public void load(AbstractPlayer p) {
		super.load(p);
		useSha = p.getState().isUsedSha();
		p.getState().setUsedSha(false);
	}
	/**
	 * 重写卸载
	 */
	@Override
	public void unload(AbstractPlayer p) {
		super.unload(p);
		p.getState().setUsedSha(useSha);
	}

	/**
	 * 技能实现
	 * 重写杀后处理
	 */
	@Override
	public void afterSha(AbstractPlayer p, AbstractPlayer target) {
		super.afterSha(p, target);
		useSha = true;
		p.getState().setUsedSha(false);
	}
	
	@Override
	public void beginInit() {
		super.beginInit();
		useSha = false;
	}
	
	
}
