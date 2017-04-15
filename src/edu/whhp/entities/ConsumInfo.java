/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: ConsumInfo.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.entities 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月7日 下午10:05:22 
 * @version: V1.0   
 */
package edu.whhp.entities;

/**
 * @ClassName: ConsumInfo
 * @Description: 消费信息
 * @author: 桂鹏:739312906@qq.com
 * @date: 2017年4月7日 下午10:05:22
 */
public class ConsumInfo {
	private String cardNumber; // 卡号
	private String type; // 类型
	private int consumData;// 消费数据
	
	public ConsumInfo(String cardNumber, String type, int consumData) {
		this.cardNumber = cardNumber;
		this.type = type;
		this.consumData = consumData;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getConsumData() {
		return consumData;
	}
	public void setConsumData(int consumData) {
		this.consumData = consumData;
	}
	
}
