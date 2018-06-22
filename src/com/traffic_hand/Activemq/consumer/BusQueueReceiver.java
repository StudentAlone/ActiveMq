
package com.traffic_hand.Activemq.consumer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic_hand.Activemq.bus.busstask.BusRemindTask;
import com.traffic_hand.Activemq.bus.busstask.BusRemindUtilsTask;
import com.traffic_hand.Activemq.taskManger.QuartzManager;
import com.traffic_hand.util.LocalStringUtils;
import com.traffic_hand.vo.req.v1.bus.BusRemindReqVo;

/**
 * 
 * @author liang
 * @description 队列消息监听器
 * 
 */
@Component
public class BusQueueReceiver implements MessageListener {
	public static Logger log = Logger.getLogger("busRemind");
	public static Logger logger = Logger.getLogger(BusQueueReceiver.class);
	public static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	QuartzManager quartzManager;

	@Override
	public void onMessage(Message message) {
		try {
			log.info("receiveTime:"+new Date().toLocaleString());
			BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
			Map<String,Integer> RecoredMap = instance.getRecoredMap();
			List<String> cidList = instance.getCidList();
			Map<String, String> mapRemind = instance.getMapRemindTask();
			String messages = ((TextMessage) message).getText();
			BusRemindReqVo busRe = mapper.readValue(messages, BusRemindReqVo.class);
			System.out.println("QueueReceiver接收到消息:" + messages);
			// 开启一个线程处理业务逻辑
			String cid = busRe.getCid();
			// 移除旧数据再添加
			mapRemind.remove(cid);
			quartzManager.removeJob( cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
			quartzManager.addJob( cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid
			// 给15秒时间，读取第三方数据库
					, BusRemindTask.class, "0/5 * * * * ?", "cidMessKey", messages);
			// 记录接收的消息
			Map<String ,Object> receiveMap=new HashMap<>();
			receiveMap.put(busRe.getCid(), busRe);
			receiveMap.put("time", LocalStringUtils.getDate());
			instance.getAllReceive().add(receiveMap);
			
			log.info(messages);
			System.out.println("开启定时器:"+"job"+cid);
			cidList.add(cid);
			Integer temp=RecoredMap.get("allReceive")==null?0:RecoredMap.get("allReceive");
			RecoredMap.put("allReceive", temp+1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("接收消息异常");
		}
	}
}
