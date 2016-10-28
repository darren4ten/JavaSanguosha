package org.wangfuyuan.sgs.gui.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 玩家头像面板区域
 * --头像图片
 * --技能
 * --血量
 * --身份
 * @author user
 *
 */
public class Panel_HeadImg extends JPanel implements RefreshbleIF{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1541397598002315656L;
	AbstractPlayer player;
	//边框图像
	Image border;
	//头像图像
	Image img ;
	//血条板
	Panel_HP jp_HP ;
	//身份面板
	JLabel jl_id ;
	//技能面板
	Panel_Skill skill;
	//玩家控制面板
	Panel_Control pc;
	/**
	 * 构造器
	 * @param p
	 */
	public Panel_HeadImg(AbstractPlayer p,Panel_Control pc){
		this.pc = pc;
		player = p;
		border = ImgUtil.getPngImgByName("headborder");
		img = p.getInfo().getHeadImg();
		jp_HP = new Panel_HP(p);
		this.setLayout(null);
		this.add(jp_HP);
		jp_HP.setLocation(Const_UI.FRAME_WIDTH/5-jp_HP.getWidth(), 25);
		jl_id = new JLabel(new ImageIcon(ImgUtil.getPngImgByName("id_zhu")));
		jl_id.setSize(35, 50);
		this.add(jl_id);
		jl_id.setLocation(jp_HP.getX()-jl_id.getWidth(), 8);
		skill = new Panel_Skill(p);
		this.add(skill);
		//测试监听
		addMouseListener(listen);
	}
	
	/**
	 * 重写绘制方法
	 */
	public void paint(Graphics g){
		if(img!=null){
			g.drawImage(img, 15, 20,this.getWidth()-25,this.getHeight()-30,null);
		}
		if(border!=null){
			g.drawImage(border, 0, 0,this.getWidth(),this.getHeight(), null);
		}
		//如果在回合中
		/*if(player.getStageNum()!=PlayerIF.STAGE_END){
			g.drawImage(ImgUtil.getPngImgByName("bd_cur"), 10, 10, this.getWidth(),this.getHeight(),null);
		}
		//如果在响应
		if(player.getState().isRequest()){
			g.drawImage(ImgUtil.getPngImgByName("bd_select"), 10, 10, this.getWidth(),this.getHeight(),null);
		}*/
		super.paintChildren(g);
	}
	
	/**
	 * 实现刷新方法
	 */
	@Override
	public void refresh() {
		//血条板刷新
		jp_HP.refresh();
		//技能板刷新
		skill.refresh();
		repaint();
	}
	
	/**
	 * 监听
	 */
	MouseListener listen = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			pc.hand.target.add(player);
		}
		
	};
}
