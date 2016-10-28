package org.wangfuyuan.sgs.card.changed;

import java.awt.Image;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.ComboCardIF;
import org.wangfuyuan.sgs.card.base.Card_Sha;
import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * ×éºÏµÄ¡¾É±¡¿
 * @author user
 *
 */
public class Combo_Sha implements ComboCardIF{
	List<AbstractCard> realCardList;
	public Combo_Sha(List<AbstractCard> list){
		this.realCardList = list;
	}
	@Override
	public int getCardType() {
		return Const_Game.SHA;
	}

	@Override
	public List<AbstractCard> getRealCards() {
		return realCardList;
	}

	@Override
	public void use(AbstractPlayer p, AbstractPlayer toP) {
		new Card_Sha().executeSha(p, toP);
	}
	
	public Image getEffectImage(){
		return ImgUtil.getPngImgByName("ef_sha");
	}
}
