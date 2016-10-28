package org.wangfuyuan.sgs.gui.select;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 代理武将的面板
 * @author user
 *
 */
public class Pane_ProxyPlayer extends JLabel{
	
	private static final long serialVersionUID = 4985646644606464150L;
	ProxyPlayer pp;
	Panel_Select ps;
	Border border = BorderFactory.createLineBorder(Color.lightGray, 1);
	
	public Pane_ProxyPlayer(ProxyPlayer pp,Panel_Select ps){
		this.pp = pp;
		this.ps = ps;
		setBorder(border);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBounds(new Rectangle(Const_UI.PROXYWIDTH-5,Const_UI.PROXYHEIGHT-10));
		/*BufferedImage img = ImgUtil.getJpgImgByName(pp.getImgName());
		Image newImg = img.getSubimage(0, 0, img.getWidth(), img.getHeight());
		setIcon(new ImageIcon(newImg));*/
		
		
	}
	
	/**
	 * 绘制
	 */
	public void paint(Graphics g){
		super.paint(g);
		if(pp!=null){
			BufferedImage img = ImgUtil.getJpgImgByName(pp.getImgName());
			g.drawImage(img, 0, 0, Const_UI.PROXYWIDTH,Const_UI.PROXYHEIGHT, null);
		}
		super.paintBorder(g);
	}
	
	public void addListener(){
		addMouseListener(listen);
	}
	
	public void resetBorder(){
		setBorder(border);
	}
	/**
	 * 鼠标监听
	 */
	MouseListener listen = new MouseAdapter() {
		final Border border2 = BorderFactory.createLineBorder(Color.red, 5);
 		@Override
		public void mouseEntered(MouseEvent e) {
 			setBorder(border2);
 			
 		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBorder(border);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			ps.selectOne(pp);
		}
		
	};

	public ProxyPlayer getPp() {
		return pp;
	}

	public void setPp(ProxyPlayer pp) {
		this.pp = pp;
	}
	
	}
