/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: NetPackage.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.entities 
 * @Description: TODO
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:59:15 
 * @version: V1.0   
 */
package edu.whhp.entities;

import edu.whhp.abstracts.ServicePackage;
import edu.whhp.interfaces.NetService;

/**
 * @ClassName: NetPackage
 * @Description:网虫套餐
 * @author: 桂鹏:739312906@qq.com
 * @date: 2017年4月7日 下午9:59:15
 */
public class NetPackage extends ServicePackage implements NetService {
	private int flow; // 上网流量

	public NetPackage() {
		super.setPrice(68);
		this.flow = 3*1024;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	@Override
	public int netPlay(int minCount, MobileCard card) throws Exception{
		int temp = minCount;
		for (int i = 0; i < minCount; i++) {
			if (this.flow - card.getRealFlow() >= 0) {
				// 第一种情况：套餐剩余流量大于0
				card.setRealFlow(card.getRealFlow() + 1);// 实际流量加1
			} else if (card.getMoney() >= 0.1) {
				// 第二种情况：套餐流量已用完，账户余额还可以支付1MB流量
				card.setRealFlow(card.getRealFlow() + 1);// 实际流量加1
				// 账户余额消费0.1元/条
				card.setMoney(Common.sub(card.getMoney(), 0.1));
				card.setConsumAmount(card.getConsumAmount() + 0.1);
			} else {
				temp = i; // 记录实际流量
				throw new Exception("本次已使用流量" + i + "MB，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}

	@Override
	public void showInfo() {
		System.out.println("网虫套餐:上网流量为"+this.flow/1024+"GB/月。");
	}
}
