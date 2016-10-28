package org.wangfuyuan.sgs.skills.action;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.impl.P_Action;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
/**
 * �����ܡ����
 * 
 * @author user
 *
 */
public class MaChao_tieji extends P_Action implements LockingSkillIF{

	public MaChao_tieji(AbstractPlayer p) {
		super(p);
	}

	/**
	 * ��дɱ
	 * �ж������Ϊ���ң�ֱ�ӿ�Ѫ
	 */
	@Override
	public boolean sha(AbstractPlayer p) {
		AbstractCard cc = ModuleManagement.getInstance().showOneCheckCard();
		//����ж�Ϊ����
		if(player.getFunction().checkRollCard(cc, Colors.HONGXIN)){
			ViewManagement.getInstance().printBattleMsg(player.getInfo().getName()+getName()+"��Ч");
			p.getAction().loseHP(1+player.getState().getExtDamage(), player);
			//����
			player.getState().setUsedSha(true);
			//���ô����¼�
			player.getTrigger().afterSha();
			return true;
		}else{
			return super.sha(p);
		}
	}

	@Override
	public String getName() {
		return "����";
	}
}
