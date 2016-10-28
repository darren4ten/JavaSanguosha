package org.wangfuyuan.sgs.skills.function;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Function;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ����������
 * @author user
 *
 */
public class MaChao_mashu extends P_Function implements LockingSkillIF{

	public MaChao_mashu(AbstractPlayer player) {
		super(player);
	}

	@Override
	public int getDistance(AbstractPlayer p) {
		return super.getDistance(p)-1;
	}

	@Override
	public String getName() {
		return "����";
	}

}
