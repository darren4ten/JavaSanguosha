package org.wangfuyuan.sgs.data.types;

import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;

/**
 * �Զ�������ݽṹ
 * װ���ṹ
 * --����
 * --����
 * --��1��
 * --��1��
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
	 * �غϳ�ʼ������װ��
	 */
	public void initAll(){
		if(weapons!=null)weapons.beginInit();
		if(armor!=null)armor.beginInit();
	}
	
	/**
	 * �Ƿ�������
	 */
	public boolean hasWeapons(){
		return weapons!=null;
	}
	/**
	 * �Ƿ�����
	 */
	public boolean hasHorse(){
		return attHorse!=null||defHorse!=null;
	}

	/**
	 * ɾ������װ��
	 */
	public void removeALL(){
		weapons = null;
		armor = null;
		attHorse = null;
		defHorse = null;
	}
	
	/**
	 * �Ƿ�û��װ��
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
