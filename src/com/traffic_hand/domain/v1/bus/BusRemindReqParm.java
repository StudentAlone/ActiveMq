package com.traffic_hand.domain.v1.bus;

public class BusRemindReqParm {

	private String cityCode;// 城市代码
	private String busSrc;// 数据来源代码
	private String stationId;// 公交站点
	private String lineId;// 公交线路id
	private String cid; // 个推设备id
	private String sendType; // 推送方式1：通知 2：透传
	private String interfaceType; // 提醒方式1：上车 2：下车

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBusSrc() {
		return busSrc;
	}

	public void setBusSrc(String busSrc) {
		this.busSrc = busSrc;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

}
