package org.wangfuyuan.sgs.player.impl;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.Player_TriggerIF;

/**
 * 玩家触发的实现类
 * @author user
 *
 */
public class P_Trigger implements Player_TriggerIF {
	//人物模型
	protected AbstractPlayer player;
	
	public P_Trigger(){}
	//构造器
	public P_Trigger(AbstractPlayer p){
		this.player = p;
	}
	@Override
	public void afterAddHP() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterGetHandCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLoadEquipmentCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLoseHP(AbstractPlayer murderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLoseHandCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterMagic() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 杀后触发
	 * 默认开关关闭
	 */
	@Override
	public void afterSha() {
		player.getState().setUsedSha(true);
	}

	@Override
	public void afterShan() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTao() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterUnloadEquipmentCard() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 死亡触发
	 */
	@Override
	public void afterDead() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterNullCards() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 判定触发
	 * 参数1：判定牌
	 * 参数2：判定结果
	 */
	@Override
	public void afterCheck(AbstractCard c,boolean result) {
		c.gc();
	}
	
}
