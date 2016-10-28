package org.wangfuyuan.sgs.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wangfuyuan.sgs.Main;
import org.wangfuyuan.sgs.data.enums.Country;
import org.wangfuyuan.sgs.gui.select.ProxyPlayer;
import org.wangfuyuan.sgs.player.impl.P_Info;

/**
 * 读取配置信息的类
 * 
 * @author user
 * 
 */

public class ConfigFileReadUtil {

	/**
	 * 从配置文件中读取指定人物的信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static P_Info getInfoFromXML(String playerName) {
		P_Info info = new P_Info();
		// 获取根节点
		Element root = getRoot("config/character.xml");
		// 获取根节点下的指定人物节点
		Element e = null;
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String code = elm.attribute("id").getText();
			if (code.equals(playerName)) {
				e = elm;
				break;
			}
		}
		
		// 获取人物节点下所有子节点
		String str_name = e.element("name").getText();
		String str_sex = e.element("sex").getText();
		String str_country = e.element("country").getText();
		String str_maxhp = e.element("maxhp").getText();
		String str_img = e.element("headimg").getText();
		String str_immuneCard = e.element("immuneCard").getText();
		// 将信息封装成一个info返回
		info = new P_Info();
		// 姓名
		info.setName(str_name);
		// 性别
		// info.setSex(Integer.parseInt(str_sex) == 1 ? true : false);
		info.setSex(Boolean.valueOf(str_sex));
		// 所属势力
		info.setCountry(Enum.valueOf(Country.class, str_country));
		// 生命上限
		info.setMaxHP(Integer.parseInt(str_maxhp));
		// 头像名称
		info.setHeadImg(ImgUtil.getJpgImgByName(str_img));
		// 免疫牌
		List<Integer> cards = new ArrayList<Integer>();
		String[] ss = str_immuneCard.split(",");
		for (int i = 0; i < ss.length; i++) {
			if(ss[i].isEmpty())break;
			int n = Integer.parseInt(ss[i]);
			cards.add(n);
		}

		info.setImmuneCard(cards);
		// 技能
		Element eSkill = e.element("skill");
		for (Iterator<Element> it = eSkill.elementIterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			if (elm.getName().equals("active")) {
				String str_skillname = elm.getText();
				if (str_skillname != null) {
					info.getSkillName().add(str_skillname);
				}
			}

		}
		/*
		 * if(eSkill.element("active")!=null){ String str_skillname =
		 * eSkill.element("active").getText();
		 * 
		 * }
		 */
		return info;
	}

	/**
	 * 【暂时废弃】
	 * 读取武将列表
	 */
	public static Properties getCharacterList() {
		Properties p = new Properties();
		try {
			String url = Main.class.getResource(
					"config/characterlist.properties").toString()
					.split(":/", 2)[1];
			File f = new File(url);
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			p.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 读取牌列表
	 */
	public static Properties getCardList() {
		Properties p = new Properties();
		try {
			p.load(ClassLoader.getSystemResourceAsStream("config"
					+ "/cardlist.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 读取测试时候额外添加的牌列表
	 */
	public static Properties getTestCardList(){

		Properties p = new Properties();
		try {
			p.load(ClassLoader.getSystemResourceAsStream("config"
					+ "/testlist.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	
	}
	/**
	 * 从XML中获取武将的技能列表
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getSkillListFromXML(String playerName) {
		List<String> skillList = new ArrayList<String>();
		Element root = getRoot("config/character.xml");
		
		// 获取根节点下的指定人物节点
		Element e = null;
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String code = elm.attribute("id").getText();
			if (code.equals(playerName)) {
				e = elm;
				break;
			}
		}
		Element skills = e.element("skill");
		if (skills != null) {
			for (Iterator<Element> it = skills.elementIterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				skillList.add(elm.getName() + "," + elm.getText());
			}
		}
		return skillList;
	}

	
	/**
	 * 从XML中获取所有代理对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ProxyPlayer> getProxyPlayersFromXML(){
		List<ProxyPlayer> ppList = new ArrayList<ProxyPlayer>();
		Element root = getRoot("config/character.xml");
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String id = elm.attribute("id").getText();
			Element img = elm.element("headimg");
			String imgName = img.getText();
			ppList.add(new ProxyPlayer(id, imgName));
		}	
		return ppList;
	}
	
	/**
	 * 从xml中读取牌的配置信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Properties getCardInfoFromXML(int id){
		Properties p = new Properties();
		Element root = getRoot("config/card.xml");
		//Element e =null;
		String clazz = null;
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element elm = (Element) it.next();
			String code = elm.attribute("id").getText();
			int codeInt = Integer.parseInt(code);
			//检测到匹配
			if (codeInt == id) {
				//class
				clazz = elm.attribute("class").getText();
				//name
				String name = elm.element("name").getText();
				String type = elm.element("type").getText();
				String targetType = elm.element("targetType").getText();
				String needRange = elm.element("needRange").getText();	
				String img = elm.element("img").getText();
				String ef_img = elm.element("ef_img").getText();
				p.put("class", clazz);
				p.put("name", name);
				p.put("type", type);
				p.put("targetType", targetType);
				p.put("needRange", needRange);
				p.put("img", img);
				p.put("ef_img", ef_img);
				//武器
				String cardType = elm.attribute("cardtype").getText();
				if(cardType.equals("equipment")){
					String att = elm.element("att").getText();
					String def = elm.element("def").getText();
					String eqType = elm.element("eqType").getText();
					p.put("att", att);
					p.put("def", def);
					p.put("eqType", eqType);
				}
				break;
			}
		}
		return p;
	}
	
	/*
	 * 内部通用方法：获取根节点
	 */
	private static Element getRoot(String fileName){
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					ClassLoader
							.getSystemResourceAsStream(fileName),
					"utf-8"));
			doc = reader.read(r);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		// 获取根节点
		Element root = doc.getRootElement();
		return root;
	}
}
