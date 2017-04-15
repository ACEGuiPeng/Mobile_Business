/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: SuperPackage.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.entities 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月7日 下午10:02:56 
 * @version: V1.0   
 */
package edu.whhp.entities;

import edu.whhp.abstracts.ServicePackage;
import edu.whhp.interfaces.CallService;
import edu.whhp.interfaces.NetService;
import edu.whhp.interfaces.SendService;

/**
 * @ClassName: SuperPackage
 * @Description: 超人套餐
 * @author: 桂鹏:739312906@qq.com
 * @date: 2017年4月7日 下午10:02:56
 */
public class SuperPackage extends ServicePackage implements CallService, SendService, NetService {
	private int talkTime;// 通话时长
	private int smsCount; // 短信条数
	private int flow; // 上网流量

	public SuperPackage() {
		super.setPrice(78);
		this.talkTime = 200;
		this.flow = 1 * 1024;
		this.smsCount = 50;
	}

	public int getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	@Override
	public int netPlay(int minCount, MobileCard card) throws Exception {
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
	public int send(int mincount, MobileCard card) throws Exception {
		int temp = mincount;
		for (int i = 0; i < mincount; i++) {
			if (this.smsCount - card.getRealSMSCount() >= 0) {
				// 第一种情况：套餐剩余短信数大于0
				card.setRealSMSCount(card.getRealSMSCount() + 1);// 实际短信数据加1
			} else if (card.getMoney() >= 0.1) {
				// 第二种情况：套餐短信数已用完，账户余额还可以支付一条短信
				card.setRealSMSCount(card.getRealSMSCount() + 1);// 实际短信数据加1
				// 账户余额消费0.1元/条
				card.setMoney(Common.sub(card.getMoney(), 0.1));
				card.setConsumAmount(card.getConsumAmount() + 0.1);
			} else {
				temp = i; // 记录实际短信次数
				throw new Exception("本次已发短信" + i + "条，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}

	@Override
	public int call(int minCount, MobileCard card) throws Exception {
		int temp = minCount;
		for (int i = 0; i < minCount; i++) {
			if (this.talkTime - card.getRealTalkTime() >= 1) {
				// 第一种情况：套餐剩余通话时长可以支持一分钟通话
				card.setRealTalkTime(card.getRealTalkTime() + 1);// 实际通话数据加1
			} else if (card.getMoney() >= 0.2) {
				// 第二种情况：套餐通话时长已用完，账户余额可以支付1分钟通话，使用账户余额支付
				card.setRealTalkTime(card.getRealTalkTime() + 1);// 实际通话时长1分钟
				// 账户余额消费0.2元（1分钟 额外通话）
				card.setMoney(Common.sub(card.getMoney(), 0.2));
				card.setConsumAmount(card.getConsumAmount() + 0.2);
			} else {
				temp = i;// 记录实际通话分钟数
				throw new Exception("本次已通话" + i + "分钟，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}

	@Override
	public void showInfo() {
		System.out.println("超人套餐：通话时长为" + this.talkTime + "分钟/月，短信条数为" + this.smsCount + "条/月，" + "上网流量为"
				+ this.flow / 1024 + "GB/月。");
	}

}
