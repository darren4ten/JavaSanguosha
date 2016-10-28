package org.wangfuyuan.sgs.card;

import org.wangfuyuan.sgs.data.enums.EquipmentType;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 装备牌接口
 * @author user
 *
 */
public interface EquipmentCardIF {
	
	//装载
	void load(AbstractPlayer p);
	//卸载
	void unload(AbstractPlayer p);
	//获取攻击距离
	int getAttDistance() ;
	//获取防御距离
	int getDefDistance ();
	//获取类型
	EquipmentType getEquipmentType();
	//回合初始化
	void beginInit();
}
