package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ½ѷ����Ӫ��
 * @author user
 *
 */
public class LuXun_lianying extends P_Trigger implements LockingSkillIF{
	public LuXun_lianying(AbstractPlayer p){
		this.player = p;
	}
	
	/**
	 * ��д��ʧ���ƴ���
	 */
	@Override
	public void afterLoseHandCard() {
		//�����������һ��
		if(player.getState().getCardList().isEmpty()){
			player.getAction().addOneCardFromList();
			System.out.println("�Ʋ������ܵģ�û���������ܵģ�");
		}
	}
	
	@Override
	public String getName() {
		return "��Ӫ";
	}

}
