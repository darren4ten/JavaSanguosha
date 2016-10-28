package org.wangfuyuan.sgs.card.base;

import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.card.EffectCardIF;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.PaintService;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * Ã“
 * @author user
 *
 */
public class Card_Tao extends AbstractCard implements EffectCardIF{
	public Card_Tao(){
		
	}
	public Card_Tao(int id, int number, Colors color) {
		
		super(id, number, color);
		this.name = "Ã“";
		this.type = Const_Game.TAO;
		this.targetType = CardIF.NONE;
	}

	public Image showImg() {
		return ImgUtil.getPngImgByName("ktao");
	}

	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		super.use(p, players);
		// TODO Auto-generated method stub
		ViewManagement.getInstance().printBattleMsg(p.getInfo().getName()+" π”√¡ÀÃ“");
		p.getAction().tao();
		p.getPanel().refresh();
	}

	
	
	@Override
	protected void drawEffect(final AbstractPlayer p, List<AbstractPlayer> players) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					PaintService.drawEffectImage(getEffectImage(), p);
					PaintService.clearAfter(1000);
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Image getEffectImage() {
		return ImgUtil.getPngImgByName("ef_tao");
	}
}
