package org.wangfuyuan.sgs.skills.function;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Function;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ����Ӣ����š�
 * @author user
 *
 */
public class HuangYueYing_QiCai extends P_Function implements LockingSkillIF{
	AbstractPlayer player;
	public HuangYueYing_QiCai(AbstractPlayer player) {
		super(player);
	}
	
	/**
	 * ��д����ʹ�÷�Χ��������
	 */
	@Override
	public int getKitUseDistance() {
		return 999;
	}
	
	@Override
	public String getName() {
		return "���";
	}
}
