package org.wangfuyuan.sgs.card.equipment;

import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
/**
 * ��ֽ�
 * @author user
 *
 */

public class Card_QingGangJian extends AbstractWeaponCard{
	
	public Card_QingGangJian(){
		super();
	}
	public Card_QingGangJian(int id, int number, Colors color){
		super(id, number, color);
	}
	
	/**
	 * �����ж�
	 * ���ӷ���
	 * ��Զ����false
	 */
	@Override
	public boolean checkArmor(AbstractPlayer p, AbstractPlayer target) {
		return false;
	}
	
	
}
