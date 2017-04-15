/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: Common.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.entities 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月8日 下午4:57:15 
 * @version: V1.0   
 */
package edu.whhp.entities;

import java.text.DecimalFormat;

/** 
 * @ClassName: Common 
 * @Description: 公共类
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月8日 下午4:57:15  
 */
public class Common {
	public static String dataFormat(double data){
		DecimalFormat formatData = new DecimalFormat("0.0");
		return formatData.format(data);
	}

	public static double sub(double money, double d) {
		return money-d;
	}
}
