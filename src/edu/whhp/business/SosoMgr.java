/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: Business.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.business 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月8日 下午2:40:54 
 * @version: V1.0   
 */
package edu.whhp.business;

import java.util.Scanner;

import edu.whhp.abstracts.ServicePackage;
import edu.whhp.constractions.CardUtil;
import edu.whhp.entities.MobileCard;
import edu.whhp.entities.NetPackage;
import edu.whhp.entities.SuperPackage;
import edu.whhp.entities.TalkPackage;

/**
 * @ClassName: Business
 * @Description: 业务类
 * @author: 桂鹏:739312906@qq.com
 * @date: 2017年4月8日 下午2:40:54
 */
public class SosoMgr {
	private CardUtil cardU = new CardUtil();
	private Scanner input = new Scanner(System.in);
	private String number = ""; // 接收用户输入的手机号码
	boolean isRight = false; // 只有用户登录后才能使用其他功能

	public static void main(String[] args) {
		SosoMgr menu = new SosoMgr();
		menu.start();
	}

	/**
	 * @Title: start
	 * @Description: 显示主菜单
	 * @return: void
	 */
	public void start() {
		// 主菜单显示
		System.out.println("******************欢迎使用嗖嗖移动业务大厅******************");
		System.out.println("1.用户登录    2.用户注册    3.使用嗖嗖    4.话费充值    5.资费说明    6.退出系统");
		System.out.print("请选择:");
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			// 实现用户登录功能
			do {
				System.out.print("请输入手机卡号:");
				number = input.next();
				System.out.print("请输入密码:");
				String passWord = input.next();
				isRight = cardU.isExitCard(number, passWord);
				if (isRight) {
					break;
				} else {
					System.out.println("您输入的卡号或密码有误");
					start();
				}
			} while (!isRight);
			// 进入二级菜单
			this.cardMenu();
			break;
		case 2:
			// 实现用户注册功能
			this.registCard();
			start();
			break;
		case 3:
			// 使用嗖嗖
			cardU.iniScenes();
			if (number.equals("")) {
				System.out.println("您还未注册，请先注册再使用嗖嗖！");
			} else {
				cardU.userSoso(number);
			}
			start();
			break;
		case 4:
			// 话费充值
			System.out.print("请输入充值卡号：");
			String cardNum = input.next();
			System.out.print("请输入充值金额:");
			double charMoney = input.nextInt();
			while (charMoney < 50) {
				System.out.print("充值金额最少为50元，请重新输入充值金额:");
				charMoney = input.nextInt();
			}
			cardU.chargeMoney(cardNum, charMoney);
			start();
			break;
		case 5:
			// 资费说明
			cardU.showDescription();
			start();
			break;
		default:
			System.out.println("谢谢使用！");
			break;
		}
	}

	/**
	 * @Title: cardMenu
	 * @Description: 显示二级菜单
	 * @return: void
	 */
	public void cardMenu() {
		// 二级菜单显示
		System.out.println("*******嗖嗖移动用户菜单*******");
		System.out.println("1.本月账单查询\n2.套餐余量查询\n3.打印消费详单\n4.套餐变更\n5.办理退网\n");
		System.out.print("请选择(输入1~5选择功能，其他键返回上一级):");
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			System.out.println("*****本月账单查询*****");
			cardU.showAmountDetail(number);
			cardMenu();
			break;
		case 2:
			System.out.println("*****套餐余量查询*****");
			cardU.showReminDetail(number);
			cardMenu();
			break;
		case 3:
			System.out.println("*****消费详单查询*****");
			cardU.printConsumInfo(number);
			cardMenu();
			break;
		case 4:
			System.out.println("*****套餐变更*****");
			System.out.print("1.话唠套餐    2.网虫套餐    3.超人套餐    请选择(序号):");
			int changeNum = input.nextInt();
			cardU.changingPack(number, changeNum);
			cardMenu();
			break;
		case 5:
			System.out.println("*****办理退网*****");
			cardU.delCard(number);
			start();
			break;
		default:
			// 其他键返回主菜单
			start();
			break;
		}
	}

	/**
	 * @Title: registCard
	 * @Description: 用户注册流程
	 * @return: void
	 */
	public void registCard() {
		MobileCard card = new MobileCard();
		ServicePackage serPackage = null;
		// 输出可选择的卡号
		System.out.println("*****可选择的卡号*****");
		String[] numbers = cardU.getNewNumber(9);
		for (int i = 0; i < numbers.length; i++) {
			System.out.print((i + 1) + "." + numbers[i] + "\t");
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
		System.out.print("请选择卡号(输入1~9的序号):");
		// 根据选择确定卡号
		card.setCardNumber(numbers[input.nextInt()]);
		// 三种套餐
		System.out.print("1.话唠套餐    2.网虫套餐    3.超人套餐，请选择套餐(输入序号):");
		int choice1 = input.nextInt();
		switch (choice1) {
		case 1:
			serPackage = new TalkPackage();
			break;
		case 2:
			serPackage = new NetPackage();
			break;
		case 3:
			serPackage = new SuperPackage();
			break;
		}
		card.setSerPackage(serPackage);
		System.out.print("请输入姓名:");
		card.setUserName(input.next());
		System.out.print("请输入密码:");
		card.setPassWord(input.next());
		System.out.print("请输入预存话费金额:");
		card.setMoney(input.nextInt());
		while (card.getMoney() < serPackage.getPrice()) {
			System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值:");
			card.setMoney(input.nextInt());
		}
		cardU.addCard(card);
		this.number = card.getCardNumber();
		System.out.print("注册成功！");
		// 本月合计费用增加为月租费，账户余额扣除月租费
		card.setConsumAmount(serPackage.getPrice());
		card.setMoney(card.getMoney() - serPackage.getPrice());
		card.showMeg();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
