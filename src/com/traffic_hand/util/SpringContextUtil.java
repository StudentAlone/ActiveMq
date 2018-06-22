package com.traffic_hand.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
* <p>Title:SpringContextUtil </p>
* <p>Description: 获得ApplicationContext中的bean</p>
* 
* @author LiuSP
* @date 2017年11月24日
 */

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//适用于在@Service("UserService")标注了bean的名字
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	//适用于在@Service后面什么也没有
	public static <T> Object getBean(Class<T> c) {
		return applicationContext.getBean(c);
	}
}
