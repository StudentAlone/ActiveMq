package com.traffic_hand.domain.v1.bus;

public class BusRemindEntity {
	private Integer returnCode;
	private String returnMsg;
    public BusRemindEntity() {
    	
    }
    
    public BusRemindEntity(Integer returnCode,String returnMsg) {
    	this.returnCode=returnCode;
    	this.returnMsg=returnMsg;
    }
    
    public static BusRemindEntity getBusRemindEntity(Integer returnCode,String returnMsg) {
    	return new BusRemindEntity(returnCode,returnMsg);
    }
	
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	

	@Override
	public String toString() {
		return "returnCode=" + returnCode + ", returnMsg=" + returnMsg;
	}
}
