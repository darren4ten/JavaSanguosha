package org.wangfuyuan.sgs.data.enums;


/**
 * 花色枚举
 */
public enum Colors {
		HEITAO, HONGXIN, MEIHUA, FANGKUAI;
		//重写toString
		@Override
		public String toString() {
			switch (this) {
			case HEITAO:
				return "黑桃";
			case HONGXIN:
				return "红心";
			case MEIHUA:
				return "梅花";
			case FANGKUAI:
				return "方块";
			}
			return null;
		}
	}

