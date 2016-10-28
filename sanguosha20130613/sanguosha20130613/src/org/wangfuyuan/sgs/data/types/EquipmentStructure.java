package org.wangfuyuan.sgs.data.types;

import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;

/**
 * 自定义的数据结构
 * 装备结构
 * --武器
 * --防具
 * --加1马
 * --减1马
 * @author user
 *
 */
public class EquipmentStructure {
	AbstractEquipmentCard weapons;
	AbstractEquipmentCard armor;
	AbstractEquipmentCard attHorse;
	AbstractEquipmentCard defHorse;
	
	public EquipmentStructure(){
		
	}

	/**
	 * 回合初始化所有装备
	 */
	public void initAll(){
		if(weapons!=null)weapons.beginInit();
		if(armor!=null)armor.beginInit();
	}
	
	/**
	 * 是否有武器
	 */
	public boolean hasWeapons(){
		return weapons!=null;
	}
	/**
	 * 是否有码
	 */
	public boolean hasHorse(){
		return attHorse!=null||defHorse!=null;
	}

	/**
	 * 删除所有装备
	 */
	public void removeALL(){
		weapons = null;
		armor = null;
		attHorse = null;
		defHorse = null;
	}
	
	/**
	 * 是否没有装备
	 */
	public boolean isEmpty(){
		return weapons==null && armor == null && attHorse == null && defHorse == null;
	}
	
	
	public AbstractEquipmentCard getWeapons() {
		return weapons;
	}


	public AbstractEquipmentCard getArmor() {
		return armor;
	}

	public void setArmor(AbstractEquipmentCard armor) {
		this.armor = armor;
	}

	public AbstractEquipmentCard getAttHorse() {
		return attHorse;
	}

	public void setAttHorse(AbstractEquipmentCard attHorse) {
		this.attHorse = attHorse;
	}

	public AbstractEquipmentCard getDefHorse() {
		return defHorse;
	}

	public void setDefHorse(AbstractEquipmentCard defHorse) {
		this.defHorse = defHorse;
	}

	public void setWeapons(AbstractEquipmentCard weapons) {
		this.weapons = weapons;
	}
	
	
}
