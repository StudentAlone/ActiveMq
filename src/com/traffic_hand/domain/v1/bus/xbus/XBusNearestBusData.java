package com.traffic_hand.domain.v1.bus.xbus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class XBusNearestBusData {

	private String busId;//车辆ID
	private int timeCost;//耗时(单位：秒)
	private long gpsTime;//预计到达时间
	private int disByStaCount;//车辆距离目标站的站点个数
	private double disByMetre;//车辆距离目标站点多少米
	private String posByStaName;//车辆当前站点位置名称
	private String posByStaIndex;//车辆当前站点位置
	private double lng;//经度
	private double lat;//纬度
	
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
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
	public int getDisByStaCount() {
		return disByStaCount;
	}
	public void setDisByStaCount(int disByStaCount) {
		this.disByStaCount = disByStaCount;
	}
	public double getDisByMetre() {
		return disByMetre;
	}
	public void setDisByMetre(double disByMetre) {
		this.disByMetre = disByMetre;
	}
	public String getPosByStaName() {
		return posByStaName;
	}
	public void setPosByStaName(String posByStaName) {
		this.posByStaName = posByStaName;
	}
	public String getPosByStaIndex() {
		return posByStaIndex;
	}
	public void setPosByStaIndex(String posByStaIndex) {
		this.posByStaIndex = posByStaIndex;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
}
