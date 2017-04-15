/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: SendService.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.interfaces 
 * @Description: TODO
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:44:35 
 * @version: V1.0   
 */
package edu.whhp.interfaces;

import edu.whhp.entities.MobileCard;

/** 
 * @ClassName: SendService 
 * @Description: 发送短信服务接口
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:44:35  
 */
public interface SendService {
	int send(int count,MobileCard card) throws Exception;
}
