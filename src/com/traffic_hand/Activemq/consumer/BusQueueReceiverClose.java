
package com.traffic_hand.Activemq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.traffic_hand.Activemq.taskManger.QuartzManager;

/**
 * 
 * @author liang
 * @description 队列消息监听器
 * 
 */
@Component
public class BusQueueReceiverClose implements MessageListener {
	@Autowired
	QuartzManager quartzManager;
	
	@Override
	public void onMessage(Message message) {
		try {
			String cid = ((TextMessage) message).getText();
			System.out.println("QueueClose接收到消息:" + cid);
			quartzManager.removeJob("job" + cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
