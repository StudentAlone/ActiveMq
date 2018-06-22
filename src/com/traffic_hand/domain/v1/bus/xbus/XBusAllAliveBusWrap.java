package com.traffic_hand.domain.v1.bus.xbus;

import java.util.List;

public class XBusAllAliveBusWrap extends XBusBaseData {

	private List<XBusAllAliveBusData> data;//具体返回信息
	
	public List<XBusAllAliveBusData> getData() {
		return data;
	}
	public void setData(List<XBusAllAliveBusData> data) {
		this.data = data;
	}
}
