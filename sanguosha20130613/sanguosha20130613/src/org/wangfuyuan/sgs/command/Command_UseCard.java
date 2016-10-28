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
		// �����ʾ��Ϣ
		ViewManagement.getInstance().getPrompt().clear();
		// ���û��ѡ������򷵻�
		if (ph.getTarget().isEmpty()
				&& ph.getSelectedList().get(0).getCard().getTargetType() != CardIF.NONE) {
			System.out.println("no select!"+ph.getSelectedList().get(0).getCard().toString());
			return;
		}
		// �����Ƶ�use
		ph.getSelectedList().get(0).getCard().use(ph.getPlayer(),
				ph.getTarget().getList());
		// ����б�
		ph.getSelectedList().clear();
	}

}
