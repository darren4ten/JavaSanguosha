package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ������ġ��ɼ���
 * ��ʧװ���󣬻��2������
 * @author user
 *
 */
public class SunShangXiang_xiaoji extends P_Trigger implements LockingSkillIF{
	private static final int NUMBER = 2;
	public SunShangXiang_xiaoji(AbstractPlayer p) {
		this.player = p;
	}
	
	/**
	 * ��дж��װ������
	 */
	@Override
	public void afterUnloadEquipmentCard() {
		ViewManagement.getInstance().printBattleMsg("���ɼ������ܴ���");
		for (int i = 0; i < NUMBER; i++) {
			player.getAction().addOneCardFromList();			
		}
	}

	@Override
	public String getName() {
		return "�ɼ�";
	}
	
}
