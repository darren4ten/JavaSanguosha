package org.wangfuyuan.sgs.data.enums;

import java.awt.Image;

import org.wangfuyuan.sgs.util.ImgUtil;


/**
 * 玩家身份枚举
 * 
 * @author user
 * 
 */
public enum Identity {
	ZHUGONG, ZHONGCHEN, NEIJIAN, FANZEI;
	public Image toImg() {
		switch (this) {
		case ZHUGONG:
			return ImgUtil.getPngImgByName("id_zhu");
		case ZHONGCHEN:
			return ImgUtil.getPngImgByName("id_zhong");
		case NEIJIAN:
			return ImgUtil.getPngImgByName("id_nei");
		case FANZEI:
			return ImgUtil.getPngImgByName("id_fan");
		}
		return null;
	}
}
