/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: CardUtil.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.constractions 
 * @Description: TODO
 * @author: 桂鹏:739312906@qq.com 
 * @date: 2017年4月7日 下午10:14:05 
 * @version: V1.0   
 */
package edu.whhp.constractions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.whhp.abstracts.ServicePackage;
import edu.whhp.entities.Common;
import edu.whhp.entities.ConsumInfo;
import edu.whhp.entities.MobileCard;
import edu.whhp.entities.NetPackage;
import edu.whhp.entities.Scene;
import edu.whhp.entities.SuperPackage;
import edu.whhp.entities.TalkPackage;
import edu.whhp.interfaces.CallService;
import edu.whhp.interfaces.NetService;
import edu.whhp.interfaces.SendService;

/**
 * @ClassName: CardUtil
 * @Description: 工具类
 * @author: 桂鹏:739312906@qq.com
 * @date: 2017年4月7日 下午10:14:05
 */
public class CardUtil {
	Map<String, MobileCard> cards = new HashMap<>(); // 已注册嗖嗖移动用户列表，键为卡号，值为卡对象
	Map<String, List<ConsumInfo>> consumInfos = new HashMap<>();// 键为卡号，值为
																// 所有消费记录集合
	List<Scene> scenes = new ArrayList<>();// 储存场景
	List<ConsumInfo> infos = new ArrayList<>();// 储存消费记录

	/**
	 * @Title: iniScene
	 * @Description: 初始化六个场景
	 * @return: void
	 */
	public void iniScenes() {
		Scene scene1 = new Scene("通话", "问候客户，谁知其如此难缠，通话90分钟", 90);
		Scene scene2 = new Scene("通话", "询问妈妈身体状况，本地通话30分钟", 30);
		Scene scene3 = new Scene("短信", "参与环境保护实施方案问卷调查，发送短信5条", 5);
		Scene scene4 = new Scene("短信", "通知朋友手机换号，发送短信50条", 50);
		Scene scene5 = new Scene("上网", "和女友用微信视频聊天，使用流量1GB", 1 * 1024);
		Scene scene6 = new Scene("上网", "晚上手机在线看韩剧，不留神睡着了！使用流量2GB", 2 * 1024);
		scenes.add(0, scene1);
		scenes.add(1, scene2);
		scenes.add(2, scene3);
		scenes.add(3, scene4);
		scenes.add(4, scene5);
		scenes.add(5, scene6);

	}

	/**
	 * @Title: isExitCard
	 * @Description: 根据卡号和密码验证该卡是否注册
	 * @return: void
	 */
	public boolean isExitCard(String number, String password) {
		Set<String> numbers = cards.keySet(); // 得到存储卡号集合的键集合
		// 遍历键集合
		for (String searchNum : numbers) {
			if (searchNum.equals(number) && (cards.get(searchNum).getPassWord().equals(password))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Title: creatNumber
	 * @Description: 生成随机卡号
	 * @return: String
	 */
	public String creatNumber() {
		// 记录现有用户中是否存在此卡号用户 是：true 否： false
		boolean isExist = false;
		String number = "";
		int temp = 0;
		do {
			isExist = false; // 标志位重置为false，用于控制外重循环
			// 生成的随机数是8位，不能小于10000000,否则重新生成
			do {
				temp = (int) (Math.random() * 100000000);
			} while (temp < 10000000);
			// 生成之后，前面加"139"
			number = "139" + temp;
			// 和现有用户的卡号比较，不能是重复的
			Set<String> cardNumbers = cards.keySet();
			for (String cardNumber : cardNumbers) {
				if (number.equals(cardNumber)) {
					isExist = true;
					break;
				}
			}
		} while (isExist);
		return number;
	}

	/**
	 * @Title: getNewNumber
	 * @Description: 生成指定个数的卡号列表
	 * @return: String
	 */
	public String[] getNewNumber(int count) {
		String[] chooseNum = new String[count];// 储存9个随机生成的电话号码
		for (int i = 0; i < chooseNum.length; i++) {
			chooseNum[i] = this.creatNumber();
		}
		return chooseNum;
	}

	/**
	 * @Title: addCard
	 * @Description: 注册新卡
	 * @return: void
	 */
	public void addCard(MobileCard card) {
		cards.put(card.getCardNumber(), card);
	}

	/**
	 * @Title: delCard
	 * @Description: 办理退网
	 * @return: void
	 */
	public void delCard(String searchNumber) {
		Set<String> cardNumbers = cards.keySet();
		for (String cardNumber : cardNumbers) {
			if (cardNumber.equals(searchNumber)) {
				cards.remove(searchNumber);
				System.out.println("卡号" + searchNumber + "办理退网成功！\n谢谢使用！");
			}
		}
	}

	/**
	 * @Title: showReminDetail
	 * @Description: 套餐余量查询
	 * @return: void
	 */
	public void showReminDetail(String serchNumber) {
		MobileCard card;// 要查询的卡
		int remainTalkTime; // 剩余通话时间
		int remainSmsCount;// 剩余信息条数
		int remainFlow;// 剩余流量
		StringBuffer meg = new StringBuffer();//提示的信息
		
		card = cards.get(serchNumber);
		meg.append("您的卡号是" + serchNumber + ",套餐内剩余：\n");
		ServicePackage pack = card.getSerPackage();
		if (pack instanceof TalkPackage) {
			// 向下转型为话唠套餐对象
			TalkPackage cardPack = (TalkPackage) pack;
			// 话唠套餐，查询套餐内剩余的通话时长和短信条数
			remainTalkTime = cardPack.getTalkTime() > card.getRealTalkTime()
					? cardPack.getTalkTime() - card.getRealTalkTime() : 0;
			meg.append("通话时长：" + remainTalkTime + "分钟\n");
			remainSmsCount = cardPack.getSmsCount() > card.getRealSMSCount()
					? cardPack.getSmsCount() - card.getRealSMSCount() : 0;
			meg.append("短信条数：" + remainSmsCount + "条");
		} else if (pack instanceof NetPackage) {
			// 向下转型为网虫套餐
			NetPackage cardPack = (NetPackage) pack;
			// 网虫套餐，查询套餐内剩余的网络流浪
			remainFlow = cardPack.getFlow() > card.getRealFlow() ? cardPack.getFlow() - card.getRealFlow() : 0;
			meg.append("上网流量：" + Common.dataFormat(remainFlow * 1.0 / 1024) + "GB");
		} else if (pack instanceof SuperPackage) {
			// 向下转型为超人套餐
			SuperPackage cardPack = (SuperPackage) pack;
			// 超人套餐，查询套餐内剩余的通话时长，网络流量，短信条数
			remainTalkTime = cardPack.getTalkTime() > card.getRealTalkTime()
					? cardPack.getTalkTime() - card.getRealTalkTime() : 0;
			meg.append("通话时长：" + remainTalkTime + "分钟\n");
			remainFlow = cardPack.getFlow() > card.getRealFlow() ? cardPack.getFlow() - card.getRealFlow() : 0;
			meg.append("上网流量：" + Common.dataFormat(remainFlow * 1.0 / 1024) + "GB\n");
			remainSmsCount = cardPack.getSmsCount() > card.getRealSMSCount()
					? cardPack.getSmsCount() - card.getRealSMSCount() : 0;
			meg.append("短信条数：" + remainSmsCount + "条");
		}
		System.out.println(meg);
	}

	/**
	 * @Title: showAmountDetail
	 * @Description: 本月账单查询
	 * @return: void
	 */
	public void showAmountDetail(String searchNumber) {
		MobileCard card; // 要查询的卡
		StringBuffer meg = new StringBuffer();
		card = cards.get(searchNumber);// 查找到要查询的卡
		meg.append("您的卡号：" + card.getCardNumber() + ",当月账单:\n");
		meg.append("套餐资费：" + card.getSerPackage().getPrice() + "元\n");
		meg.append("合计：" + Common.dataFormat(card.getConsumAmount()) + "元\n");
		meg.append("账户余额：" + Common.dataFormat(card.getMoney()) + "元");
		// 显示本月消费详细信息
		System.out.println(meg);
	}

	/**
	 * @Title: addConsumInfo
	 * @Description: 添加指定卡号的消费记录
	 * @return: void
	 */
	public void addConsumInfo(String number, ConsumInfo info) {
		infos.add(info);
		consumInfos.put(number, infos);
	}

	/**
	 * @Title: userSoso
	 * @Description: 使用嗖嗖
	 * @return: void
	 */
	public void userSoso(String number) {
		MobileCard card = cards.get(number); // 获取此卡对象
		ServicePackage pack = card.getSerPackage(); // 获取此卡所属套餐
		Random random = new Random();
		int ranNum = 0;
		int temp = 0; // 记录各场景中的实际消费数据
		do {
			ranNum = random.nextInt(6);
			Scene scene = scenes.get(ranNum);
			switch (ranNum) {
			case 0:
			case 1:
				// 序号为0或1为通话场景
				// 判断该卡所属套餐是否支持通话功能
				if (pack instanceof CallService) {
					// 执行通话方法
					System.out.println(scene.getDescription());
					CallService callSer = (CallService) pack;
					try {
						temp = callSer.call(scene.getData(), card);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					// 添加一条消费记录,更新数据
					addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
					break;
				} else {
					// 如果该卡套餐不支持通话功能，则重新生成随机数选择其他场景
					continue;
				}
			case 2:
			case 3:
				// 序号为2或3为发短信场景
				// 判断该卡所属套餐是否支持发短信功能
				if (pack instanceof SendService) {
					// 执行发短信功能
					System.out.println(scene.getDescription());
					SendService sendSer = (SendService) pack;
					try {
						temp = sendSer.send(scene.getData(), card);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					// 添加一条消费记录
					addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
					break;
				} else {
					continue;
				}
			case 4:
			case 5:
				// 序号为4或5为上网场景
				// 判断该卡所属套餐是否支持发上网功能
				if (pack instanceof NetService) {
					// 执行发短信功能
					System.out.println(scene.getDescription());
					NetService netSer = (NetService) pack;
					try {
						temp = netSer.netPlay(scene.getData(), card);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					// 添加一条消费记录
					addConsumInfo(number, new ConsumInfo(number, scene.getType(), temp));
					break;
				} else {
					continue;
				}
			}
			break;
		} while (true);
	}

	/**
	 * @Title: showDescription
	 * @Description: 资费说明
	 * @return: void
	 */
	public void showDescription() {
		Reader fileReader = null;
		BufferedReader br = null;
		StringBuffer sbf = new StringBuffer("");
		String brs = null;
		try {
			fileReader = new FileReader("src\\edu\\whhp\\constractions\\套餐资费说明.txt");
			br = new BufferedReader(fileReader);
			// 读取一行数据
			brs = br.readLine();
			while (brs != null) {
				sbf.append(brs + "\n");
				brs = br.readLine();
			}
			System.out.println(sbf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: changingPack
	 * @Description: 套餐变更
	 * @return: void
	 */
	public void changingPack(String number, int packNum) {
		MobileCard card = cards.get(number); // 得到当前卡对象
		switch (packNum) {
		case 1:
			// 判断是不是已经开通话唠套餐
			if (card.getSerPackage() instanceof TalkPackage) {
				System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
			} else {
				// 判断余额是否足够支付新套餐的月租费用
				if (card.getMoney() < new TalkPackage().getPrice()) {
					System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
				} else {
					// 改变套餐
					card.setSerPackage(new TalkPackage());
					System.out.println("更换套餐成功！");
					card.getSerPackage().showInfo();
					// 实际使用数据清零
					card.setRealTalkTime(0);
					card.setRealFlow(0);
					card.setRealSMSCount(0);
					// 当前卡余额减去月租费
					card.setMoney(card.getMoney() - card.getSerPackage().getPrice());
					// 本月消费金额修改为新套餐月资费
					card.setConsumAmount(card.getConsumAmount() + card.getSerPackage().getPrice());
				}
			}
			break;
		case 2:
			// 判断是不是已经开通网虫套餐
			if (card.getSerPackage() instanceof NetPackage) {
				System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
			} else {
				// 判断余额是否足够支付新套餐的月租费用
				if (card.getMoney() < new NetPackage().getPrice()) {
					System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
				} else {
					// 改变套餐
					card.setSerPackage(new NetPackage());
					System.out.println("更换套餐成功！");
					card.getSerPackage().showInfo();
					// 实际使用数据清零
					card.setRealTalkTime(0);
					card.setRealFlow(0);
					card.setRealSMSCount(0);
					// 当前卡余额减去月租费
					card.setMoney(card.getMoney() - card.getSerPackage().getPrice());
					// 本月消费金额修改为新套餐月资费
					card.setConsumAmount(card.getConsumAmount() + card.getSerPackage().getPrice());
				}
			}
			break;
		case 3:
			// 判断是不是已经开通超人套餐
			if (card.getSerPackage() instanceof SuperPackage) {
				System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
			} else {
				// 判断余额是否足够支付新套餐的月租费用
				if (card.getMoney() < new SuperPackage().getPrice()) {
					System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
				} else {
					// 改变套餐
					card.setSerPackage(new SuperPackage());
					System.out.println("更换套餐成功！");
					card.getSerPackage().showInfo();
					// 实际使用数据清零
					card.setRealTalkTime(0);
					card.setRealFlow(0);
					card.setRealSMSCount(0);
					// 当前卡余额减去月租费
					card.setMoney(card.getMoney() - card.getSerPackage().getPrice());
					// 本月消费金额修改为新套餐月资费
					card.setConsumAmount(card.getConsumAmount() + card.getSerPackage().getPrice());
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @Title: printConsumInfo
	 * @Description: 打印消费详单
	 * @return: void
	 */
	public void printConsumInfo(String number) {
		Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(number + "消费记录.txt");// 创建一个txt文件夹
			Set<String> numbers = consumInfos.keySet();
			// 存储指定卡的所有消费记录
			// 现有消费列表中是否存在此卡号消费记录，是：true，否：false
			boolean isExit = false;
			// 从consumInfos中查找是否存在该卡消费记录的代码
			isExit = consumInfos.containsKey(number);
			if (isExit) {
				StringBuffer content = new StringBuffer("*****" + number + "消费记录*****\n");
				content.append("序号\t类型\t数据(通话(分钟))/上网(MB)/短信(条))\n");
				for (int i = 0; i < infos.size(); i++) {
					ConsumInfo info = infos.get(i);
					content.append((i + 1) + ".\t\t" + info.getType() + "\t\t" + info.getConsumData() + "\n");
				}
				fileWriter.write(content.toString());
				fileWriter.flush();
				System.out.println("消费记录打印完毕！");
			} else {
				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭写出流
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: chargeMoney
	 * @Description:话费充值
	 * @return: void
	 */
	public void chargeMoney(String number, double newMoney) {
		MobileCard card = cards.get(number); // 获取当前卡对象
		if (card != null) {
			card.setMoney(card.getMoney() + newMoney);
			System.out.println("充值成功，当前话费余额为" + Common.dataFormat(card.getMoney()));
		} else {
			System.out.println("您输入的卡号有误，请重新充值！");
		}
	}
}
