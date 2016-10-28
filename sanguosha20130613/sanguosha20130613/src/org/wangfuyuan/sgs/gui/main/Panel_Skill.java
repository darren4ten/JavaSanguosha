package org.wangfuyuan.sgs.gui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.wangfuyuan.sgs.data.constant.Const_Game;
import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
import org.wangfuyuan.sgs.skills.SkillIF;
import org.wangfuyuan.sgs.skills.SkillMultiIF;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 技能栏面板 包含了主动技能和被动技能的按钮 默认先加载主动技能，再加载被动
 * 
 * @author user
 * 
 */
public class Panel_Skill extends JPanel implements RefreshbleIF {
	private static final long serialVersionUID = 2040720083702356144L;
	AbstractPlayer p;
	Skill s1;
	Skill s2;

	public Panel_Skill(AbstractPlayer p) {
		this.p = p;
		this.setSize(170, 40);
		this.setLayout(new GridLayout(0, 3));
		this.setOpaque(false);
		this.setLocation(20, 150);
		loadSkills();
	}

	// 装载技能
	private void loadSkills() {
		//主动技能
		List<SkillIF> skills = p.getState().getSkill();
		if (!skills.isEmpty()) {
			for (int i = 0; i < skills.size(); i++) {
				add(new Skill(p.getState().getSkill().get(i)));
			}
		}
		// 被动技能
		List<LockingSkillIF> lSkills = p.getState().getLockingSkill();
		if (!lSkills.isEmpty()) {
			for (int i = 0; i < lSkills.size(); i++) {
				LockingSkillIF ls = lSkills.get(i);
				if (ls instanceof SkillMultiIF) {
					SkillMultiIF sm = (SkillMultiIF) ls;
					for (int j = 0; j < sm.getNameList().size(); j++) {
						add(new Skill(sm.getNameList().get(j), 0));
					}
				} else {
					s2 = new Skill(ls.getName(), 0);
					add(s2);
				}
			}
		}
	}

	/**
	 * 刷新
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	/**
	 * 内部类 技能的按钮
	 */
	class Skill extends JPanel {
		private static final long serialVersionUID = -5187604468743452501L;

		JLabel text = new JLabel();
		String name;

		Image imgEnable = ImgUtil.getPngImgByName("bok");
		Image imgUnable = ImgUtil.getPngImgByName("bok2");
		Image imgDown = ImgUtil.getPngImgByName("bend");
		Image curImg;
		MouseListener listener = new skillListener();
		SkillIF skill ;
		// 构造器
		public Skill(SkillIF skill) {
			this.skill = skill;
			this.name = skill.getName();
			createUI();
			// 初始不可用
			this.enableToUse();
		}

		// 重载，构造锁定技面板
		public Skill(String name, int type) {
			this.name = name;
			createUI();
			curImg = imgUnable;
		}

		private void createUI() {
			this.setSize(100, 50);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			curImg = imgEnable;
			Font f = new Font("楷体", Font.BOLD, 22);
			text.setFont(f);
			text.setForeground(Color.white);
			text.setText(name);
			this.add(text);
		}

		/**
		 * 禁用按钮
		 */
		public void unableToClick() {
			this.curImg = imgUnable;
			this.removeMouseListener(listener);
			this.setCursor(Cursor.getDefaultCursor());
			repaint();
		}

		/**
		 * 启用按钮
		 */
		public void enableToUse() {
			this.curImg = imgEnable;
			if (this.getMouseListeners().length == 0)
				this.addMouseListener(listener);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			repaint();
		}

		/**
		 * 绘制
		 */
		public void paint(Graphics g) {
			g.drawImage(curImg, 0, 0, this.getWidth(), this.getHeight(), null);
			super.paintChildren(g);
			g.dispose();
		}

		/**
		 * 鼠标监听类
		 * 
		 * @author user
		 * 
		 */
		class skillListener extends MouseAdapter {

			@Override
			public void mousePressed(MouseEvent e) {
				curImg = imgDown;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				curImg = imgEnable;
				repaint();
			}
			
			/**
			 * 技能发动的原理：
			 * 主动技能一般都是一个runnable
			 * 在被动响应阶段，直接new一个thread运行技能
			 * 在出牌阶段，需要通过信号来告诉process类当前做什么
			 * 因此先将信号设置为skill，process将锁住，又因为process中接受到skill的信号后
			 * 是直接调用技能列表中的第一个，因此在点击事件触发时，将当前代表的skill移到列表第一个去
			 * 
			 * 【注】
			 * 这个设计只不过是基于当前架构下的一个选择，未必是最优方案
			 * 暂且如此，希望能抛砖引玉
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// 发动技能
				p.getState().getSkill().remove(skill);
				p.getState().getSkill().add(0, skill);
				if (p.getState().isRequest()) {
					Thread t = new Thread((Runnable) p.getState().getSkill().get(0));
					t.start();
				} else {
					p.getState().setRes(Const_Game.SKILL);
				}
			}
		}
	}
}
