package com.traffic_hand.Activemq.bus.busstask;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic_hand.Activemq.taskManger.QuartzManager;
import com.traffic_hand.domain.v1.bus.BusNearestBus;
import com.traffic_hand.util.GtPushUtils;
import com.traffic_hand.util.LocalStringUtils;
import com.traffic_hand.util.NearestBusUtils;
import com.traffic_hand.util.SpringContextUtil;
import com.traffic_hand.vo.req.v1.bus.BusRemindReqVo;

/**
 * 业务定时处理
 * 
 * @author Administrator
 * 
 */
public class BusRemindTask implements Job {
	public static Logger log = Logger.getLogger("busRemind");
	public static Logger logger = Logger.getLogger(BusRemindTask.class);
	public static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long sTime = System.currentTimeMillis();
		BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
		Map<String, String> busIdMap = instance.getMapRemindTask();
		List<String> cidList = instance.getCidList();
		List<String> finishedList = instance.getFinishedList();
		Map<String, Long> runningTask = instance.getRunningTask();

		String messages = arg0.getJobDetail().getJobDataMap().getString("cidMessKey");
		QuartzManager quartzManager = (QuartzManager) SpringContextUtil.getBean("quartzManager");

		long arrivedTime = 0;
		long beforeTime = -1;
		String timerExp = "0/30 * * * * ?";
		System.out.println("无状态quartz：" + timerExp);
		Boolean noData = false;
		String cid = null;
		String stationId = null;
		String lineId = null;
		String sendType = null;
		log.info("正在处理：" + messages);
		log.info("处理时间" + new Date().toLocaleString());
		try {
			BusRemindReqVo readValue = mapper.readValue(messages, BusRemindReqVo.class);
			cid = readValue.getCid();
			stationId = readValue.getStationId();
			lineId = readValue.getLineId();
			sendType = readValue.getSendType();
			// test
			if (!cidList.contains(cid)) {
				cidList.add(cid);
			}

			List<BusNearestBus> nearestBusList = NearestBusUtils.getNearestBus(stationId, lineId);
			if (nearestBusList == null || nearestBusList.size() == 0) {
				noData = true;
			}
			BusNearestBus NearestBus = nearestBusList.get(0);
			arrivedTime = NearestBus.getTimeCost();
			// 记录正在运行的
			runningTask.put(cid, arrivedTime);
			String beforeBusId = busIdMap.get(cid) == null ? "" : busIdMap.get(cid);
			// 说明是第一次寻找，本身就是最近的车辆
			if ("".equals(beforeBusId)) {
				// 更新自己为最近车辆
				beforeBusId=NearestBus.getBusId();
				busIdMap.put(cid, NearestBus.getBusId());
			}
			System.out.println(beforeBusId.equals(NearestBus.getBusId())+":"+beforeBusId);
			
			// 幽灵车
			if (!beforeBusId.equals(NearestBus.getBusId())) {
				beforeTime = NearestBusUtils.getAllAliveBus(stationId, lineId, beforeBusId);
				// 超车
				if (beforeTime > arrivedTime) {
					// 更新每次拿到最近车辆的id
					busIdMap.put(cid, NearestBus.getBusId());
				} else {
					GtPushUtils.sendMessage(cid, sendType, "上车提醒:车辆失联，可能提前到站，请注意!");
				}
			}

			System.out.println(arrivedTime);
			// 超过5分钟，一半的时间后访问
			if (arrivedTime > 300) {
				String halfArrivedTime = arrivedTime / (2 * 60) + "";
				timerExp = "0 " + "*/" + halfArrivedTime + " * * * ?";
			}
			if (arrivedTime > 90) {
				log.info(cid + "修改前timerExp:" + timerExp);
				quartzManager.modifyJobTime(cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid,
						timerExp);
				log.info(cid + "修改：" + timerExp);
			}
			if (arrivedTime >= 0 && arrivedTime < 90) {
				quartzManager.removeJob(cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
				cidList.remove(cid);
				finishedList.add(cid);
				// 正常情况下，使用前台传递的参数
				GtPushUtils.sendMessage(cid, sendType, readValue.getSendMessage());
				log.info(cid + "推送");
			}
			if (arrivedTime < 0) {
				quartzManager.removeJob(cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
				cidList.remove(cid);
				finishedList.add(cid);
				GtPushUtils.sendMessage(cid, sendType, "上车提醒:车辆已过站!");
			}
			
			log.info("最新时间：" + arrivedTime);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (noData) {
					GtPushUtils.sendMessage(cid, sendType, "上车提醒:暂无车辆，等待发车!");
				} else {
					GtPushUtils.sendMessage(cid, sendType, "上车提醒:系统异常!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error(cid + "推送组件发生异常!");
			}
			quartzManager.removeJob(cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
			cidList.remove(cid);
			finishedList.add(cid);
			throw new RuntimeException(cid + "推送失败!");
		}
		long eTime = System.currentTimeMillis();
		logger.info("jobTime:"+(eTime-sTime));
	}
}