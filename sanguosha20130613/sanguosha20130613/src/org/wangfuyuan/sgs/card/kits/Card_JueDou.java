package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;
/**
 * 决斗
 * @author user
 *
 */
public class Card_JueDou extends AbstractKitCard{
	AbstractPlayer player1;
	AbstractPlayer player2;
	public Card_JueDou(){
		
	}

	/**
	 * 重写use方法
	 */
	@Override
	public void use( AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		//ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+"使用"+this.name);
		AbstractPlayer p2 = players.get(0);
		player1 = p;
		player2 = p2;
		//触发
		p.getTrigger().afterMagic();
		//无懈
		// 如果无懈，则return
		askWuXieKeJi(p, players);
		if (isWuXie) {
			ViewManagement.getInstance().printBattleMsg(getName() + "无效");
			ViewManagement.getInstance().refreshAll();
			return;
		}
		p.refreshView();
		//只要满足没出胜负，就交换身份决斗
		while(p.getAction().jueDou(p2)){
			AbstractPlayer tmp = p;
			p=p2;
			p2 = tmp;
		}
	}

	
}
