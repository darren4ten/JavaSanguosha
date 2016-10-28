package org.wangfuyuan.sgs.gui.select;

import org.wangfuyuan.sgs.player.AbstractPlayer;

/**
 * 代理对象
 * 只有标识id和图像显示
 * 存储一个真正对象的引用，延迟加载
 * 当确定选择后，才将其真正加载
 * @author user
 *
 */
public class ProxyPlayer {
	AbstractPlayer player ;
	String id ;
	String imgName;
	
	public ProxyPlayer(String characterID,String imgName){
		this.id = characterID;
		this.imgName = imgName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	
	
}
