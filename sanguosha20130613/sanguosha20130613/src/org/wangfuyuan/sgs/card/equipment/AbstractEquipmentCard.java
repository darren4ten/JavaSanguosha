package org.wangfuyuan.sgs.card.equipment;

import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.EquipmentCardIF;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.data.enums.EquipmentType;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
/**
 * 装备牌的抽象类
 * @author user
 *
 */
public abstract class AbstractEquipmentCard extends AbstractCard implements EquipmentCardIF{
	//攻击距离加成
	protected int attDistance = 0;
	//防御距离加成
	protected int defDistance = 0;
	//装备类型
	protected EquipmentType equipmentType;
	
	public AbstractEquipmentCard(){
		//super();
	}
	/**
	 * 构造
	 */
	public AbstractEquipmentCard(int id, int number, Colors color){
		super(id, number, color);
		//数据初始化
		init();
	}
	
	/**
	 * 重写use
	 */
	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		//清空战场
		ModuleManagement.getInstance().getBattle().clear();
		//当前出牌区域清空
		p.getState().getUsedCard().clear();
		//手牌中删除
		p.getState().getCardList().remove(this);
		//装载
		p.getAction().loadEquipmentCard(this);
		//使用者手牌刷新
		p.refreshView();
	}
	/**
	 *  初始化数据 
	 */
	protected  void init(){
		
	}

	/**
	 * 装载到玩家上
	 */
	@Override
	public void load(AbstractPlayer p) {
		ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+"装载"+getName());
		//根据不同类型对应不同位置
		//如果已有装备，先卸载再装载
		AbstractEquipmentCard old;
		switch(equipmentType){
		case WUQI:
			old = p.getState().getEquipment().getWeapons();
			if(old!=null){
				old.unload(p);
			}
			p.getState().getEquipment().setWeapons(this);
			break;
		case FANGJU:
			old = p.getState().getEquipment().getArmor();
			if(old!=null){
				old.unload(p);
			}
			p.getState().getEquipment().setArmor(this);
			break;
		case _MA:
			old = p.getState().getEquipment().getAttHorse();
			if(old!=null){
				old.unload(p);
			}
			p.getState().getEquipment().setAttHorse(this);
			break;
		case MA:
			old = p.getState().getEquipment().getDefHorse();
			if(old!=null){
				old.unload(p);
			}
			p.getState().getEquipment().setDefHorse(this);
			break;
		}
		p.getState().attChange(attDistance);
		p.getState().defChange(defDistance);
	}

	/**
	 * 从玩家卸载
	 */
	@Override
	public void unload(AbstractPlayer p) {
		switch(equipmentType){
		case WUQI:
			p.getState().getEquipment().setWeapons(null);
			break;
		case FANGJU:
			p.getState().getEquipment().setArmor(null);
			break;
		case _MA:
			p.getState().getEquipment().setAttHorse(null);
			break;
		case MA:
			p.getState().getEquipment().setDefHorse(null);
			break;
		}
		this.throwIt(p);
		//调用卸载触发
		p.getTrigger().afterUnloadEquipmentCard();
		p.refreshView();
	}
	
	/**
	 * 目标检测
	 *//*
	@Override
	public void targetCheck(Panel_HandCards ph) {
		List<Panel_Player> list = ph.getMain().getPlayers();
		for (Panel_Player pp : list) {
			
				pp.disableToUse();
			
		}
	}*/
	public int getAttDistance() {
		return attDistance;
	}
	public int getDefDistance() {
		return defDistance;
	}
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}
	
	public void setAttDistance(int attDistance) {
		this.attDistance = attDistance;
	}
	public void setDefDistance(int defDistance) {
		this.defDistance = defDistance;
	}
	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}
	@Override
	public void beginInit() {
		
	}
	
}
