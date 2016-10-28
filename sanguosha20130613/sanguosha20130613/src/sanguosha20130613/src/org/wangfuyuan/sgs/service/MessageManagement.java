package org.wangfuyuan.sgs.service;

import org.wangfuyuan.sgs.data.enums.ErrorMessageType;

/**
 * 消息管理类
 * @author user
 *
 */
public class MessageManagement {
	public static void printErroMsg(ErrorMessageType emt ){
		String msg = null;
		switch (emt){
		case cannotUseNow:
			msg = "未到发动时机";
			break;
		case cannotUseCause_FullHP:
			msg = "满血，不能使用";
			break;
		case hasUsed:
			msg = "已经使用过，无法再次使用";
			break;
		case hasUsed_Sha:
			msg = "已经出过杀";
			break;
		}
		ViewManagement.getInstance().printChatMsg(msg);
	}
}
