package org.wangfuyuan.sgs.player.impl;

import java.lang.reflect.Constructor;
import java.util.List;

import org.wangfuyuan.sgs.player.AbstractPlayer;
import org.wangfuyuan.sgs.player.Player_ActionIF;
import org.wangfuyuan.sgs.player.Player_FunctionIF;
import org.wangfuyuan.sgs.player.Player_TriggerIF;
import org.wangfuyuan.sgs.skills.LockingSkillIF;
import org.wangfuyuan.sgs.skills.SkillIF;
import org.wangfuyuan.sgs.util.ConfigFileReadUtil;


/**
 * ����������
 * 
 * @author user
 * 
 */
public class Player extends AbstractPlayer {
	/**
	 * ������ ����info ����һ����������
	 */
	public Player(String name) {
		// ��ȡ��Ϣ
		this.info = ConfigFileReadUtil.getInfoFromXML(name);
		// ��ʼ��
		initial();
		// ���뼼��
		loadSkills(name);
	}

	/**
	 * ���뼼��
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void loadSkills(String name) {
		// ��ȡ�����б�
		List<String> list = ConfigFileReadUtil.getSkillListFromXML(name);
		// ������������
		for (String s : list) {
			s=s.trim();
			String type = s.split(",")[0];
			String clazz = s.split(",")[1];
			try {
				Constructor con = Class.forName(clazz).getConstructor(
						AbstractPlayer.class);
				Object obj = con.newInstance(this);
				//����Ǵ�������
				if (type.equals("trigger")) {
					setTrigger((Player_TriggerIF) obj);
				}
				//����ǻغ���
				if(type.equals("process")){
					setProcess((P_Process) obj);
				}
				//����Ƕ�����
				if(type.equals("action")){
					setAction((Player_ActionIF) obj);
				}				
				//����Ǻ�����
				if(type.equals("function")){
					setFunction((Player_FunctionIF) obj);
				}
				if(obj instanceof LockingSkillIF){
					LockingSkillIF ls = (LockingSkillIF) con.newInstance(this);
					this.getState().getLockingSkill().add(ls);
					//this.info.lockingSkill = ls.getName();
				}
			} catch (Exception e) {
				System.out.println(getInfo().getName() + "�����������");
				e.printStackTrace();
			}
		}
		//������������
		for (int i = 0; i < getInfo().getSkillName().size(); i++) {			
			String strSkillName = this.getInfo().getSkillName().get(i);
			if(strSkillName!=null){
				try {
					Constructor con = Class.forName(strSkillName).getConstructor(AbstractPlayer.class);
					SkillIF skill = (SkillIF) con.newInstance(this);
					getState().getSkill().add(skill);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("�������������쳣");
				}
			}
		}
	}

}
