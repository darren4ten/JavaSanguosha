package org.wangfuyuan.sgs.command;

import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ÆúÅÆ
 * 
 * @author user
 * 
 */
public class Command_ThrowCards implements Runnable {
	Panel_HandCards ph;

	public Command_ThrowCards(Panel_HandCards ph) {
		this.ph = ph;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < ph.getSelectedList().size(); i++) {
			ph.getSelectedList().get(i).getCard().throwIt(ph.getPlayer());
		}
		ph.refresh();
		int n = ph.getPlayer().getState().getCardList().size()
				- ph.getPlayer().getState().getCurHP();
		if (n > 0) {
			ph.setSelectLimit(
					ph.getPlayer().getState().getCardList().size()
							- ph.getPlayer().getState().getCurHP());
			ViewManagement.getInstance().getPrompt().show_RemindToThrow(n);
		} else {
			ViewManagement.getInstance().getPrompt().clear();
		}
	}

}
