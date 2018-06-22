package com.traffic_hand.domain.v1.bus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BusNearestBus {

	private String busId;//车辆ID
	private int stCount;//车辆距离目标站的站点个数
	private String stStatus;//车辆站点状态（0道路中，1站点上）
	private String stName;//车辆当前站点名称
	private int stOrder;//车辆当前站点序列
	private double lon;//经度
	private double lat;//纬度
	private int timeCost;//耗时(单位：秒)
	private long gpsTime;//预计到达时间
	
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public int getStCount() {
		return stCount;
	}
	public void setStCount(int stCount) {
		this.stCount = stCount;
	}
	public String getStStatus() {
		return stStatus;
	}
	public void setStStatus(String stStatus) {
		this.stStatus = stStatus;
	}
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}
	public int getStOrder() {
		return stOrder;
	}
	public void setStOrder(int stOrder) {
		this.stOrder = stOrder;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getTimeCost() {
		return timeCost;
	}
	public void setTimeCost(int timeCost) {
		this.timeCost = timeCost;
	}
	public long getGpsTime() {
		return gpsTime;
	}
	public void setGpsTime(long gpsTime) {
		this.gpsTime = gpsTime;
	}
}
