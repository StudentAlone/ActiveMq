package com.traffic_hand.domain.v1.bus.xbus;

import java.util.List;

public class XBusNearestBusWrap extends XBusBaseData {

	private List<XBusNearestBusData> data;//具体返回信息
	
	public List<XBusNearestBusData> getData() {
		return data;
	}
	public void setData(List<XBusNearestBusData> data) {
		this.data = data;
	}
}
