package org.wangfuyuan.sgs.player;
/**
 * »ØºÏ6½×¶Î
 * @author user
 *
 */
public interface Player_ProcessIF {
	void start();
	void stage_begin();
	void stage_check();
	void stage_addCards();
	void stage_useCards();
	void stage_throwCrads();
	void stage_end();
	void  setSkilling(boolean b);
	boolean isSkilling();
	void setCanUseCard(boolean canAddCard);
}
