package org.wangfuyuan.sgs.card;
/**
 * 延迟锦囊接口
 * @author user
 *
 */
public interface DelayKitIF {
	//发动技能
	void doKit();
	//获取锦囊类型
	int getKitCardType();
	//获取面板显示字符
	String getShowNameInPanel();
}
