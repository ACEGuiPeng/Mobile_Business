/**   
 * Copyright © 2017 guipeng. All rights reserved.
 * 
 * @Title: CallService.java 
 * @Prject: Mobile_Business
 * @Package: edu.whhp.interfaces 
 * @Description: TODO
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:42:06 
 * @version: V1.0   
 */
package edu.whhp.interfaces;

import edu.whhp.entities.MobileCard;

/** 
 * @ClassName: CallService 
 * @Description: 通话服务接口
 * @author: guipeng:739312906@qq.com 
 * @date: 2017年4月7日 下午9:42:06  
 */
public interface CallService {
	public int call(int minCount,MobileCard card) throws Exception;
}
