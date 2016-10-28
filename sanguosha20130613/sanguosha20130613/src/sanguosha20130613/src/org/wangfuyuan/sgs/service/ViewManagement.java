package org.wangfuyuan.sgs.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Message;
import org.wangfuyuan.sgs.gui.main.Panel_Prompt;
import org.wangfuyuan.sgs.gui.main.RefreshbleIF;
import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 视图管理类 包含游戏组件的刷新等操作
 * 
 * @author user
 * 
 */
public class ViewManagement {

	// 单例构造
	private static ViewManagement instance;

	private ViewManagement() {
	}

	public static ViewManagement getInstance() {
		if (instance == null) {
			instance = new ViewManagement();
		}
		return instance;
	}

	/**
	 * 重置
	 */
	public static void reset() {
		instance = new ViewManagement();
	}

	// 可刷新组件集合
	List<RefreshbleIF> refreshList = new ArrayList<RefreshbleIF>();
	// 消息面板
	JTextArea msg;
	// 聊天面板
	JTextArea msgChat;
	// 提示信息
	Panel_Prompt prompt;
	// 战场消息面板
	Panel_Message message;

	/**
	 * 通知所有组件刷新
	 */
	public void refreshAll() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < refreshList.size(); i++) {
					refreshList.get(i).refresh();
					// System.out.println(i+"刷新");
				}
				printChatMsg("refresh!");
			}
		});
	}

	/**
	 * 打印消息
	 */
	public void printMsg(String str) {
		msg.append(str + "\n");
		msg.setCaretPosition(msg.getText().length());
		// printBattleMsg(str);
	}

	/**
	 * 聊天信息追加
	 * 
	 * @return
	 */
	public void printChatMsg(String msg) {
		// msgChat.append("【AI孙权】道：我去你妈了个彼得！会不会玩啊"+"\n");
		msgChat.append(msg + "\n");
		msgChat.setCaretPosition(msgChat.getText().length());
	}

	/**
	 * 战场信息显示 战场信息默认同时在消息面板出现
	 * 
	 * @return
	 */
	public void printBattleMsg(String str) {
		message.addMessage(str);
		message.repaint();
		printMsg(str);
	}

	/**
	 * 询问玩家是否发动技能
	 * 
	 * @return
	 */
	public void ask(final AbstractPlayer player, final String skillName) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Panel_Control pc = (Panel_Control) player.getPanel();
				Panel_HandCards ph = pc.getHand();
				ViewManagement.getInstance().getPrompt().show_Message(
						"是否发动" +skillName);
				ph.unableToUseCard();
				ph.enableOKAndCancel();

			}
		});
	}

	public List<RefreshbleIF> getRefreshList() {
		return refreshList;
	}

	public JTextArea getMsg() {
		return msg;
	}

	public void setMsg(JTextArea msg) {
		this.msg = msg;
	}

	public void setMsgChat(JTextArea msgChat) {
		this.msgChat = msgChat;
	}

	public Panel_Prompt getPrompt() {
		return prompt;
	}

	public void setPrompt(Panel_Prompt prompt) {
		this.prompt = prompt;
	}

	public void setMessage(Panel_Message message) {
		this.message = message;
	}

}
