package org.wangfuyuan.sgs.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.data.enums.Colors;

/**
 * 图像读取的工具类
 * @author user
 *
 */
public class ImgUtil {
	/**
	 * 获取jpg图像方法
	 * @param name
	 * @return
	 */
	public static BufferedImage getJpgImgByName(String name){
		BufferedImage bfimg = null;
		try {
			bfimg = ImageIO.read(ClassLoader.getSystemResourceAsStream("img"+"/"+name+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bfimg;
	}
	
	/**
	 * 获取png像方法
	 * @param name
	 * @return
	 */
	public static BufferedImage getPngImgByName(String name){
		BufferedImage bfimg = null;
		try {
			bfimg = ImageIO.read(ClassLoader.getSystemResourceAsStream("img"+"/"+name+".png"));
		} catch (Exception e) {
			System.out.println("异常name:"+name);
			e.printStackTrace();
		}
		return bfimg;
	}
	
	/**
	 * 获取花色图像
	 * @param c
	 * @return
	 */
	public static final BufferedImage getColorImg(Colors c){
		switch(c){
		case HEITAO:
			return ImgUtil.getPngImgByName(CardIF.HEITAO_FN);
		case HONGXIN:
			return ImgUtil.getPngImgByName(CardIF.HONGXIN_FN);
		case MEIHUA:
			return ImgUtil.getPngImgByName(CardIF.MEIHUA_FN);
		case FANGKUAI:
			return ImgUtil.getPngImgByName(CardIF.FANGKUAI_FN);
		}
		return null;
		
	}
}
