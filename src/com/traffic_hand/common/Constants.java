package com.traffic_hand.common;

import com.traffic_hand.util.PropertiesUtils;

public class Constants {
	/** 配置文件名称 */
	public static final String CONFIG_FILE_NAME = "config.properties";
	/** HTTP请求成功状态码 */
	public static final int HTTP_SUCCESS_STATUS_CODE = 200;
	/** 编码格式*/
	public static final String CHARSET_UTF8 = "UTF-8";
	/** Date默认时区 **/
	public static final String DATE_TIMEZONE = "GMT+8";
	
	/** 权鉴信息code前缀 */
	public static final String AUTH_CODE_PREFIX = "OP_CODE_";
	/** 权鉴信息psw前缀 */
	public static final String AUTH_PWS_PREFIX = "OP_PSW_";
	/** 权鉴信息key前缀 */
	public static final String AUTH_KEY_PREFIX = "OP_KEY_";
	
	/** poi-tgis-地址*/
	public static final String POI_TGIS_URL = PropertiesUtils.getConfigInfo("POI_TGIS_URL").trim();
	/** poi-tgis-范围坐标*/
	public static final String POI_TGIS_RANGE = PropertiesUtils.getConfigInfo("POI_TGIS_RANGE").trim();
	/** poi-tgis-范围坐标*/
	public static final String POI_TGIS_CONFIG = PropertiesUtils.getConfigInfo("POI_TGIS_CONFIG").trim();
	
	/** 公交-先进院-地址*/
	public static final String BUS_OPENX_URL = PropertiesUtils.getConfigInfo("BUS_OPENX_URL").trim();
	/** 公交-先进院-用户*/
	public static final String BUS_OPENX_USER = PropertiesUtils.getConfigInfo("BUS_OPENX_USER").trim();
	/** 公交-先进院-坐标系*/
	public static final String BUS_OPENX_COORDINATE = PropertiesUtils.getConfigInfo("BUS_OPENX_COORDINATE").trim();
	/** 公交-先进院-关键字查询线路方法*/
	public static final String BUS_OPENX_SEARCHLINE = PropertiesUtils.getConfigInfo("BUS_OPENX_SEARCHLINE").trim();
	/** 公交-先进院-关键字查询站点方法*/
	public static final String BUS_OPENX_SEARCHSTATION = PropertiesUtils.getConfigInfo("BUS_OPENX_SEARCHSTATION").trim();
	/** 公交-先进院-附件站点方法*/
	public static final String BUS_OPENX_NEARBYSTATION = PropertiesUtils.getConfigInfo("BUS_OPENX_NEARBYSTATION").trim();
	/** 公交-先进院-站点经过线路方法*/
	public static final String BUS_OPENX_LINESBYSTATION = PropertiesUtils.getConfigInfo("BUS_OPENX_LINESBYSTATION").trim();
	/** 公交-先进院-线路信息所有站点方法*/
	public static final String BUS_OPENX_LINEINFO = PropertiesUtils.getConfigInfo("BUS_OPENX_LINEINFO").trim();
	/** 公交-先进院-最近到站车辆方法*/
	public static final String BUS_OPENX_NEARESTBUS = PropertiesUtils.getConfigInfo("BUS_OPENX_NEARESTBUS").trim();
	/** 公交-先进院-线路所有车辆方法*/
	public static final String BUS_OPENX_ALLALIVEBUS = PropertiesUtils.getConfigInfo("BUS_OPENX_ALLALIVEBUS").trim();
	/** 公交-先进院-获取静态数据sqlite*/
	public static final String BUS_OPENX_SQLITE = PropertiesUtils.getConfigInfo("BUS_OPENX_SQLITE").trim();
	
	/** 公交-车来了-地址*/
	public static final String BUS_CHELAILE_URL = PropertiesUtils.getConfigInfo("BUS_CHELAILE_URL").trim();
	/** 公交-车来了-请求签名*/
	public static final String BUS_CHELAILE_SIGN = PropertiesUtils.getConfigInfo("BUS_CHELAILE_SIGN").trim();
	/** 公交-车来了-坐标系*/
	public static final String BUS_CHELAILE_COORDINATE = PropertiesUtils.getConfigInfo("BUS_CHELAILE_COORDINATE").trim();
	/** 公交-车来了-版本号*/
	public static final String BUS_CHELAILE_VERSION = PropertiesUtils.getConfigInfo("BUS_CHELAILE_VERSION").trim();
	/** 公交-车来了-关键字模糊查询*/
	public static final String BUS_CHELAILE_SEARCHLINESTATION = PropertiesUtils.getConfigInfo("BUS_CHELAILE_SEARCHLINESTATION").trim();
	/** 公交-车来了-附件站点方法*/
	public static final String BUS_CHELAILE_NEARBYSTATION = PropertiesUtils.getConfigInfo("BUS_CHELAILE_NEARBYSTATION").trim();
	/** 公交-车来了-站点经过线路方法*/
	public static final String BUS_CHELAILE_LINESBYSTATION = PropertiesUtils.getConfigInfo("BUS_CHELAILE_LINESBYSTATION").trim();
	/** 公交-车来了-线路信息所有站点方法*/
	public static final String BUS_CHELAILE_LINEINFO = PropertiesUtils.getConfigInfo("BUS_CHELAILE_LINEINFO").trim();
	/** 公交-车来了-最近到站车辆方法*/
	public static final String BUS_CHELAILE_NEARESTBUS = PropertiesUtils.getConfigInfo("BUS_CHELAILE_NEARESTBUS").trim();
	
	/** 公交-易行网-换乘规划url*/
	public static final String BUS_YXW_BUSPLAN = PropertiesUtils.getConfigInfo("BUS_YXW_BUSPLAN").trim();
	
	/** 长客-票联网-登录用户*/
	public static final String COACH_PLW_USERCODE = PropertiesUtils.getConfigInfo("COACH_PLW_USERCODE").trim();
	/** 长客-票联网-登录密码*/
	public static final String COACH_PLW_USERPASS = PropertiesUtils.getConfigInfo("COACH_PLW_USERPASS").trim();
	
	/** 长客-客货中心-url*/
	public static final String COACH_KHZX_URL = PropertiesUtils.getConfigInfo("COACH_KHZX_URL").trim();
	
	/** 停车服务-停车百事通-url*/
	public static final String PARK_TCBST_URL = PropertiesUtils.getConfigInfo("PARK_TCBST_URL").trim();
	/** 停车服务-停车百事通-用户*/
	public static final String PARK_TCBST_USER = PropertiesUtils.getConfigInfo("PARK_TCBST_USER").trim();
	/** 停车服务-停车百事通-密码*/
	public static final String PARK_TCBST_PSW = PropertiesUtils.getConfigInfo("PARK_TCBST_PSW").trim();
	/** 停车服务-停车百事通-附近方法*/
	public static final String PARK_TCBST_NEARBY = PropertiesUtils.getConfigInfo("PARK_TCBST_NEARBY").trim();
	/** 停车服务-停车百事通-详情方法*/
	public static final String PARK_TCBST_DETAIL = PropertiesUtils.getConfigInfo("PARK_TCBST_DETAIL").trim();
	
	/** 资讯-出行提醒-url*/
	public static final String INFO_CXTX_URL = PropertiesUtils.getConfigInfo("INFO_CXTX_URL").trim();
	/** 资讯-通知公告-url*/
	public static final String INFO_TZGG_URL = PropertiesUtils.getConfigInfo("INFO_TZGG_URL").trim();
	
	
	
	/** 腾讯地图-坐标转换api-地址*/
	public static final String TENCENT_MAP_API_URL = PropertiesUtils.getConfigInfo("TENCENT_MAP_API_URL").trim();
	/** 腾讯地图-坐标转换api-密钥*/
	public static final String TENCENT_MAP_API_KEY = PropertiesUtils.getConfigInfo("TENCENT_MAP_API_KEY").trim();
	
	/** 百度地图-坐标转换api-地址*/
	public static final String BAIDU_MAP_API_URL = PropertiesUtils.getConfigInfo("BAIDU_MAP_API_URL").trim();
	/** 百度地图-坐标转换api-密钥*/
	public static final String BAIDU_MAP_API_KEY = PropertiesUtils.getConfigInfo("BAIDU_MAP_API_KEY").trim();
	
	public static final String INITIAL_SIZE = "Initial_Size";
	public static final String MAX_ACTIVE = "Max_Active";
	public static final String MIN_IDLE = "Min_Idle";
	public static final String MAX_WAIT = "Max_Wait";
	public static final String REMOVE_ABANDONED = "Remove_Abandoned";
	public static final String REMOVE_ABANDONED_TIMEOUT = "Remove_Abandoned_Timeout";
	public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "Time_Between_Eviction_Runs_Millis";
	public static final String TEST_WHILE_IDLE = "Test_While_Idle";
	
	public static final String MYSQL_VALIDATION_QUERY = "Mysql_Validation_Query";
	public static final String ORACLE_VALIDATION_QUERY = "Oracle_Validation_Query";
	

	//个推
	public static final String appId = "9HdfOXOZ0K9Oq8rnOvJQp6";
	public static final String appKey = "GqdU5kgFF467WYiDJJmjH3";
	public static final String masterSecret = "hTThh1uOHW9D2WZeE1LL11";
	public static final String host = "http://sdk.open.api.igexin.com/apiex.htm";

}
