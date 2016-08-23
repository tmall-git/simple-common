package com.simple.common.util;

import java.math.BigDecimal;

public class DoubleUtil {
	/**
	 * 价格，截取两位小数，不四舍五入
	 * @param value
	 * @return
	 */
	public static double formatPrice(double value) {
		BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 保留两位小数，四舍五入
	 * @param value
	 * @return
	 */
	public static double formatDouble(double value) {
		BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(formatDouble(0.01));
	}
}
