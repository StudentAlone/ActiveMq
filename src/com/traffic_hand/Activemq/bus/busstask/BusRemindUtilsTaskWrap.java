package com.traffic_hand.Activemq.bus.busstask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.recompile;
import com.traffic_hand.vo.req.v1.bus.BusRemindReqVo;

public class BusRemindUtilsTaskWrap {
	// allSend allReceive  RunningCount
	//private Map<String,Integer>  recoredMap=new HashMap<String ,Integer>();
	private Integer schedulerCount=0;
	// 处理幽灵车：cid beforeBusId
	private Map<String ,Object> mapRemindTaskWrap=new HashMap<String,Object>();
	// 发送多少条
	private List<Map<String,Object>> allSend=new ArrayList<>();
	// 接收多少条
	private List<Map<String,Object>> allReceive=new ArrayList<>();
	
	
	// 定时器任务数据
	private Map<String,Object> timerCountWrap=new HashMap<>();
	// 正在运行的任务
	private List<String> cidList=new ArrayList<>();
	private Map<String,Object> runningTaskWrap=new HashMap<>();
	private List<String> finishedList=new ArrayList<>();
	
	public static BusRemindUtilsTaskWrap instance = new BusRemindUtilsTaskWrap();
	

	private BusRemindUtilsTaskWrap() {
	}

	public static BusRemindUtilsTaskWrap getInstance() {
		
		return instance;
	}

/*	public Map<String, Integer> getRecoredMap() {
		return recoredMap;
	}*/

	public Map<String, Object> getMapRemindTaskWrap() {
		return mapRemindTaskWrap;
	}

	public List<Map<String, Object>> getAllSend() {
		return allSend;
	}

	public List<Map<String, Object>> getAllReceive() {
		return allReceive;
	}

	public Map<String, Object> getTimerCountWrap() {
		return timerCountWrap;
	}

	public Map<String, Object> getRunningTaskWrap() {
		return runningTaskWrap;
	}

/*	public void setRecoredMap(Map<String, Integer> recoredMap) {
		this.recoredMap = recoredMap;
	}*/

	public void setAllSend(List<Map<String, Object>> allSend) {
		this.allSend = allSend;
	}

	public void setAllReceive(List<Map<String, Object>> allReceive) {
		this.allReceive = allReceive;
	}

	public Integer getSchedulerCount() {
		return schedulerCount;
	}

	public void setSchedulerCount(Integer schedulerCount) {
		this.schedulerCount = schedulerCount;
	}

	public List<String> getFinishedList() {
		return finishedList;
	}

	public void setFinishedList(List<String> finishedList) {
		this.finishedList = finishedList;
	}

	public List<String> getCidList() {
		return cidList;
	}

	public void setCidList(List<String> cidList) {
		this.cidList = cidList;
	}
	
	

}
