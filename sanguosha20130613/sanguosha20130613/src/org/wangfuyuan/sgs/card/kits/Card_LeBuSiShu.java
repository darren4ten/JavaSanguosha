package org.wangfuyuan.sgs.card.kits;

import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.DelayKitIF;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * 乐不思蜀
 * @author user
 *
 */
public class Card_LeBuSiShu extends AbstractKitCard implements DelayKitIF{
	AbstractPlayer owner;
	public Card_LeBuSiShu(){
	}

	/**
	 * 重写use
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		final AbstractPlayer target = players.get(0);
		owner = target;

		//牌堆收回
		ModuleManagement.getInstance().getGcList().remove(this);
		//目标判定区添加
		target.getState().getCheckedCardList().add(this);
		p.refreshView();
		target.refreshView();
	}

	/**
	 * 锦囊发动
	 */
	@Override
	public void doKit() {
		//无懈
		askWuXieKeJi(owner, null);
		if (isWuXie) {
			ViewManagement.getInstance().printBattleMsg(getName() + "无效");
			ViewManagement.getInstance().refreshAll();
			owner.getProcess().setCanUseCard(true);
			owner.getState().getCheckedCardList().remove(this);
			gc();
			return;
		}
		AbstractCard cc = ModuleManagement.getInstance().showOneCheckCard();
		boolean flag = owner.getFunction().checkRollCard(cc, Colors.HONGXIN);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(flag){			
			ViewManagement.getInstance().printBattleMsg(getName()+"失效");
		}else{
			ViewManagement.getInstance().printBattleMsg(getName()+"生效");
		}
		owner.getProcess().setCanUseCard(flag);
		owner.getState().getCheckedCardList().remove(this);
		gc();
	}

	@Override
	public String getShowNameInPanel() {
		return "乐";
	}
	
	@Override
	public int getKitCardType() {
		return type;
	}

	@Override
	public void targetCheck(Panel_HandCards ph) {
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (Panel_Player pp : list) {
			if (!pp.getPlayer().getState().isDead()) {
				if(pp.getPlayer().getState().hasDelayKit(Const_Game.LEBUSISHU)){
					pp.disableToUse();
				}
			}
		}
	}
	public AbstractPlayer getOwner() {
		return owner;
	}
	public void setOwner(AbstractPlayer owner) {
		this.owner = owner;
	}
	
}
