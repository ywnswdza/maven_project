package com.losy.common.utils;


public class MemcachedKeyPrefix {
	//######################user##########################
	public static String MobileAccount = "ua_m_";
	public static String EmailAccount = "ua_e_";
//	public static String UserIdAccount = "ua_i_";
	public static String UserAccount = "ua_";
	public static String UserAccountInfo = "ua_info_";
	public static String UserAccountImsi = "ua_im_";
	
	public static String authorize = "authorize2_";
	public static String ignoreRegLimit = "ir_";
	public static String GSLogin = "gs_";
	//#######################user########################
	
	
	//###############sdk############start
	//MemcachedSDKUtils公用
	
	/** 缓存 每个 产品的 cmge client download url **/
	public static String SPY = "spy_";
	
	public static String AppSdkCMGEDownloadURL = "sdk_appSdkCMGEDownloadURL_";
	
	/** 網遊日曆的记录**/
	public static String AppCalender = "sdk_appCalender_";
	
	/** sdk精品推荐ids的记录**/
	public static String AppSdkInfoIds = "sdk_appSdkInfoIds_";
	
	/** sdk精品推荐  product 对应的info**/
	public static String AppSdkInfo = "sdk_appSdkInfo_";
	
	/** sdk精品推荐 详细 data**/
	public static String AppSdkInfoData = "sdk_appSdkInfoData_";
	
	/** imsi限额 **/
	public static String LimitPayIMSI = "sdk_limit_pay_imsi_";
	
	/** 账号限额 **/
	public static String LimitPayUserAccount = "sdk_limit_pay_userAccount_";
	
	/** 玩家最后支付时间  **/
	public static String LastPayTime = "sdk_user_lastPayTime_";
	/** 玩家最后支付时间(imsi)**/
	public static String LastPayTimeImsi = "s_u_l_p_imsi_";
	
	/** 缓存 t_product_server 表数据  **/
	public static String ProductServerExt = "p_server_ext_";
	
	/** 缓存 t_product_charge_esqinfo 表数据  **/
	public static String ChargeEsqinfo = "chargeesqinfo_";
	
	/** 缓存 t_product_charge_activity 表数据  **/
	public static String ChargeActivity = "chargeactivity_";
	
	/** imsi注册限制数  **/
	public static String SDKUUIDRegLimit = "sdk_uuid_reg_limit_";
	
	/** 前n个激活数在memcached中key的前缀 **/
	public static String ACT_TOP_KEY = "actnum_";
	
	/** 缓存订单数据 (order_订单id)  **/
	public static String orderkey = "order_";
	/** 产品名 **/
	public static String productName = "pm_";
	
	/** 产品是否为vip**/
	public static String productIsVip = "sdk_product_vip_";
	
	/** vip信息**/
	public static String GsVipInfo = "sdk_gs_vipinfo_";
	
	/** 渠道名 **/
	public static String channelName = "cm_";
	
	/** 渠道产品信息 **/
	public static String channelProduct = "cp_";
	
	/** 渠道信息 **/
	public static String channels = "chl_";
	
	/** 产品标识信息 **/
	public static String productMark = "pmark_";
	
	/** 产品标识信息,包名不唯一 **/
	public static String packageProductMark = "ppm2_";
	
	/** 支付方式 **/
	public static String chargeType = "ct2_";
	
	/** 话费支付通道 **/
	public static String noteChannels = "nc_";
	
	/** 忽略短信限制的imsi**/
	public static String ignorePayLimit = "ipaylimit_";
	/** 充值忽略二次确认 产品id**/
	public static String filterproductId = "filterproductId_";
	/** 单机版没有通道时是否发金币 **/
	public static String singleNotePayFlag = "singleNotePayFlag_";
	
	/** 前n个已激活的douId列表  **/
	public static String channel_checked_record = "channel_checked_record_";
	public static String mobileOperatorPrice = "m_o_p_";
	/** 统计已经创建的表 **/
	public static String statTableName = "stat_t_";
	/** 单机版补单 **/
	public static String replenGold = "sdk_repleng_";
	
	/** 显示更多游戏 **/
	public static String NoChannelRecommend = "sdk_no_channel_recommend_";
	
	/** vip desc **/
	public static String VipDesc = "sdk_vipdesc_";
	
	/** 快充金额数据 **/
	public static String RapidCharge = "sdk_rapid_";
	
	/** ip对应省份 **/
	public static String IPProvince = "sdk_province_ip_";
	
	/** 请求短信通道记录 **/
//	public static String RequestNotePayChannel = "n_p_c_";
	public static String RequestNotePayChannel2 = "n_p_c2_";
	/** 缓存t_product_info表信息**/
	public static String ProductInfos = "sdk_productInfos_";

	/** 记录请求的通道 **/
	public static String NotePayChannel2 = "n_p_c2_";
	
	public static String ChargeList = "p_t_id_";
	
	public static String PayChennelList = "paychennellist_";
	
	public static String Pay19ChennelList = "pay19chennellist_";
	
	public static String Pay19ChennelData = "pay19chennelData_";
	
	/** 快速出包  **/
	public static String QUICKPACKAGE = "quickPackage";
	/** order已经创建的表 **/
	public static String orderTableName = "order_t_";
	public static String orderRecordTableName = "order_record_t_";
	public static String orderPaysynTableName = "order_paysyn_t_";
	public static String orderPaygameTableName = "order_paygame_t_";
	public static String orderNoteTradeTableName = "order_note_t_";
	public static String requestChannelRecordTableName = "r_c_r_";
	public static String cpRoleDataTableName = "cp_role_data_";
	
	public static String orderMiddleTableName = "order_t_mid_";
	public static String orderRecordMiddleTableName = "order_record_t_mid_";
	public static String orderPaysynMiddleTableName = "order_paysyn_t_mid_";
	public static String orderPaygameMiddleTableName = "order_paygame_t_mid_";
	
	public static String paymentId = "paymentid_";
	
	public static String EntityProduct = "e_product_";
	public static String EntityOrder = "e_order_";
	public static String EntityOrderRecord = "e_order_record_";
	public static String EntityChargeTypeProduct = "e_ctp_";
	public static String EntityChannel = "e_channel_";
	public static String EntityChargeActivity = "e_c_act_";
	public static String ProductSort = "e_p_sort_";
	public static String ProductPicture = "e_p_pic_";
	public static String ProductPictureList = "l_p_pic_";
	public static String TableStatDay = "t_stat_";
	public static String TableStatDayMiddle = "t_stat_mid_";
	
	public static String RefuseSMS = "refuse_sms_";
	
	public static String NoticesLast = "notice_2_last_";
	
	public static String NoticesList = "notice_l_";
	public static String Notices2Ids = "notice_ids_2_";
	public static String UserLastNoticeId = "p_u_l_";
	public static String Notices = "notice_";
	public static String Menu = "e_menu_";
	public static String ProductMenus = "p_menus_";
	
	public static String Platform = "platform_"; 
	public static String Plugin = "plugin_";
	public static String MemCusMiddle = "mid_";
	public static String BBSURL = "lt_url_";
	public static String MIDDLE_BBS = "middle_bbs";
	//联通计费点
	public static String ChargePoint = "charge_point_";
	
	//###################sdk#######################end
	
	
	//	积分墙推广AppStore相关
	/** cp缓存key  **/
	public static String CP_KEY = "cpuuid_";
	/** 积分墙缓存key  **/
	public static String JFQ_KEY = "jfquuid_";
	/** 积分墙缓存　操作方式　添加到缓存 */
	public static String JFQ_API_ADD_TYEP = "optTypeAdd";
	/** 在缓存中查找 */
	public static String JFQ_API_QUERY_TYEP = "optTypeQuery";
	/**在缓存中判断有效**/
	public static String JFQ_SECRET_ADD_TYEP = "add_secret";
	public static String JFQ_SECRET_QUERY_TYEP = "query_secret";
	
	
	/** 缓存t_sdk_game_push表信息**/
	public static String ProductPushBanner = "sdk_game_push_ban_";
	
	public static String ProductPushNew = "sdk_game_push_ban_new_";
	
	public static String ProductPushJinp = "sdk_game_push_ban_jinp_";
	
	/** 缓存游戏中心详情页面数据接口**/
	
	public static String ProductPushInfo = "sdk_game_push_info_";
	
}