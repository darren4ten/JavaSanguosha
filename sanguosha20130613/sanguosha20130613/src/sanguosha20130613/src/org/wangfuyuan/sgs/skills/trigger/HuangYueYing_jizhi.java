package org.wangfuyuan.sgs.skills.trigger;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ����Ӣ�����ǡ�
 * @author user
 *
 */
public class HuangYueYing_jizhi extends P_Trigger implements LockingSkillIF{
	public HuangYueYing_jizhi(AbstractPlayer p){
		this.player = p;
	}
	/**
	 * ������Ӣ��
	 * ��дʹ�ý��Ҵ���
	 */
	@Override
	public void afterMagic() {
		player.getAction().addOneCardFromList();
		player.refreshView();
	}

	@Override
	public String getName() {
		return "����";
	}

}
