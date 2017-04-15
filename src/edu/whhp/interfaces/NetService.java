/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: NetService.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.interfaces 
 * @Description: TODO
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:45:44 
 * @version: V1.0   
 */
package edu.whhp.interfaces;

import edu.whhp.entities.MobileCard;

/**
 * @ClassName: NetService
 * @Description: 上网服务接口
 * @author: guipeng:739312906@qq.com
 * @date: 2017年4月7日 下午9:45:44
 */
public interface NetService {
	int netPlay(int flow, MobileCard card) throws Exception;
}
