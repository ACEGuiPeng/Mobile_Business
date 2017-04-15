/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: Scene.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.entities 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月7日 下午10:07:54 
 * @version: V1.0   
 */
package edu.whhp.entities;

/** 
 * @ClassName: Scene 
 * @Description: 场景类
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月7日 下午10:07:54  
 */
public class Scene {
	private String type;//场景类型
	private int data; //场景消费数据
	private String description; //场景描述
	
	public Scene(String type, String description,int data) {
		super();
		this.type = type;
		this.description = description;
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
