package org.wangfuyuan.sgs.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.wangfuyuan.sgs.card.CardIF;
import org.wangfuyuan.sgs.data.enums.Colors;

/**
 * ͼ���ȡ�Ĺ�����
 * @author user
 *
 */
public class ImgUtil {
	/**
	 * ��ȡjpgͼ�񷽷�
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
	 * ��ȡpng�񷽷�
	 * @param name
	 * @return
	 */
	public static BufferedImage getPngImgByName(String name){
		BufferedImage bfimg = null;
		try {
			bfimg = ImageIO.read(ClassLoader.getSystemResourceAsStream("img"+"/"+name+".png"));
		} catch (Exception e) {
			System.out.println("�쳣name:"+name);
			e.printStackTrace();
		}
		return bfimg;
	}
	
	/**
	 * ��ȡ��ɫͼ��
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
