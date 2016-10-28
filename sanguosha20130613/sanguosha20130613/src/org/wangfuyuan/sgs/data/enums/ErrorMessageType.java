package org.wangfuyuan.sgs.data.enums;
/**
 * 用来标识用户一些无效操作的信息
 * @author user
 *
 */
public enum ErrorMessageType {
	//已经使用过
	hasUsed,
	//已经使用过杀
	hasUsed_Sha,
	//未到发动时机
	cannotUseNow,
	//无法使用因为：满血
	cannotUseCause_FullHP,
	//无法使用因为：没有手牌
	cannotUseCause_NoneHandCard,
	//无法使用因为：没有装备
	cannotUseCause_NoneEquipmentCard,
	//无法使用因为：没有目标
	cannotUseCause_NoneTarget,
	
}
