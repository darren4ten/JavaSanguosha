package org.wangfuyuan.sgs.player.impl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.data.enums.Country;


/**
 * 玩家信息类
 * 封装玩家的基本信息数据
 * @author user
 *
 */
public class P_Info {
	
	//人物名称
	protected String name ;
	//血量上限
	protected int maxHP;
	//人物性别 真-男；假-女
	protected boolean sex;
	//所属势力
	protected Country country ;
	//头像
	protected Image headImg;
	//免疫的牌
	protected List<Integer> immuneCard = new ArrayList<Integer>();
	//主动技能的类名
	protected List<String> skillName = new ArrayList<String>();
	//锁定技类名
	protected List<String> lockingSkill = new ArrayList<String>();
	


	public P_Info(){
		
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Image getHeadImg() {
		return headImg;
	}

	public void setHeadImg(Image headImg) {
		this.headImg = headImg;
	}

	public List<Integer> getImmuneCard() {
		return immuneCard;
	}


	public void setImmuneCard(List<Integer> immuneCard) {
		this.immuneCard = immuneCard;
	}


	public List<String> getSkillName() {
		return skillName;
	}


	public void setSkillName(List<String> skillName) {
		this.skillName = skillName;
	}


	public List<String> getLockingSkill() {
		return lockingSkill;
	}


	public void setLockingSkill(List<String> lockingSkill) {
		this.lockingSkill = lockingSkill;
	}


	
}
