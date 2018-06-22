package com.traffic_hand.Activemq.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.impl.StdScheduler;

import com.traffic_hand.util.SpringContextUtil;

public class CommonListener implements ServletContextListener {

	public static final Logger logger = Logger.getLogger(CommonListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Initialized");
		System.out.println("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("contextDestroyed");
		StdScheduler bean = (StdScheduler) SpringContextUtil.getBean("schedulerFactoryBean");
		bean.shutdown();
	}

}
