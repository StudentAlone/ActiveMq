package com.traffic_hand.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traffic_hand.common.Constants;
import com.traffic_hand.domain.v1.bus.BusNearestBus;
import com.traffic_hand.domain.v1.bus.xbus.XBusAllAliveBusData;
import com.traffic_hand.domain.v1.bus.xbus.XBusAllAliveBusWrap;
import com.traffic_hand.domain.v1.bus.xbus.XBusNearestBusData;
import com.traffic_hand.domain.v1.bus.xbus.XBusNearestBusWrap;

public class NearestBusUtils {
	protected static ObjectMapper mapper = new ObjectMapper();
	/*
	 * 获取最近到站车辆信息
	 */
	public static List<BusNearestBus> getNearestBus(String stationId, String lineId) throws Exception {

		String param = "user=" + Constants.BUS_OPENX_USER + "&sta_id=" + stationId + "&line_id_dir=" + lineId;
		String url = Constants.BUS_OPENX_URL + Constants.BUS_OPENX_NEARESTBUS + "?" + param;

		String data = CryptUtil.decrypt(HttpClientUtils.doGet(url));

		XBusNearestBusWrap xNearestBus = mapper.readValue(data, XBusNearestBusWrap.class);

		// 重新组装最近到站车辆数据
		List<BusNearestBus> busNearestBus = new ArrayList<>();
		for (XBusNearestBusData near : xNearestBus.getData()) {

			BusNearestBus nearestBus = new BusNearestBus();
			nearestBus.setBusId(near.getBusId());
			nearestBus.setStCount(near.getDisByStaCount());
			String[] order = near.getPosByStaIndex().split(">");
			if (order.length > 1) {
				nearestBus.setStStatus("0");
				nearestBus.setStOrder(Integer.parseInt(order[1]));
			} else {
				nearestBus.setStStatus("1");
				nearestBus.setStOrder(Integer.parseInt(order[0]));
			}
			nearestBus.setStName(near.getPosByStaName());
			nearestBus.setLon(near.getLng());
			nearestBus.setLat(near.getLat());
			nearestBus.setTimeCost(near.getTimeCost());
			nearestBus.setGpsTime(near.getGpsTime() / 1000);
			busNearestBus.add(nearestBus);
		}
		//test
/*		if(busNearestBus.size()==0) {
			BusNearestBus s=new BusNearestBus();
			s.setTimeCost(200);
			s.setBusId("XBUS_00006249");
			busNearestBus.add(s);
		}*/
		return busNearestBus;
	}
	
	/*
	 * 获取线路所有车辆信息
	 */
	public static Long getAllAliveBus(String stationId, String lineId,String beforeBusId) throws Exception {
		//默认未匹配到车辆
		long timeCost=-2;
		String param = "user=" + Constants.BUS_OPENX_USER + "&sta_id=" + stationId + "&line_id_dir=" + lineId;
		String url = Constants.BUS_OPENX_URL + Constants.BUS_OPENX_ALLALIVEBUS + "?" + param;

		String data = CryptUtil.decrypt(HttpClientUtils.doGet(url));
		XBusAllAliveBusWrap xAllAliveBus = mapper.readValue(data, XBusAllAliveBusWrap.class);

		for (XBusAllAliveBusData near : xAllAliveBus.getData()) {
			
			if(beforeBusId.equals(near.getBusId())) {
				timeCost=near.getTimeCost();
			}
		}
		
		return timeCost;
	}
}
