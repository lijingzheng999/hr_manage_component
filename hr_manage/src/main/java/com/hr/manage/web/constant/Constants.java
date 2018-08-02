package com.hr.manage.web.constant;

/**
 *
 * @author renren
 */

public class Constants {

	/**
	 * 商品状态:下架=0,上架=1,就绪=2,删除=3
	 */
	public static final int PRODUCT_STATUS_UNVALID = 0;
	public static final int PRODUCT_STATUS_VALID = 1;
	public static final int PRODUCT_STATUS_READY = 2;
	public static final int PRODUCT_STATUS_DELETE = 3;
	
	/**
	 * 商品类型  健康生活=1,手机周边=2,虚拟商品=3
	 */
	public static final int PRODUCT_TYPE_JKSH = 1;
	public static final int PRODUCT_TYPE_SJZB = 2;
	public static final int PRODUCT_TYPE_XN = 3;
	
	/**
	 * 商品选项状态
	 */
	public static final int OPTION_TYPE_VALID = 1;
	public static final int OPTION_TYPE_UNVALID = 0;
	
	//网购短信签名
	public static final String SMS_ENDING = "【中国移动网购平台】";
	
	/**
	 * 导购状态：0:未审批；1:审批完成；2:已删除
	 */
	public static final int SUBJECT_STATUS_READY = 0;
	public static final int SUBJECT_STATUS_PASS = 1;
	public static final int SUBJECT_STATUS_DELETE = 2;
	
	/*调用状态--成功**/
	public static final int STATUS_SUCCESS = 0;
	
	//个人积分查询类型 0-全部，1-获取,2-消耗
	public static final int MYPOINTS_ALL = 0;
	public static final int MYPOINTS_ADD = 1;
	public static final int MYPOINTS_COST = 2;
	
	/**
	 * 订单状态(从.net获取)
		-4 系统取消
		-2 客户取消
		-1 商家取消
		0 待审核:创建订单完成， 未付款
		1 处理中
		4 已出库
		10 客户拒收
		100完成
	*/
	public static final int ORDER_STATUS_CANCEL_SYS = -4;
	public static final int ORDER_STATUS_CANCEL_CUSTOMER=-2;
	public static final int ORDER_STATUS_CANCEL_MERCHANT = -1;
	public static final int ORDER_STATUS_UNPAY = 0;
	public static final int ORDER_STATUS_PROCESS = 1;
	public static final int ORDER_STATUS_OUTBOUND = 4;
	public static final int ORDER_STATUS_REFUSE=10;
	public static final int ORDER_STATUS_COMPLETE = 100;
	
	/*订单付款状态**/
	public static final int ORDER_PAY_STATUS_PAYED = 1;
	public static final int ORDER_PAY_STATUS_UNPAY = 0;
	
	/*订单列表页tab**/
	public static final int ORDER_LIST_TAB_COMPLETE = 0;
	public static final int ORDER_LIST_TAB_PAYED = 1;
	public static final int ORDER_LIST_TAB_UNPAY = 2;
	
	
	/*cms图片本地路径**/
	public static final String IMAGE_LOCAL_CMS = "/data/www/image/cms";
	/*site图片本地路径**/
	public static final String IMAGE_LOCAL_SITE = "/data/www/image/site";
	
	
	/** 权限判断用，登录时写入id数据的key */
	public static final String LOGIN_USER_ID_TAG_FOR_AUTH = "loginUserId";
}
