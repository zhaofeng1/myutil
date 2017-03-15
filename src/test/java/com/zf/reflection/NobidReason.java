/**
 * 
 */
package com.zf.reflection;

/**
 * 
 * nobidreason
 * 
 * <pre>
 * 	21*** bitset
 *  22*** 不匹配
 *  23*** 频次控制
 *  24*** 预算和余额相关
 *  30*** 系统内部错误
 * </pre>
 * 
 * 
 */
public interface NobidReason {

	/********************* Bitset 过滤条件***************** */
	public final static int BITSET_ADEXCHANGE = 21000;
	public final static int BITSET_CARRIER = 21001;
	public final static int BITSET_OSV = 21002;
	public final static int BITSET_DEVICE_MODEL = 21003;
	public final static int BITSET_EMPTY_OSV = 21004;
	public final static int BITSET_GEO = 21005;
	public final static int BITSET_JS = 21006;
	public final static int BITSET_BDOMAIN = 21007;
	public final static int BITSET_BBUNDLE = 21008;
	public final static int BITSET_BBADV = 21009;
	public final static int BITSET_BATTR = 21010;
	public final static int BITSET_BCATEGOTY = 21011;
	public final static int BITSET_ADSIZE = 21012;
	public final static int BITSET_BAPPID = 21013;
	public final static int BITSET_BSITEID = 21014;
	public final static int BITSET_APPID = 21015;
	public final static int BITSET_SITEID = 21016;
	public final static int BITSET_BUNDLE = 21017;
	public final static int BITSET_DOMAIN = 21018;
	public final static int BITSET_PUBLISHER = 21019;
	public final static int BITSET_BPUBLISHER = 21020;
	public final static int BITSET_ADTYPE = 21021;
	public final static int BITSET_ADX = 21022;
	public final static int BITSET_TIMETARGET = 21023;
	public final static int BITSET_CONNTYPE = 21025;
	public final static int BITSET_ALLOW_PMP = 21026;
	/***************** 请求不匹配************************* */

	/**
	 * IP未匹配
	 */
	public final static int IP_NOT_MATCH = 22003;

	/**
	 * IDfa未匹配
	 */
	public final static int DEVICEID_NOT_MATCH = 22005;

	/**
	 * Region 未匹配
	 */
	public final static int REGION_NOT_MATCH = 22006;

	/**
	 * idfa 黑名单
	 */
	public final static int BLACK_DEVICEID = 22007;

	/**
	 * googleId或idfa为空
	 */
	public static final int DEVICE_ID_IS_NULL = 22008;

	/** ************************频次控制**************************** */

	/**
	 * 达到IP+UA曝光时间间隔限制
	 */
	public final static int IP_UA_IMP_FREQUENCY_CAP = 23001;

	/**
	 * 达到IPScoper+UA曝光时间间隔限制
	 */
	public final static int IPSCOPE_UA_IMP_FREQUENCY_CAP = 23002;

	/**
	 * 该Ip超过出价次数控制
	 */
	public final static int IP_COUNTER_CAP = 23004;

	/**
	 * 该domain超过出价次数控制
	 */
	public final static int DOMAIN_COUNTER_CAP = 23005;

	/**
	 * 该App Bundle 超过出价次数控制
	 */
	public final static int BUNDLE_COUNTER_CAP = 23006;
	/**
	 * 达到IPScoper+CampaignID曝光时间间隔限制
	 */
	public final static int IPSCOPE_IMP_FREQUENCY_CAP = 23007;

	/**
	 * 达到IP+CampaingId曝光时间间隔限制
	 */
	public final static int IP_IMP_FREQUENCY_CAP = 23008;

	/**
	 * cached IP,UA的频次检查
	 */
	public final static int CACHED_CAMPAIGN_UA_FREQUENCY = 23009;

	/**
	 * cached IP的频次检查
	 */
	public final static int CACHED_CAMPAIGN_IP_FREQUENCY = 23010;

	/**
	 * IDFA 曝光频次限制
	 */
	public final static int IDFA_FREQUNCY_CAP = 23011;

	/**
	 * Adid 曝光次数超过设定值
	 */
	public static final int ADID_WIN_LIMIT_COUNTER = 23012;

	/**
	 * Adid 点击次数超过设定值
	 */
	public static final int ADID_CLICK_LIMIT_COUNTER = 23013;

	/**
	 * Native required 字段验证失败
	 */
	public static final int NATIVE_ASSETS_REQUIRED = 23014;
	/**
	 * 达到该周期设定的win数量
	 */
	public static final int BID_WIN_CAP = 23015;

	/********************* 预算和出价相关错误码 ************************/
	/**
	 * 账户预算下限
	 */
	public final static int ACCOUNT_BALANCE_LOWWER = 24001;

	/**
	 * 系列预算下限
	 */
	public final static int CAMPAIGN_BALANCE_LOWWER = 24002;

	/**
	 * Brand 余额下限
	 */
	public final static int BRAND_BALANCE_LOWWER = 24003;

	/**
	 * Group 余额下限
	 */
	public final static int GROUP_BALANCE_LOWER = 24004;
	/**
	 * 达到当前平分后的预算上限,目前会把天预算根据权重平分到每分钟
	 */
	public final static int LIMITED_REALTIME_BUDGET = 24005;
	/**
	 * 最小出价限制
	 */
	public final static int BID_FLOOR_CAP = 24006;

	/**
	 * 打开动态出价后，当前小时的花费超过当前小时的权重值
	 */
	public static final int DYNAMIC_LIMITED_REALTIME_BUDGET = 24007;

	/**
	 * 系列预算尚未同步到内存中
	 */
	public final static int CAMPAIGN_BALANCE_EMPTY = 24008;

	/** *********Campaign设置 相关信息不bid **************************/
	/**
	 * 没有广告
	 */
	public final static int BIDDER_EMPTY_ADS = 25001;

	/**
	 * 返回空campaign
	 */
	public final static int BIDDER_NOT_FOUND_CMP = 25002;

	/**
	 * Campaign已暂停
	 */
	public final static int CAMPAIGN_PAUSED = 25003;

	/**
	 * 通过索引查找Campaign没有匹配到任何Campaign
	 */
	public final static int FIND_CAMPAIGN_BY_BITSET_RESULT_EMPTY = 25004;

	/** *********bidder 内部错误码 **************************/
	/**
	 * 内部错误
	 */
	public final static int BIDDER_ERROR = 30000;
	/**
	 * 调用findCampaign接口结果为NULL
	 */
	public final static int FIND_CAMPAIGN_RESULT_NULL = 30001;

	/**
	 * 查找Campaign错误
	 */
	public final static int BIDDER_FIND_CAMPAIGN_ERROR = 30002;

	/**
	 * 索引查找错误
	 */
	public final static int SEARCHER_INDEX_NULL_POINT_ERROR = 30003;

	/**
	 * 未catch错误
	 */
	public final static int SEARCHER_INDEX_EXCEPTION = 30004;

	/**
	 * next检查器错误
	 */
	public final static int SEARCHER_CHECKER_ERROR = 30006;

	/**
	 * 生成应答json
	 */
	public final static int BIDDER_MAKE_RESPONSE_ERROR = 30009;

	/**
	 * 解析请求json错误
	 */
	public final static int BIDDER_PARSE_JSON_ERROR = 30010;
	
	/**
	 * 解析请求protocolbuffer 错误
	 */
	public final static int BIDDER_PARSE_PROTOCOL_ERROR = 30011;
	

}
