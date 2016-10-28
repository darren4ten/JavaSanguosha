package org.wangfuyuan.sgs.gui.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.GameOver;
import org.wangfuyuan.sgs.util.ImgUtil;
/**
 * 游戏结束面板
 * @author user
 *
 */
public class Panel_GameOver extends JPanel{
	private static final long serialVersionUID = -6807835044745693395L;
	// 背景图
	BufferedImage bgimg = ImgUtil.getJpgImgByName("bg");
	JLabel jl_words = new JLabel("",SwingConstants.CENTER);
	JLabel jl_winner = new JLabel("",SwingConstants.CENTER);
	Font font = new Font("楷体", Font.BOLD, 100);
	GameOver go ;
	public Panel_GameOver(GameOver go){
		this.go = go;
		setLayout(null);
		setSize(Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT);
		setLocation(0, 0);
		//setOpaque(false);
		//jl_winner.setOpaque(false);
		//jl_words.setOpaque(false);
		jl_words.setForeground(Color.white);
		jl_winner.setForeground(Color.white);
		jl_winner.setFont(font);
		jl_words.setFont(font);
		jl_words.setText(go.getWords());
		jl_winner.setText(go.getWinner());
		jl_words.setSize(Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT/2);
		jl_winner.setSize(Const_UI.FRAME_WIDTH,Const_UI.FRAME_HEIGHT/2);
		jl_words.setLocation(0, 0);
		jl_winner.setLocation(0, Const_UI.FRAME_HEIGHT/3);
		add(jl_winner);
		add(jl_words);
	}
	
	public void paintComponent(Graphics g){
		drawUnable(g);
		g.drawImage(bgimg, 0, 0, this.getWidth(), this.getHeight(), null);
		
	}
	
	/**
	 * 绘制不可用
	 * 
	 * @param g
	 */
	private void drawUnable(Graphics g) {
		/*
		 * BufferedImage image = new BufferedImage(getWidth(), getHeight(),
		 * BufferedImage.TYPE_INT_ARGB);
		 */
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.darkGray);
		g2.fillRect(0, 0, getWidth(),getHeight());
		g2.setComposite(AlphaComposite.SrcOver.derive(Const_UI.CARD_UNABLE));
	}
}
