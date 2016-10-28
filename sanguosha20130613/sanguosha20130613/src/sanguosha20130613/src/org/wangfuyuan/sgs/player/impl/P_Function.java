package org.wangfuyuan.sgs.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.Player_FunctionIF;

/**
 * 封装了玩家的一些通用操作函数
 * @author user
 *
 */
public class P_Function implements Player_FunctionIF{
	AbstractPlayer player;
	public P_Function(AbstractPlayer player){
		this.player = player;
	}
	/**
	 * 获取上家
	 * @return
	 */
	public AbstractPlayer getLastPlayer() {
		AbstractPlayer p = player;
		while (p.getNextPlayer() != player) {
			p = p.getNextPlayer();
		}
		return p;
	}

	/**
	 * 是否满血
	 * 
	 * @return
	 */
	public boolean isFullHP() {
		return (player.getState().getCurHP() == player.getInfo().getMaxHP());
	}

	/**
	 * 空血
	 * 
	 * @return
	 */
	public void isNullHP() {

	}
	
	/**
	 * 计算两个玩家之间的距离
	 * 两者各按逆时针计算，取最小值
	 */
	public int getDistance(AbstractPlayer p){
		AbstractPlayer pNext = player.getNextPlayer();
		int i = 1;
		int j = 1;
		while(pNext!=p){
			i++;
			pNext = pNext.getNextPlayer();
		}
		pNext = p.getNextPlayer();
		while(pNext!=player){
			j++;
			pNext = pNext.getNextPlayer();
		}
		return i<=j?i:j;
	}
	/**
	 * 获取场上全部玩家
	 */
	@Override
	public List<AbstractPlayer> getAllPlayers() {
		List<AbstractPlayer> list = new ArrayList<AbstractPlayer>();
		AbstractPlayer p = player.getNextPlayer();
		while(p!=player){
			list.add(p);
			p = p.getNextPlayer();
		}
		return list;
	}
	
	/**
	 * 获取场上玩家所有手牌
	 */
	public List<String> getAllPlayersHandCard(){
		List<String> listRes = new ArrayList<String>();
		List<AbstractPlayer> list = getAllPlayers();
		for (int i = 0; i < list.size(); i++) {
			StringBuilder sb = new StringBuilder(list.get(i).getInfo().getName()+":");
			for (int j = 0; j < list.get(i).getState().getCardList().size(); j++) {
				String s =  list.get(i).getState().getCardList().get(j).toString();
				sb.append(s+",");
			}
			listRes.add(new String(sb));
		}
		return listRes;
	}
	
	/**
	 *获取攻击距离 
	 */
	public int getAttackDistance(){
		return player.getState().getAttDistance();
	}
	
	/**
	 *获取防守距离 
	 */
	public int getDefenceDistance(){
		return player.getState().getDefDistance();
	}
	
	/**
	 * 锦囊使用距离
	 * 默认与攻击距离相同
	 */
	public int getKitUseDistance(){
		return getAttackDistance();
	}
	
	/**
	 * 翻一张牌判定花色
	 */
	@Override
	public boolean checkRollCard(AbstractCard card,Colors... color) {
		boolean result = false ; 
		for (int i = 0; i < color.length; i++) {
			if(card.getColor() == color[i]){
				result = true;
				break;
			}
		}
		//判定牌触发
		player.getTrigger().afterCheck(card, result);
		return result;
	}
	
	/**
	 * 翻一张牌判定数值
	 */
	@Override
	public boolean checkRollCard(AbstractCard card, int max, int min) {
		int n = card.getNumber();
		return n>=min&&n<=max;
	}
	
	/**
	 * 检测是否在使用范围内
	 */
	@Override
	public boolean isInRange(AbstractPlayer target){
		int p2p = getDistance(target);
		int att = getAttackDistance();
		int def = getDefenceDistance();
		return (att-def)>=p2p;
		
	}
	/**
	 * 是否同一国家
	 */
	@Override
	public boolean isSameCountry(AbstractPlayer target) {
		return player.getInfo().getCountry()==target.getInfo().getCountry();
	}
	
	/**
	 * 是否同性
	 */
	@Override
	public boolean isSameSex(AbstractPlayer target) {
		return player.getInfo().sex == target.getInfo().sex;
	}
	
}
