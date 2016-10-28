package org.wangfuyuan.sgs.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.card.DelayKitIF;
import org.wangfuyuan.sgs.data.enums.Identity;
import org.wangfuyuan.sgs.data.types.EquipmentStructure;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
import org.wangfuyuan.sgs.skills.SkillIF;


/**
 * 玩家状态类
 * 封装游戏时玩家的各项数据
 * @author user
 *
 */
public class P_State {
	//玩家的身份
	protected Identity id ;
	//当前血量
	protected int curHP;
	//是否死亡
	protected boolean isDead;
	//手牌集合
	protected List<AbstractCard> cardList ;
	//免疫牌
	protected List<Integer> immuneCards;
	//是否为AI
	protected boolean isAI;
	//装备集合
	protected EquipmentStructure equipment;
	//所中的判定牌集合
	protected List<AbstractCard> checkedCardList;
	//回合中当前打出的牌
	protected List<AbstractCard> usedCard;
	//攻击距离
	protected int attDistance ;
	//防御距离
	protected int defDistance ;
	//主动技能
	protected List<SkillIF> skill;
	//锁定技能
	protected List<LockingSkillIF> lockingSkill;
	//额外伤害值
	protected int extDamage;
	//是否出过杀
	protected boolean usedSha ;
	//是否处于响应他人状态
	protected boolean isRequest;
	//响应他人的结果
	protected int res;
	//当进入选牌时候，选择的牌
	protected AbstractCard selectCard;
	/**
	 * 构造
	 * @param info
	 */
	public P_State(P_Info info){
		cardList = new ArrayList<AbstractCard>();
		equipment = new EquipmentStructure();
		checkedCardList = new ArrayList<AbstractCard>();
		usedCard = new ArrayList<AbstractCard>();
		skill = new ArrayList<SkillIF>();
		lockingSkill = new ArrayList<LockingSkillIF>();
		attDistance = 1;
		defDistance = 0;
		extDamage = 0;
		curHP = info.getMaxHP();
		immuneCards = new ArrayList<Integer>(info.getImmuneCard());
	}

	
	/**
	 * 回合开始前的一些状态清空
	 */
	public void reset(){
		
	}
	/**
	 * 是否中某个延迟锦囊
	 * @param type
	 * @return
	 */
	public boolean hasDelayKit(int type){
		for (AbstractCard c : checkedCardList) {
			DelayKitIF d = (DelayKitIF) c;
			if(d.getKitCardType() == type){
				return true;
			}
		}
		return false;
	}
	public void attChange(int n){
		attDistance += n;
	}
	
	public void defChange(int n){
		defDistance+=n;
	}
	public List<AbstractCard> getCardList() {
		return cardList;
	}

	public void setCardList(List<AbstractCard> cardList) {
		this.cardList = cardList;
	}

	

	public EquipmentStructure getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentStructure equipment) {
		this.equipment = equipment;
	}

	public List<AbstractCard> getCheckedCardList() {
		return checkedCardList;
	}

	public void setCheckedCardList(List<AbstractCard> checkedCardList) {
		this.checkedCardList = checkedCardList;
	}

	public int getAttDistance() {
		return attDistance;
	}

	public void setAttDistance(int attDistance) {
		this.attDistance = attDistance;
	}

	public int getDefDistance() {
		return defDistance;
	}

	public void setDefDistance(int defDistance) {
		this.defDistance = defDistance;
	}

	public boolean isUsedSha() {
		return usedSha;
	}

	public void setUsedSha(boolean usedSha) {
		this.usedSha = usedSha;
	}

	public Identity getId() {
		return id;
	}

	public void setId(Identity id) {
		this.id = id;
	}

	public int getCurHP() {
		return curHP;
	}

	public void setCurHP(int curHP) {
		this.curHP = curHP;
	}

	public List<AbstractCard> getUsedCard() {
		return usedCard;
	}

	public void setUsedCrad(List<AbstractCard> usedCard) {
		this.usedCard = usedCard;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}



	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public void setUsedCard(List<AbstractCard> usedCard) {
		this.usedCard = usedCard;
	}

	public int getRes() {
		return res;
	}

	

	public List<SkillIF> getSkill() {
		return skill;
	}

	public void setSkill(List<SkillIF> skill) {
		this.skill = skill;
	}

	public List<LockingSkillIF> getLockingSkill() {
		return lockingSkill;
	}

	public void setLockingSkill(List<LockingSkillIF> lockingSkill) {
		this.lockingSkill = lockingSkill;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public boolean isRequest() {
		return isRequest;
	}

	public void setRequest(boolean isRequest) {
		this.isRequest = isRequest;
	}
	
	public int getExtDamage() {
		return extDamage;
	}

	public void setExtDamage(int extDamage) {
		this.extDamage = extDamage;
	}

	public AbstractCard getSelectCard() {
		return selectCard;
	}

	public void setSelectCard(AbstractCard selectCard) {
		this.selectCard = selectCard;
	}

	public List<Integer> getImmuneCards() {
		return immuneCards;
	}

	public void setImmuneCards(List<Integer> immuneCards) {
		this.immuneCards = immuneCards;
	}
	
}
