package org.wangfuyuan.sgs.data.enums;
/**
 * 游戏结束的几种结果
 * @author user
 *
 */
public enum GameOver {
	FANZEI_WIN,
	ZHUGONG_WIN,
	NEIJIAN_WIN;
	
	public String getWinner(){
		switch(this){
		case FANZEI_WIN:
			return "反贼胜";
		case ZHUGONG_WIN:
			return "主公胜";
		case NEIJIAN_WIN:
			return "内奸胜";
		}
		return "";
	}
	
	public String getWords(){
		switch(this){
		case FANZEI_WIN:
			return "苍天已死，黄天当立";
		case ZHUGONG_WIN:
			return "这江山，始终是我的";
		case NEIJIAN_WIN:
			return "天下大势，为我所控";
		}
		return "";
	}
}
