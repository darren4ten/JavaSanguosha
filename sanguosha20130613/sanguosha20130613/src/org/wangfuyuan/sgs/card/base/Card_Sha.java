package org.wangfuyuan.sgs.card.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.card.equipment.AbstractWeaponCard;
import org.wangfuyuan.sgs.card.equipment.ArmorIF;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * “杀”牌的类
 * 
 * @author user
 * 
 */
public class Card_Sha extends AbstractCard implements EffectCardIF{

	public Card_Sha() {

	}

	/**
	 * 重写use方法
	 */
	@Override
	public void use(final AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		System.out.println("目标数："+players.size());
		List<AbstractPlayer> list = new ArrayList<AbstractPlayer>(players);
		for (int i = 0; i < list.size(); i++) {
			final AbstractPlayer tmp = list.get(i);
			// 绘制杀的效果
			ViewManagement.getInstance().printBattleMsg(
					p.getInfo().getName() + "对" + tmp.getInfo().getName()
					+ "使用了杀" );
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						p.refreshView();
						PaintService.drawEffectImage(getEffectImage(),p);
						PaintService.drawLine(p,tmp);
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//drawEffect(p, players);
			executeSha(p, tmp);
			// 刷新
			tmp.refreshView();

		}
		// ViewManagement.getInstance().refreshAll();
	}

	/**
	 * 执行杀牌的杀流程
	 */
	public void executeSha(AbstractPlayer p, AbstractPlayer toP) {
		if (!toP.getAction().avoidSha(p,this)) {
			// 如果使用者带武器，则调用武器的杀
			AbstractWeaponCard awc = (AbstractWeaponCard) p.getState()
					.getEquipment().getWeapons();
			if (awc != null) {
				awc.shaWithEquipment(p, toP, this);
			} else {
				// 判定防具
				ArmorIF am = (ArmorIF) toP.getState().getEquipment().getArmor();
				if (am == null || !am.check(this, toP)) {
					p.getAction().sha(toP);
				}
			}
		}
		p.refreshView();
	}
	
	/**
	 * 使用目标检测
	 */
	@Override
	public  void targetCheck(Panel_HandCards ph) {

		// 遍历 检测
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (int i = 0; i < list.size(); i++) {
			Panel_Player pp = list.get(i);
			//如果死亡
			if(pp.getPanelState()==Panel_Player.DEAD||pp.getPanelState()==Panel_Player.DISABLE){
				System.out.println("this is dead");
				continue;
			}
			// 如果需要射程
			if (!this.isInRange(ph.getPlayer(), pp.getPlayer())) {
				pp.disableToUse();
				continue;
			}
			pp.enableToUse();
		}
	
	}

	/**
	 * 判断user是否能对target使用这张牌
	 */
	@Override
	public boolean targetCheck4SinglePlayer(AbstractPlayer user,
			AbstractPlayer target) {
		boolean b = !user.getState().isUsedSha();
		boolean b2 = isInRange(user, target);
		return b&&b2;
	}

}
