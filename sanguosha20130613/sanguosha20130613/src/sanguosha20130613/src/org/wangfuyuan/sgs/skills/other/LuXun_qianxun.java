package org.wangfuyuan.sgs.skills.other;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.skills.LockingSkillIF;

/**
 * ½ѷ��ǫѷ��
 * ����˳��ǣ����ֲ�˼��
 * �����ֻ����Ϊһ����ʶ��
 * �����ʵ�����������ļ���<immuneCard>������д�Ƶľ�����ֵ
 * @author user
 *
 */
public class LuXun_qianxun implements LockingSkillIF{
	AbstractPlayer player;
	public LuXun_qianxun(AbstractPlayer p){
		this.player = p;
	}
	@Override
	public String getName() {
		return "ǫѷ";
	}

}
