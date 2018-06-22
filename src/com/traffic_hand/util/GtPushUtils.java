package com.traffic_hand.util;
import java.util.List;

import org.apache.log4j.Logger;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.traffic_hand.Activemq.bus.busstask.BusRemindTask;
import com.traffic_hand.Activemq.bus.busstask.BusRemindUtilsTaskWrap;
import com.traffic_hand.common.Constants;

public class GtPushUtils {
	public static Logger log = Logger.getLogger("busRemind");
	public static Logger logger = Logger.getLogger(GtPushUtils.class);
		/**
		 * 对指定CID的APP透传
		 */
		public static void sendMessage(String CID,String type,String sendMeg) throws Exception {
			
			// 透传
			IGtPush push = new IGtPush(Constants.host, Constants.appKey, Constants.masterSecret);
			// LinkTemplate template = linkTemplateDemo();
			if(sendMeg==null||"".equals(sendMeg)) {
				sendMeg="交通在手APP提醒您：车辆即将到站，请注意！";
			}
			// 调用透传方法
			TransmissionTemplate template2=null;
			NotificationTemplate template1=null;
			if("2".equals(type)) {
				template2 = getTemplate(sendMeg);
			}else {
				template1= notificationTemplateDemo(Constants.appId,Constants.appKey,sendMeg);
			}
			//TransmissionTemplate template = getTemplate();
			//NotificationTemplate template= notificationTemplateDemo(GtpushConfig.appId,GtpushConfig.appKey);
			SingleMessage message = new SingleMessage();
			message.setOffline(true);
			// 离线有效时间，单位为毫秒，可选
			message.setOfflineExpireTime(24 * 3600 * 1000);
			// 透传
			if("2".equals(type)) {
				message.setData(template2);
			}else {
				message.setData(template1);
			}
			
			// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
			message.setPushNetWorkType(0);
			Target target = new Target();
			target.setAppId(Constants.appId);
			target.setClientId(CID);
			// target.setAlias(Alias);
			IPushResult ret = null;
			try {
				ret = push.pushMessageToSingle(message, target);
			} catch (RequestException e) {
				e.printStackTrace();
				ret = push.pushMessageToSingle(message, target, e.getRequestId());
			}
			if (ret != null) {
				System.out.println(ret.getResponse().toString());
			} else {
				System.out.println("服务器响应异常");
			}
			return;
		}

		/**
		 * 透传消息
		 *
		 * @return
		 */
		public static TransmissionTemplate getTemplate(String sendMeg) {
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(Constants.appId);
			template.setAppkey(Constants.appKey);
			template.setTransmissionContent(sendMeg);
			logger.info("已透传");

			// 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
			template.setTransmissionType(1);
			APNPayload payload = new APNPayload();
			payload.setBadge(1);
			payload.setContentAvailable(1);
			payload.setSound("default");
			payload.setCategory("$由客户端定义");
			// 简单模式APNPayload.SimpleMsg
			payload.setAlertMsg(new APNPayload.SimpleAlertMsg(sendMeg));
			// 字典模式使用下者
			// payload.setAlertMsg(getDictionaryAlertMsg());
			template.setAPNInfo(payload);
			return template;
		}
		
		// 通知
		public static NotificationTemplate notificationTemplateDemo(String appId, String appkey,String sendMeg) {
			NotificationTemplate template = new NotificationTemplate();
			// 设置APPID与APPKEY
			template.setAppId(appId);
			template.setAppkey(appkey);
			// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
			template.setTransmissionType(1);
			template.setTransmissionContent("请输入您要透传的内容");
			// 设置定时展示时间
			// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
			Style0 style = new Style0();
			// 设置通知栏标题与内容
			style.setTitle("交通在手");
			style.setText(sendMeg);
			// 配置通知栏图标
			style.setLogo("icon.png");
			// 配置通知栏网络图标
			style.setLogoUrl("");
			// 设置通知是否响铃，震动，或者可清除
			style.setRing(true);
			style.setVibrate(true);
			style.setClearable(true);
			template.setStyle(style);
			 return template;
			}

}
