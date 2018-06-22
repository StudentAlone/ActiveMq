package com.traffic_hand.Activemq.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic_hand.Activemq.bus.busstask.BusRemindUtilsTask;
import com.traffic_hand.Activemq.bus.busstask.BusRemindUtilsTaskWrap;
import com.traffic_hand.Activemq.producer.QueueSender;
import com.traffic_hand.Activemq.taskManger.QuartzManager;
import com.traffic_hand.domain.v1.bus.BusRemindEntity;
import com.traffic_hand.util.GtPushUtils;
import com.traffic_hand.util.LocalStringUtils;
import com.traffic_hand.vo.req.v1.bus.BusRemindReqVo;


/**
 * 
 * @author liang
 * @description controller测试
 */
@Controller
@RequestMapping("/activemq")
public class ActivemqController {

	@Resource
	QueueSender queueSender;
	@Autowired
	QuartzManager quartzManager;
	@Autowired
	SchedulerFactoryBean schedulerFactory;

	protected static ObjectMapper mapper = new ObjectMapper();
	public static Logger log = Logger.getLogger("busRemind");

	/**
	 * 发送消息到队列 Queue队列
	 * 
	 * @param parmInfo
	 * @return BusRemindEntity
	 */
	@ResponseBody
	@RequestMapping(value="getOnRemind",method = { RequestMethod.GET, RequestMethod.POST })
	public BusRemindEntity queueSender(BusRemindReqVo busRe,HttpServletRequest request) {
		String method = request.getMethod();
		BusRemindEntity res = BusRemindEntity.getBusRemindEntity(1, "success");
		try {
			BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
			Map<String, Integer> RecoredMap = instance.getRecoredMap();
			String sendMessage = busRe.getSendMessage();
			if (method.equals("GET")&&!StringUtils.isEmpty(sendMessage)) {
				new String(sendMessage.getBytes("ISO-8859-1"), "UTF-8");
				busRe.setSendMessage(sendMessage);
			}
			String message = mapper.writeValueAsString(busRe);

			// 上车提醒
			queueSender.send("getOnQueue", message);
			// 记录发送消息数
			Map<String ,Object> sendMap=new HashMap<>();
			sendMap.put(busRe.getCid(), busRe);
			sendMap.put("time", LocalStringUtils.getDate());
			instance.getAllSend().add(sendMap);
			
			log.info("sendTime:" + LocalStringUtils.getDate());
			Integer sendTemp = RecoredMap.get("allSend") == null ? 0 : RecoredMap.get("allSend");
			RecoredMap.put("allSend", sendTemp + 1);

		} catch (Exception e) {
			e.getMessage();
			res.setReturnCode(0);
			res.setReturnMsg("failure");
		}
		return res;
	}

	/**
	 * 直接关闭上车提醒
	 * 
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCloseRemind")
	public BusRemindEntity getCloseRemind(@RequestParam("cid") String cid) {
		BusRemindEntity res = BusRemindEntity.getBusRemindEntity(1, "success");
		try {
			quartzManager.removeJob( cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
			if (StringUtils.isEmpty(cid)) {
				res.setReturnCode(0);
				res.setReturnMsg("failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setReturnCode(0);
			res.setReturnMsg("failure");
		}
		return res;
	}

	/**
	 * 下车提醒推送
	 * 
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getOutRemind")
	public BusRemindEntity sendComponent(@RequestParam String cid, String sendType,String sendMessage) {
		BusRemindEntity res = BusRemindEntity.getBusRemindEntity(1, "success");
		try {
			GtPushUtils.sendMessage(cid, sendType, sendMessage);

		} catch (Exception e) {
			e.printStackTrace();
			res.setReturnCode(0);
			res.setReturnMsg("failure");
		}
		return res;
	}

	
	@ResponseBody
	@RequestMapping("/getRemindRecord")
	public BusRemindUtilsTaskWrap getRemindRecord() {
		BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
		BusRemindUtilsTaskWrap wrap = BusRemindUtilsTaskWrap.getInstance();
		// 方便下一次测试
		instance.setSpecificCid("");
		Map<String, Object> timerCountWrap = wrap.getTimerCountWrap();
		Map<String, String> timerCount = instance.getTimerCount();
		Map<String, Object> runningTaskWrap = wrap.getRunningTaskWrap();
		Map<String, Long> runningTask = instance.getRunningTask();
		
		//instance.getRecoredMap().put("RunningCount", instance.getCidList().size());
		Scheduler scheduler =schedulerFactory.getScheduler();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupStartsWith("jobGroup"));
			instance.setSchedulerCount(jobKeys.size());
			
			//wrap.setRecoredMap(instance.getRecoredMap());
			wrap.setAllReceive(instance.getAllReceive());
			wrap.setAllSend(instance.getAllSend());
			wrap.setSchedulerCount(instance.getSchedulerCount());
			wrap.getMapRemindTaskWrap().put("beforeBusIdCount", instance.getMapRemindTask().size());
			wrap.getMapRemindTaskWrap().put("beforeBusIdData", instance.getMapRemindTask());
			wrap.setCidList(instance.getCidList());
			timerCountWrap.put("timerCount", timerCount.size());
			timerCountWrap.put("timerData", timerCount);
			runningTaskWrap.put("runningCount", runningTask.size());
			runningTaskWrap.put("runningData", runningTask);
			wrap.setFinishedList(instance.getFinishedList());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return wrap;
	}
	@ResponseBody
	@RequestMapping("/getRemindRecord2")
	public BusRemindUtilsTask getRemindRecord2() {
		BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
		// 方便下一次测试
		instance.setSpecificCid("");
		Map<String, Object> timerCountWrap = instance.getTimerCountWrap();
		Map<String, String> timerCount = instance.getTimerCount();
		Map<String, Object> runningTaskWrap = instance.getRunningTaskWrap();
		Map<String, Long> runningTask = instance.getRunningTask();
		
		//instance.getRecoredMap().put("RunningCount", instance.getCidList().size());
		Scheduler scheduler =schedulerFactory.getScheduler();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupStartsWith("jobGroup"));
			instance.setSchedulerCount(jobKeys.size());
			
			timerCountWrap.put("timerCount", timerCount.size());
			timerCountWrap.put("timerData", timerCount);
			runningTaskWrap.put("runningCount", runningTask.size());
			runningTaskWrap.put("runningData", runningTask);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}

}
