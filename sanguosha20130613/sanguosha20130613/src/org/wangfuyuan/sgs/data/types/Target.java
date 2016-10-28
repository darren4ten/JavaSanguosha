package org.wangfuyuan.sgs.data.types;

import java.util.ArrayList;
import java.util.List;

import org.wangfuyuan.sgs.player.AbstractPlayer;


/**
 * 封装了牌的使用目标对象
 * @author user
 *
 */
public class Target {
	//存储人物模型
	List<AbstractPlayer> list;
	//目标上限
	int limit =1;
	//是否需要检测
	boolean needCheck ;
	
	//构造
	public Target(int limit){
		list = new ArrayList<AbstractPlayer>();
		this.limit = limit;
	}
	
	/**
	 * 添加一个
	 */
	public void add(AbstractPlayer p){
		//如果重复则返回
		if(list.contains(p))return;
		//若达到上限则删除第一个再添加
		if(list.size()>=limit){
			list.remove(0);
			list.add(p);
		}else{
			list.add(p);
		}
	}
    /**
     * 判断是否为空
     * @return
     */
	public boolean isEmpty(){
		return (list==null||list.size()==0);
	}
	public List<AbstractPlayer> getList() {
		return list;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setList(List<AbstractPlayer> list) {
		this.list = list;
	}

	public boolean isNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}
	
	
}
