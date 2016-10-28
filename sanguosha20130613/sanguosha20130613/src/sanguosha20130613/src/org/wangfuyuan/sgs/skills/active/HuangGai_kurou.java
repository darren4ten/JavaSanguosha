package org.wangfuyuan.sgs.skills.active;

import org.wangfuyuan.sgs.gui.Frame_Main;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.skills.SkillIF;
/**
 * 黄盖【苦肉】
 * 扣1点血得2张手牌
 * @author user
 *
 */
public class HuangGai_kurou implements Runnable,SkillIF{
	AbstractPlayer player;
	public HuangGai_kurou(AbstractPlayer p){
		this.player = p;
	}
	/**
	 * 当点击使用的时候
	 * 扣1点血，若没死就得张牌
	 */
	@Override
	public void run() {
		//扣1点血
		player.getAction().loseHP(1, null);
		if(!player.getState().isDead()){			
			//获得2张牌
			player.getAction().addOneCardFromList();
			player.getAction().addOneCardFromList();
			player.refreshView();
		}
		//解锁
		synchronized (player.getProcess()) {
			player.getState().setRes(0);
			player.getProcess().notify();
			//如果死亡就弃牌
			if(player.getState().isDead()&&!Frame_Main.isGameOver){
				player.setSkip(true);
			}
		}
	}

	@Override
	public String getName() {
		return "苦肉";
	}

	@Override
	public boolean isEnableUse() {
		return false;
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
