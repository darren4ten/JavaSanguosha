package org.wangfuyuan.sgs.card.kits;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;

/**
 * ��������� �ṩһЩ�����Ƶ�ͨ�÷���
 * 
 * @author user
 * 
 */
public abstract class AbstractKitCard extends AbstractCard {
	// �Ƿ���и
	protected boolean isWuXie;
	// ��ҿ���������UI�����ӵ�
	protected Panel_Control pc;

	/*
	 * Ŀ�꼯�� ��Ҫ�Ƿ�ֹ���ֶ��̲߳���ʱ����Ϊ����������б�ǿ��final���������һЩ���⣬������ʱ����һ���������洢 Ҳ�������Ҷ�����
	 */
	protected List<AbstractPlayer> targetPlayers;

	public AbstractKitCard(int id, int number, Colors color) {
		super(id, number, color);
	}

	public AbstractKitCard() {
	}

	/**
	 * ����ʹ��ǰ�ĳ�ʼ��
	 */
	protected void initKit() {
		isWuXie = false;
	}

	/**
	 * ��дuse
	 */
	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		if (!p.getState().isAI())
			pc = (Panel_Control) p.getPanel();
		targetPlayers = new ArrayList<AbstractPlayer>(players);
		initKit();
	}

	/**
	 * ѯ����и�ɻ�
	 * 
	 * ��и�ɻ���ʵ�ַ����� �������ж���һ��booleanֵ��ʾ�Ƿ���и �����������ѯ�ʳ����Ƿ�����и����������и��booleanֵȡ��
	 * �������ս����������ʵ��ʱ�����booleanֵ�ж��Ƿ񷢶�Ч��
	 * 
	 * @param p
	 * @param players
	 */
	public void askWuXieKeJi(AbstractPlayer p, List<AbstractPlayer> players) {
		if (hasWuxiekejiInBattle()) {
			p.refreshView();
			System.out.println("��������и");
			// ѯ����и
			List<AbstractPlayer> askPlayers = ModuleManagement.getInstance()
					.getPlayerList();
			for (int i = 0; i < askPlayers.size(); i++) {
				// ������˳���и
				if (askPlayers.get(i).getRequest().requestWuXie()) {
					isWuXie = true;
					break;
				}
			}
		}
	}

	/*
	 * �����Ƿ�����и����
	 */
	protected boolean hasWuxiekejiInBattle() {
		for (AbstractPlayer p : ModuleManagement.getInstance().getPlayerList()) {
			if (p.hasCard(Const_Game.WUXIEKEJI))
				return true;
		}
		return false;
	}

	/**
	 * ������Ļ��� �󲿷ֿ���ʹ�����ģ�� ��Щ�������Ҫ��д�������硢��и��
	 */
	@Override
	protected void drawEffect(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		//��Ŀ��Ľ���
		if (targetType == CardIF.SELECT) {
			final AbstractPlayer tmp = players.get(0);
			ViewManagement.getInstance().printBattleMsg(
					p.getInfo().getName() + "��" + tmp.getInfo().getName()
							+ "ʹ����" + this.toString());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (getEffectImage() != null)
						PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.drawLine(p, tmp);
				}
			});
			//��Ŀ��Ľ���
		}else{
			ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+"ʹ����"+getName());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(getEffectImage()!=null)PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.clearAfter(1000);
				}
			});
		
		}
	}
}
