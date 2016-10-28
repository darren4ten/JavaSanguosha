package org.wangfuyuan.sgs.card;

import java.lang.reflect.Constructor;
import java.util.Properties;

import org.wangfuyuan.sgs.card.equipment.AbstractEquipmentCard;
import org.wangfuyuan.sgs.data.enums.Colors;
import org.wangfuyuan.sgs.data.enums.EquipmentType;
import org.wangfuyuan.sgs.util.ConfigFileReadUtil;
import org.wangfuyuan.sgs.util.ImgUtil;

/**
 * 牌的工厂类
 * 
 * @author user
 * 
 */
public class CardFactory {

	/**
	 * 对外输出一张牌 参数为文件中读取来的一个键值对 key = id value = 包含花色数值类型的字符串
	 * value解析成3个参数，调用加工方法产生1张牌
	 */
	public static AbstractCard newCard(String key, String value) {
		int id = Integer.parseInt(key);
		String[] str = value.split(",");
		int color = Integer.parseInt(str[0]);
		Colors c = null;
		switch (color) {
		case 1:
			c = Colors.HEITAO;
			break;
		case 2:
			c = Colors.HONGXIN;
			break;
		case 3:
			c = Colors.FANGKUAI;
			break;
		case 4:
			c = Colors.MEIHUA;
			break;
		default:
			c = Colors.HEITAO;
			break;
		}
		int num = Integer.parseInt(str[1]);
		int type = Integer.parseInt(str[2]);
		AbstractCard res = newCard(type);
		res.setId(id);
		res.setNumber(num);
		res.setColor(c);
		res.setColorImg(ImgUtil.getColorImg(c));
		//if(res instanceof AbstractEquipmentCard)System.out.println(res.toString());
		return res;
	}
	/*
	 * 创建一张牌 内部加工
	 * 【2013年6月11日开始 弃用】
	 * 【今后由XML控制生成】
	 * @return
	 */
	/*private static AbstractCard createCard(int type, int id, int number,
			Colors color) {
		switch (type) {
		case Const_Game.SHA:
			return new Card_Sha(id, number, color);
		case Const_Game.SHAN:
			return new Card_Shan(id, number, color);
		case Const_Game.TAO:
			return new Card_Tao(id, number, color);
		case Const_Game.WANJIANQIFA:
			return new Card_WanJianQiFa(id, number, color);
		case Const_Game.QINGGANGJIAN:
			return new Card_QingGangJian(id, number, color);
		case Const_Game.BAGUAZHEN:
			return new Card_BaGuaZhen(id, number, color);
		case Const_Game.JUEDOU:
			return new Card_JueDou(id, number, color);
		case Const_Game.GUOHECHAIQIAO:
			return new Card_GuoHeChaiQiao(id, number, color);
		case Const_Game.WUZHONGSHENGYOU:
			return new Card_WuZhongShengYou(id, number, color);
		case Const_Game.SHUNSHOUQIANYANG:
			return new Card_ShunShouQianYang(id, number, color);
		case Const_Game.TAOYUANJIEYI:
			return new Card_TaoYuanJieYi(id, number, color);
		case Const_Game.WUGUFENGDENG:
			return new Card_WuGuFengDeng(id, number, color);
		case Const_Game.GUANSHIFU:
			return new Card_GuanShiFu(id, number, color);
		case Const_Game.CIXIONGSHUANGGUJIAN:
			return new Card_CiXiongShuangGuJian(id, number, color);
		case Const_Game.NANMANRUQIN:
			return new Card_NanManRuQin(id, number, color);
		case Const_Game.ZHUGELIANNU:
			return new Card_ZhuGeLianNu(id, number, color);
		case Const_Game.LEBUSISHU:
			return new Card_LeBuSiShu(id, number, color);
		case Const_Game.SHANDIAN:
			return new Card_ShanDian(id, number, color);
		case Const_Game.JIEDAOSHAREN:
			return new Card_JieDaoShaRen(id, number, color);
		case Const_Game.FANGTIANHUAJI:
			return new Card_FangTianHuaJi(id, number, color);
		case Const_Game.QILINGONG:
			return new Card_QiLinGong(id, number, color);
		case Const_Game.QINGLONGYANYUEDAO:
			return new Card_QingLongYanYueDao(id, number, color);
		case Const_Game.ZHANGBASHEMAO:
			return new Card_ZhangBaSheMao(id, number, color);
		case Const_Game.DILU:
			return new Card_DefHorse(id, number, color);
		case Const_Game.DAWAN:
			return new Card_AttHorse(id, number, color);
		case Const_Game.WUXIEKEJI:
			return new Card_WuXieKeJi(id, number, color);
		}
		return null;
	}*/

	/**
	 * 根据type类型值
	 * 读取XML中的配置信息，利用反射生成一张牌的实例
	 */
	@SuppressWarnings("unchecked")
	public static AbstractCard newCard(int typeID){
		AbstractCard c = null;
		Object obj = null;
		Properties p = ConfigFileReadUtil.getCardInfoFromXML(typeID);
		String clazz = (String) p.get("class");
		String name = (String) p.get("name");
		int type = Integer.parseInt((String) p.get("type"));
		
		int targetType = Integer.parseInt((String) p.get("targetType"));
		boolean needRange = Boolean.parseBoolean((String) p.get("needRange"));
		String img = (String) p.get("img");
		String ef_img = (String) p.get("ef_img");
		//创建并赋值
		try {
			Constructor con = Class.forName(clazz).getConstructor();
			 obj= con.newInstance();
		} catch (Exception e){
			e.printStackTrace();
		}
		c = (AbstractCard)obj;
		//装备
		if(c instanceof AbstractEquipmentCard){
			int att = Integer.parseInt((String)p.get("att"));
			int def = Integer.parseInt((String)p.get("def"));
			EquipmentType eqType = Enum.valueOf(EquipmentType.class, (String)p.get("eqType"));
			AbstractEquipmentCard aec = (AbstractEquipmentCard) c;
			aec.setAttDistance(att);
			aec.setDefDistance(def);
			aec.setEquipmentType(eqType);
			aec.name = name;
			aec.type = type;
			aec.targetType = targetType;
			aec.needRange = needRange;
			if(img!=null && img.length()>0)aec.cardImg = ImgUtil.getPngImgByName(img);
			if(ef_img!=null && ef_img.length()!=0)aec.effectImage = ImgUtil.getPngImgByName(ef_img);
		//	System.out.println(aec.getName()+aec.getAttDistance()+aec.getTargetType());
			return aec;
		}
		c.name = name;
		c.type = type;
		c.targetType = targetType;
		c.needRange = needRange;
		if(img!=null && img.length()>0)c.cardImg = ImgUtil.getPngImgByName(img);
		if(ef_img!=null && ef_img.length()!=0)c.effectImage = ImgUtil.getPngImgByName(ef_img);
		return c;
	}
	
	/**
	 * 根据id 数值 花色 创建一张牌指定类型的牌
	 */
	public static AbstractCard newCard(int id,int num,Colors color,int typeID){
		AbstractCard c = newCard(typeID);
		c.setId(id);
		c.setNumber(num);
		c.setColor(color);
		return c;
	}
}
