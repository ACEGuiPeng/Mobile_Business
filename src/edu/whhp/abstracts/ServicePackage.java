/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: ServicePackage.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.abstracts 
 * @Description: TODO
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:39:32 
 * @version: V1.0   
 */
package edu.whhp.abstracts;

/**
 * @ClassName: ServicePackage
 * @Description: 品牌套餐类
 * @author: guipeng:739312906@qq.com
 * @date: 2017年4月7日 下午9:39:32
 */
public abstract class ServicePackage {
	private double price;


	public abstract void showInfo();


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
}
