package org.wangfuyuan.sgs.command;

import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.service.ViewManagement;

public class Command_UseCard implements Runnable {
	Panel_HandCards ph;

	public Command_UseCard(Panel_HandCards ph) {
		this.ph = ph;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		// 清空提示消息
		ViewManagement.getInstance().getPrompt().clear();
		// 如果没有选择对象则返回
		if (ph.getTarget().isEmpty()
				&& ph.getSelectedList().get(0).getCard().getTargetType() != CardIF.NONE) {
			System.out.println("no select!"+ph.getSelectedList().get(0).getCard().toString());
			return;
		}
		// 调用牌的use
		ph.getSelectedList().get(0).getCard().use(ph.getPlayer(),
				ph.getTarget().getList());
		// 清空列表
		ph.getSelectedList().clear();
	}

}
