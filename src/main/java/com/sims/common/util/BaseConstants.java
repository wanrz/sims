package com.sims.common.util;

import java.util.ResourceBundle;

/**
 * ClassName:BaseConstants <br/>
 * Description: 系统常量 <br/>
 * Date: 2015年4月10日 下午2:41:16 <br/>
 *
 * @version
 * @see
 */
public class BaseConstants {
	/**
	 * 获取属性值. <br/>
	 * @param key
	 * @return
	 */
	public static final String getConstantsValue(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

	/**
	 * resourceBundle base-config属性文件.
	 */
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle("spring/base-config");

	/**
	 * downloadFilepath 下载文件的相对路径
	 */
	public static final String DOWNLOAD_OR_UPLOAD_FILEPATH = "WEB-INF\\classes\\";

	/**
	 * USER:用户信息
	 */
	public static final String USER = "user";

	/**
	 * USERBIGINFO:用户全部信息
	 */
	public static final String USERBIGINFO = "UserBigInfo";

	/**
	 * time:主页面时间
	 */
	public static final String TIME = "time";

	/**
	 * 数组:系统数字常量 参数错误: NUMFLAG[0] 失败: NUMFLAG[1] 成功: NUMFLAG[2] 存在表关联:NUMFLAG[3]
	 * 存在子数据: NUMFLAG[4] 已审批过，不需要再次审批NUMFLAG[5] 条件不符合: NUMFLAG[6]
	 * 当年预算有申请过，如需修改，请到预算调整中操作NUMFLAG[7] 预算数据不合理，请重新设定NUMFLAG[8]
	 */
	public static final Integer[] NUMFLAG = { -1, 0, 1, 2, 3, 4, 5, 6, 7 };

	/**
	 * SPLIT_COMMA:截取字符串:"."
	 */
	public static final String SPLIT_COMMA = "\\.";

	/**
	 * REPLACE_SERVICE:替换字符串：Service
	 */
	public static final String REPLACE_SERVICE = "Service";

	/**
	 * REPLACE_MAPPER:替换字符串：Mapper
	 */
	public static final String REPLACE_MAPPER = "Mapper";

	/**
	 * REPLACE_EMPTY:替换字符串：""
	 */
	public static final String REPLACE_EMPTY = "";

	/**
	 * TO_START_DATE:开始日期
	 */
	public static final String TO_START_DATE = "toStartDate";

	/**
	 * TO_END_DATE:结束日期
	 */
	public static final String TO_END_DATE = "toEndDate";

	/**
	 * BETWEEN_DATE:介于开始日期和结束日期之间
	 */
	public static final String BETWEEN_DATE = "betweenDate";

	/**
	 * DATE:普通日期
	 */
	public static final String DATE = "Date";

	/**
	 * FIELD_NAME:字段名称
	 */
	public static final String FIELD_NAME = "fieldName";

	/**
	 * PAGE:分页对象
	 */
	public static final String PAGE = "page";

	/**
	 * OBJ:实体对象
	 */
	public static final String OBJ = "obj";

	/**
	 * UNLOGIN:用户未登录
	 */
	public static final String UNLOGIN = "UNLOGIN";

	/**
	 * VALIDATE_CODE : 验证码
	 */
	public static final String VALIDATE_CODE = "validateCode";

	/**
	 * WORK_CODE : 登录账户
	 */
	public static final String WORK_CODE = "workCode";

	/**
	 * VALIDATE_CODE : 登录错误
	 */
	public static final String LOGIN_ERROR = "{\"result\":\"0\",\"msg\":\"登录名或密码错误\"}";

	/**
	 * VALIDATE_CODE : 用户名没找到
	 */
	public static final String USER_NOFOUND = "{\"result\":\"0\",\"msg\":\"登录名或密码错误\"}";

	/**
	 * LOGIN_SUCCESS : 登录成功
	 */
	public static final String LOGIN_SUCCESS = "{\"result\":\"1\",\"msg\":\"login/index\"}";

	/**
	 * LOGIN_MULTI_EXCEPTION : 登录用户异常
	 */
	public static final String LOGIN_MULTI_EXCEPTION = "{\"result\":\"0\",\"msg\":\"记录重复，请与管理员联系\"}";

	/**
	 * PASSWORD_ERROR : 密码错误
	 */
	public static final String PASSWORD_ERROR = "{\"result\":\"0\",\"msg\":\"登录名或密码错误\"}";
	
	/**
	 * 水印图片不存在
	 */
	public static final String WATER_IMG_ERROR = "{\"result\":\"0\",\"msg\":\"水印图片不存在\"}";
	
	/**
	 * VALIDATE_ERROR : 验证码错误
	 */
	public static final String VALIDATE_ERROR = "{\"result\":\"0\",\"msg\":\"验证码错误\"}";
	
	/**
	 * NULL_UPLOADIMG_ERROR : 上传图片为空
	 */
	public static final String NULL_UPLOADIMG_ERROR = "{\"code\":\"-1\",\"message\":\"上传图片为空\"}";
	
	/**
	 * GROUP_FACE_ADD_ERROR : 组内添加人脸失败
	 */
	public static final String GROUP_FACE_ADD_ERROR = "{\"code\":\"-1\",\"message\":\"组内添加人脸失败\"}";
	
	/**
	 * SYSTEM_EXCEPTION : 系统运行时失败异常扑捉
	 */
	public static final String SYSTEM_EXCEPTION = "{\"code\":\"-1\",\"message\":\"系统异常,请与管理员联系\"}";

	/**
	 * LOGIN_MAIN : 系统主页跳转方法
	 */
	public static final String LOGIN_INDEX = "platform/index";

	/**
	 * LOGIN_HTML : 系统主页面
	 */
	public static final String LOGIN_HTML = "platform/login";

	/**
	 * TO_LOGIN : 跳转至登录页面
	 */
	public static final String TO_LOGIN = "/login/toLogin";

	/**
	 * ROOT_RESOURCE : 根节点
	 */
	public static final String ROOT_RESOURCE = "01";

	/**
	 * USER_ORG:用户组织信息
	 */
	public static final String USER_ORG = "org";

	/**
	 * USER_PARENT_ORG:用户组织信息
	 */
	public static final String USER_PARENT_ORG = "parentOrg";

	/**
	 * 单据状态
	 *
	 * 新建: BILL_STATUS[0] 提交审批: BILL_STATUS[1] 审批通过: BILL_STATUS[2] 审批拒绝:
	 * BILL_STATUS[3] 修改申请: BILL_STATUS[4]
	 */
	public static final Integer[] BILL_STATUS = { 1, 2, 3, 4, 5 };

	/**
	 * 凭证状态
	 *
	 * [0]:新建, 拟定: VOUCHER_STATUS[0] [1]最终: VOUCHER_STATUS[1]
	 */
	public static final Integer[] VOUCHER_STATUS = { 1, 2 };

	/**
	 * 凭证状态
	 *
	 * 0：预算: FROM_TYPE[0] 1：报销: FROM_TYPE[1]
	 */
	public static final Integer[] FROM_TYPE = { 1, 2 };

	/**
	 * 预算类型
	 *
	 * BILL_TYPE[0]:全行年度预算初始， 
	 * BILL_TYPE[1]:全行年度预算分解，
	 *  BILL_TYPE[2]:分行年度预算下拨，
	 * BILL_TYPE[3]:分行年度预算上划全行年度预算, BILL_TYPE[4]:分行执行分解,
	 * BILL_TYPE[5]:分行执行预算上划至分行年度预算, BILL_TYPE[6]:支行执行预算上划至分行执行预算，
	 * BILL_TYPE[7]:支行执行预算调整, BILL_TYPE[8]:分行年度预算调整, BILL_TYPE[9]:全行年度预算调整,
	 * BILL_TYPE[10]：个人， BILL_TYPE[11]：部门）
	 */
	public static final Integer[] BILL_TYPE = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			11, 21 };

	/**
	 * 供应商是否失效 0：有效 1：失效
	 */
	public static final Integer[] DISABLE_FLAG = { 1, 2 };

	/*
	 * 余额控制方向(1:dr, 2:cr, 3:drbg, 4:crbg)
	 */
	public static final Integer[] DR_CR = { 1, 2, 3, 4 };
	
	/*
	 * 比对策略常量,RECOG_STEP[0] 最大人脸比对策略   RECOG_STEP[1] 中间区域靠下的人脸比对策略
	 */
	public static final String[] RECOG_STEP = {"0600","0601"};

}
