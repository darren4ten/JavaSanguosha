package org.wangfuyuan.sgs.card;

import java.awt.Image;
import java.util.List;

import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.gui.main.Panel_HandCards;
import org.wangfuyuan.sgs.gui.main.Panel_Player;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.service.ModuleManagement;
import org.wangfuyuan.sgs.service.ViewManagement;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 牌的抽象类
 * 
 * @author user
 * 
 */
public abstract class AbstractCard implements CardIF {
	// 牌的id
	protected int id;
	// 牌面数值
	protected int number;
	// 牌的花色
	protected Colors color;

	// 牌的名称
	protected String name;
	// 牌的类型
	protected int type;
	// 使用目标类型
	protected int targetType;
	// 使用是否需要射程
	protected boolean needRange;

	// 牌的花色图案
	protected Image colorImg;
	// 牌的图像
	protected Image cardImg;
	// 牌的效果图
	protected Image effectImage;

	// 使用服务类
	// protected CardUseServiceIF useService;

	public AbstractCard() {
	}

	/**
	 * 创建一张指定id,数值,花色的牌
	 * 
	 * @param id
	 * @param number
	 * @param color
	 */
	public AbstractCard(int id, int number, Colors color) {
		this.id = id;
		this.number = number;
		this.color = color;
		this.colorImg = ImgUtil.getColorImg(color);
	}

	/**
	 * 构造方法
	 * 
	 * @param id
	 * @param number
	 * @param color
	 * @param name
	 * @param type
	 * @param targetType
	 * @param needRange
	 */
	public AbstractCard(int id, int number, Colors color, String name,
			int type, int targetType, boolean needRange) {
		// super();
		this.id = id;
		this.number = number;
		this.color = color;
		this.name = name;
		this.type = type;
		this.targetType = targetType;
		this.needRange = needRange;

	}

	/**
	 * 显示图片 【注】该方法等效于getCardImg(),本项目中会多次出现这个方法的调用，看起来很蛋疼很费解;
	 * 请不要见怪，之所以会显得多此，完全是早期犯二的产物 懒得改动，就沿用下来
	 * 
	 * @return
	 */
	public Image showImg() {
		return getCardImg();
	}

	/**
	 * 目标检测 如果该牌需要目标，则当牌被点击的时候 调用此方法，来过滤掉不符合的目标 子类具体实现时，会同时更新UI 主要供玩家点击时使用。
	 */
	public void targetCheck(Panel_HandCards ph) {
		if (targetType == CardIF.NONE) {
			List<Panel_Player> list = ph.getMain().getPlayers();
			for (Panel_Player pp : list) {
				pp.disableToUse();
			}
		}
	}

	/**
	 * 单独 判断该牌能否满足条件对某个玩家使用 targetCheck中可能会调用此方法 AI的一些判断也会调用此方法
	 */
	public boolean targetCheck4SinglePlayer(AbstractPlayer user,
			AbstractPlayer target) {
		return true;
	}

	/**
	 * 牌的use方法，提供一些通用操作
	 */
	@Override
	public void use(AbstractPlayer p, List<AbstractPlayer> players) {
		// 清空战场
		ModuleManagement.getInstance().getBattle().clear();
		// 当前出牌区域清空
		p.getState().getUsedCard().clear();
		// 放入当前出牌区
		p.getState().getUsedCard().add(this);
		// 手牌中删除
		p.getAction().removeCard(this);
		// 战场中添加
		ModuleManagement.getInstance().getBattle().addOneCard(this);
		// 丢入弃牌堆
		gc();
		// 使用者手牌刷新
		// 此处注释掉的原因是如果在这里刷新，会把一些状态数值给刷掉
		// 所以刷新留到子类具体实现的时候视情况再用
		// p.refreshView();
		drawEffect(p, players);
	}

	/**
	 * 被动响应打出
	 */
	@Override
	public boolean requestUse(final AbstractPlayer p,
			List<AbstractPlayer> players) {
		// 当前出牌区域清空
		p.getState().getUsedCard().clear();
		// 放入当前出牌区
		p.getState().getUsedCard().add(this);
		// 手牌中删除
		p.getAction().removeCard(this);
		// 战场中添加
		ModuleManagement.getInstance().getBattle().addOneCard(this);
		// 丢入弃牌堆
		gc();
		// 打印消息
		ViewManagement.getInstance().printBattleMsg(
				p.getInfo().getName() + "打出" + this.toString());
		// 使用者手牌刷新
		p.refreshView();
		return true;

	}

	/**
	 * 牌的丢弃方法，提供一些通用操作
	 */
	public void throwIt(AbstractPlayer p) {
		// 当前出牌区域清空
		p.getState().getUsedCard().clear();
		// 手牌中删除
		p.getAction().removeCard(this);
		if (!p.getState().isAI())
			p.updateCards();
		// 放入当前出牌区
		// p.getState().getUsedCard().add(this);
		// 战场中添加
		ModuleManagement.getInstance().getBattle().addOneCard(this);
		// 丢入弃牌堆
		gc();
		// 打印消息
		ViewManagement.getInstance().printMsg(
				p.getInfo().getName() + "丢弃" + this.toString());
	}

	/**
	 * 牌从一个玩家传递给另一个玩家
	 */
	@Override
	public void passToPlayer(AbstractPlayer fromP, AbstractPlayer receiverP) {
		fromP.getAction().removeCard(this);
		receiverP.getAction().addCardToHandCard(this);
	}

	/**
	 * 丢入牌堆回收
	 */
	public void gc() {
		ModuleManagement.getInstance().getGcList().add(this);
	}

	/**
	 * 返回牌面大小的字符串形式
	 * 
	 * @return
	 */
	public String getNumberToString() {
		switch (number) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return String.valueOf(number);
		}
	}

	/**
	 * 通用方法 返回牌的描述
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return this.color.toString() + this.getNumberToString()
				+ this.getName();
	}

	/**
	 * 检测是否在使用范围内
	 */
	public boolean isInRange(AbstractPlayer user, AbstractPlayer target) {
		int p2p = user.getFunction().getDistance(target);
		int att = user.getFunction().getAttackDistance();
		int def = target.getFunction().getDefenceDistance();
		return (att - def) >= p2p;

	}

	/*
	 * 绘制特效
	 */
	protected void drawEffect(AbstractPlayer p, List<AbstractPlayer> players) {

	}

	public boolean isNeedRange() {
		return needRange;
	}

	public void setNeedRange(boolean needRange) {
		this.needRange = needRange;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getColorImg() {
		return colorImg;
	}

	public void setColorImg(Image colorImg) {
		this.colorImg = colorImg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public Image getCardImg() {
		return cardImg;
	}

	public void setCardImg(Image cardImg) {
		this.cardImg = cardImg;
	}

	public Image getEffectImage() {
		return effectImage;
	}

	public void setEffectImage(Image effectImage) {
		this.effectImage = effectImage;
	}

}
