package org.wangfuyuan.sgs.gui.main;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.data.constant.Const_UI;
import org.wangfuyuan.sgs.data.enums.Identity;
import org.wangfuyuan.sgs.util.ImgUtil;


/**
 * 显示(选择)玩家身份的面板
 * @author user
 *
 */
public class Panel_Id extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -474307825678140295L;
	//关联的玩家面板
	Panel_Player panel ;
	//显示版块
	JLabel jl_show = new JLabel(new ImageIcon(ImgUtil.getPngImgByName("id_null")));
	//忠臣反贼内奸
	JLabel jl_Zhong =new JLabel(new ImageIcon(ImgUtil.getPngImgByName("id_zhong")));
	JLabel jl_Fan = new JLabel(new ImageIcon(ImgUtil.getPngImgByName("id_fan")));
	JLabel jl_Nei = new JLabel(new ImageIcon(ImgUtil.getPngImgByName("id_nei")));
	//开关
	boolean isOpen;
	
	public Panel_Id(Panel_Player pp){
		this.panel = pp;
		this.setOpaque(false);
		this.setLayout(new GridLayout(4, 1));
		this.setSize(40, Const_UI.PLAYER_PANEL_HEIGHT);
		add(jl_show);
		jl_show.addMouseListener(new clickOpen());
		//this.open();
	}
	//打开
	public void open(){
		add(jl_Zhong);
		add(jl_Fan);
		add(jl_Nei);
		validate();
		repaint();
		addListener();
		isOpen = true;
	}
	//收缩
	public void close(){
		remove(jl_Zhong);
		remove(jl_Fan);
		remove(jl_Nei);
		repaint();
		validate();
		isOpen = false;
	}  
	//注册监听
	public void addListener(){
		jl_Zhong.addMouseListener(new clickSelect(jl_Zhong));
		jl_Fan.addMouseListener(new clickSelect(jl_Fan));
		jl_Nei.addMouseListener(new clickSelect(jl_Nei));
	}
	
	/*
	 * 点击更换身份监听
	 */
	class clickSelect extends MouseAdapter{
		JLabel jl ;
		public clickSelect(JLabel jl){
			this.jl = jl;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			//super.mouseClicked(e);
			close();
			jl_show.setIcon(jl.getIcon());
		}
	}
	
	/**
	 * 死亡后显示
	 */
	public void showAfterDead(){
		Identity id = panel.getPlayer().getState().getId();
		switch(id){
		case FANZEI:
			jl_show.setIcon(jl_Fan.getIcon());
			break;
		case NEIJIAN:
			jl_show.setIcon(jl_Nei.getIcon());
			break;
		case ZHONGCHEN:
			jl_show.setIcon(jl_Zhong.getIcon());
			break;
		}
	}
	
	/*
	 * 点击打开监听
	 */
	class clickOpen extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			jl_show.setIcon( new ImageIcon(ImgUtil.getPngImgByName("id_null")));
			if(!isOpen){
				open();
			}else{
				close();
			}
		}
	}
}
