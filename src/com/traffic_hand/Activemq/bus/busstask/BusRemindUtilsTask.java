package com.traffic_hand.Activemq.bus.busstask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.recompile;
import com.traffic_hand.vo.req.v1.bus.BusRemindReqVo;

public class BusRemindUtilsTask {
	private Integer schedulerCount=0;
	// 处理幽灵车：cid beforeBusId
	private Map<String ,String> mapRemindTask=new HashMap<String,String>();
	// allSend allReceive  RunningCount
	private Map<String,Integer>  recoredMap=new HashMap<String ,Integer>();
	
	// RunningCount
	private List<String> cidList=new ArrayList<>();
	// test 9314529eb3111abf4bbedbcec6fc57a7
	private String specificCid="";
	
	// 发送多少条
	private List<Map<String,Object>> allSend=new ArrayList<>();
	// 接收多少条
	private List<Map<String,Object>> allReceive=new ArrayList<>();
	// 定时器任务数据
	private Map<String,String> timerCount=new HashMap<>();
	// 正在运行的任务
	
	private Map<String,Long> runningTask=new HashMap<>();
	

	// 定时器任务数据
	private Map<String,Object> timerCountWrap=new HashMap<>();
	// 正在运行的任务
	private Map<String,Object> runningTaskWrap=new HashMap<>();
	
	private List<String> finishedList=new ArrayList<>();
	
	public static BusRemindUtilsTask instance = new BusRemindUtilsTask();
	

	private BusRemindUtilsTask() {
	}

	public static BusRemindUtilsTask getInstance() {
		
		return instance;
	}

	public Map<String, String> getMapRemindTask() {
		return mapRemindTask;
	}

	public Map<String, Integer> getRecoredMap() {
		return recoredMap;
	}

	public List<String> getCidList() {
		return cidList;
	}

	public String getSpecificCid() {
		return specificCid;
	}

	public List<Map<String, Object>> getAllSend() {
		return allSend;
	}

	public List<Map<String, Object>> getAllReceive() {
		return allReceive;
	}

	public Map<String, String> getTimerCount() {
		return timerCount;
	}

	public Map<String, Long> getRunningTask() {
		return runningTask;
	}

	public Integer getSchedulerCount() {
		return schedulerCount;
	}

	public void setSchedulerCount(Integer schedulerCount) {
		this.schedulerCount = schedulerCount;
	}

	public void setSpecificCid(String specificCid) {
		this.specificCid = specificCid;
	}

	public Map<String, Object> getTimerCountWrap() {
		return timerCountWrap;
	}

	public Map<String, Object> getRunningTaskWrap() {
		return runningTaskWrap;
	}

	public List<String> getFinishedList() {
		return finishedList;
	}

	

}
