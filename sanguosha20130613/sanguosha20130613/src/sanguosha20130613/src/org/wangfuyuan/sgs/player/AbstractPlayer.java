package org.wangfuyuan.sgs.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.wangfuyuan.sgs.card.AbstractCard;
import org.wangfuyuan.sgs.gui.main.Panel_Control;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.RefreshbleIF;
import org.wangfuyuan.sgs.player.impl.P_Action;
import org.wangfuyuan.sgs.player.impl.P_Function;
import org.wangfuyuan.sgs.player.impl.P_Info;
import org.wangfuyuan.sgs.player.impl.P_Process;
import org.wangfuyuan.sgs.player.impl.P_Request;
import org.wangfuyuan.sgs.player.impl.P_State;
import org.wangfuyuan.sgs.player.impl.P_Trigger;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.service.AI.AIProcessService;

/**
 * 玩家的抽象类
 * 
 * 组合了：玩家信息--玩家状态 --玩家行为类 --玩家响应类 --玩家触发类
 * 
 * 包含： 一些玩家的通用方法
 * 
 * @author Wangfuyuan
 * 
 */

public abstract class AbstractPlayer implements PlayerIF {

	// 玩家信息
	protected P_Info info;

	// 玩家状态
	protected P_State state;
	
	// 玩家回合动作类
	protected Player_ProcessIF process;

	// 玩家行为
	protected Player_ActionIF action;
	
	// 玩家响应请求
	protected Player_RequestIF request;

	// 玩家触发
	protected Player_TriggerIF trigger;

	// 玩家的功能函数类
	protected Player_FunctionIF function;
	
	// 下家的引用
	protected AbstractPlayer nextPlayer;

	// AI控制器
	protected AIProcessService AIsvr;

	// 弃牌控制(是否在自己回合)
	protected boolean isSkip = true;

	// 所处回合阶段
	protected int stageNum = STAGE_END;
	
	// 关联的显示面板
	protected RefreshbleIF panel;

	/**
	 * 构造
	 */
	public AbstractPlayer() {
		info = new P_Info();
		initial();
	}

	/**
	 *  初始化状态
	 */
	protected void initial() {
		state = new P_State(info);
		action = new P_Action(this);
		request = new P_Request(this);
		trigger = new P_Trigger(this);
		process = new P_Process(this);
		function = new P_Function(this);
		AIsvr = new AIProcessService(this);
	}

	@Override
	public void process() {
		/*// 如果是AI，则调用AI服务类的process
		if (this.getState().isAI()) {
			AIsvr.start();
			return;
		}*/
		ViewManagement.getInstance().refreshAll();
		process.start();
	}

	/**
	 * 抽象方法 载入技能
	 */
	public abstract void loadSkills(String name);
	
	
	/**
	 * 刷新面板
	 * @return
	 */
	public void refreshView(){
		getPanel().refresh();
	}
	
	/**
	 * 整理刷新手牌
	 */
	public void updateCards(){
		Panel_Control pc = (Panel_Control) getPanel();
		Panel_HandCards ph = pc.getHand();
		//ph.updateCards();
		ph.carding();
	}
	/**
	 * 等待选择
	 * @return
	 */
	public AbstractCard toSelectCard(List<AbstractCard> list){
		if(getState().isAI()){
			int n = new Random().nextInt(list.size());
			return list.get(n);
		}else{
			getState().setSelectCard(null);
			while(getState().getSelectCard()==null){
				
			}
			return getState().getSelectCard();
		}
	}
	
	/**
	 * 是否拥有某种牌
	 * @return
	 */
	public boolean hasCard(int type){
		for (int i = 0; i < getState().getCardList().size(); i++) {
			if(getState().getCardList().get(i).getType()==type)return true;
		}
		return false;
	}
	
	/**
	 * 获取同势力人物集合
	 * @return
	 */
	public List<AbstractPlayer> getSameCountryPlayers(){
		List<AbstractPlayer> result = new ArrayList<AbstractPlayer>();
		AbstractPlayer p = getNextPlayer();
		while(p!=this){
			if(p.getInfo().getCountry()==getInfo().getCountry()){
				result.add(p);
			}
			p = p.getNextPlayer();
		}
		return result;
	}
	
	/**
	 * 设置是否处于请求响应状态
	 * 牌型为type
	 * @param isRequest
	 * @param type
	 */
	public void setRequest(boolean isRequest,int type){
		getState().setRequest(isRequest);
		getRequest().setCurType(type);
	}
	
	//-----------------------------------
	public P_Info getInfo() {
		return info;
	}

	public void setInfo(P_Info info) {
		this.info = info;
	}

	public P_State getState() {
		return state;
	}

	public void setState(P_State state) {
		this.state = state;
	}

	public Player_ActionIF getAction() {
		return action;
	}

	public void setAction(Player_ActionIF action) {
		this.action = action;
	}

	public Player_RequestIF getRequest() {
		return request;
	}

	public void setRequest(Player_RequestIF request) {
		this.request = request;
	}

	public Player_TriggerIF getTrigger() {
		return trigger;
	}

	public void setTrigger(Player_TriggerIF trigger) {
		this.trigger = trigger;
	}

	public AbstractPlayer getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(AbstractPlayer nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public boolean isSkip() {
		return isSkip;
	}

	public void setSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}

	public RefreshbleIF getPanel() {
		return panel;
	}

	public void setPanel(RefreshbleIF panel) {
		this.panel = panel;
	}

	public Player_ProcessIF getProcess() {
		return process;
	}

	public void setProcess(P_Process process) {
		this.process = process;
	}

	public int getStageNum() {
		return stageNum;
	}

	public void setStageNum(int stageNum) {
		this.stageNum = stageNum;
	}

	public Player_FunctionIF getFunction() {
		return function;
	}

	public void setFunction(Player_FunctionIF function) {
		this.function = function;
	}

}