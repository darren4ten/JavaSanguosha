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
 * װ���Ƶĳ�����
 * @author user
 *
 */
public abstract class AbstractEquipmentCard extends AbstractCard implements EquipmentCardIF{
	//��������ӳ�
	protected int attDistance = 0;
	//��������ӳ�
	protected int defDistance = 0;
	//װ������
	protected EquipmentType equipmentType;
	
	public AbstractEquipmentCard(){
		//super();
	}
	/**
	 * ����
	 */
	public AbstractEquipmentCard(int id, int number, Colors color){
		super(id, number, color);
		//���ݳ�ʼ��
		init();
	}
	
	/**
	 * ��дuse
	 */
	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		//���ս��
		ModuleManagement.getInstance().getBattle().clear();
		//��ǰ�����������
		p.getState().getUsedCard().clear();
		//������ɾ��
		p.getState().getCardList().remove(this);
		//װ��
		p.getAction().loadEquipmentCard(this);
		//ʹ��������ˢ��
		p.refreshView();
	}
	/**
	 *  ��ʼ������ 
	 */
	protected  void init(){
		
	}

	/**
	 * װ�ص������
	 */
	@Override
	public void load(AbstractPlayer p) {
		ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+"װ��"+getName());
		//���ݲ�ͬ���Ͷ�Ӧ��ͬλ��
		//�������װ������ж����װ��
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
	 * �����ж��
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
		//����ж�ش���
		p.getTrigger().afterUnloadEquipmentCard();
		p.refreshView();
	}
	
	/**
	 * Ŀ����
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
