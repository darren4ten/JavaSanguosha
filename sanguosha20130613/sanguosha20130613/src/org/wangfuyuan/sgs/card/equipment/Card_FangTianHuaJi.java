package org.wangfuyuan.sgs.card.equipment;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;

/**
 * 方天画戟
 * 
 * @author user
 * 
 */
public class Card_FangTianHuaJi extends AbstractWeaponCard implements
		ActiveSkillWeaponCardIF {
	public Card_FangTianHuaJi(){}
	public Card_FangTianHuaJi(int id, int number, Colors color) {
		super(id, number, color);
	}
	/**
	 * 需要满足： 1，最后一张牌 2，最后一张是杀 3，杀已经选中
	 */
	@Override
	public boolean onClick_open(Panel_HandCards ph) {

		if (ph.getCards().size() == 1
				&& ph.getCards().get(0).getCard().getType() == Const_Game.SHA
				&& !ph.getSelectedList().isEmpty()) {
			ph.getTarget().setLimit(3);
			return true;
		}
		return false;
	}

	@Override
	public boolean onClick_close(Panel_HandCards ph) {
		return true;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
}
