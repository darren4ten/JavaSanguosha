package org.wangfuyuan.sgs.service.AI;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * AĮ����
 * @author user
 *
 */
public class AISpeakService {
	/**
	 * �ҳ�������
	 */
	public static void sayFuckBoss(AbstractPlayer speaker){
		String word = "[AI]"+speaker.getInfo().getName()+":���SB����";
		ViewManagement.getInstance().printChatMsg(word);
	}
}
