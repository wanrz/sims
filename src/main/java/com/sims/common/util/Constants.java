package com.sims.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sims.common.util.config.IConfig;

/**
 * ClassName:Constants <br/>
 * Description: 下面用到的一些常量. <br/>
 * Date: 2015年03月30日下午13:48:38 <br/>
 *
 * @author Jackson He
 * @version 1.0
 * @since JDK 1.7
 */

public class Constants {	
	
	/**
	 * PAGE_ERROR 页面错误
	 */
	public static final String PAGE_ERROR = "/html/error.html";

	/**
	 * PARAMETER_ERROR 参数错误
	 */
	public static final Integer PARAMETER_ERROR = 100;

	/**
	 * INSERT_FAILURED 添加失败.
	 */
	public static final Integer INSERT_FAILURED = -1;

	/**
	 * INSERT_FAILURED_WORKID_EXIST 数据已存在，添加失败.
	 */
	public static final Integer INSERT_FAILURED_DATA_EXIST = 3;

	/**
	 * INSERT_SUCCESSED 添加成功.
	 */
	public static final Integer INSERT_SUCCESSED = 1;

	/**
	 * DELETE_FAILURED 删除失败.
	 */
	public static final Integer DELETE_FAILURED = -1;

	/**
	 * DELETE_REF_FAILURED 因为存在关联表，删除失败.
	 */
	public static final Integer DELETE_FAILURED_REF = 3;

	/**
	 * DELETE_SUCCESSED 删除成功.
	 */
	public static final Integer DELETE_SUCCESSED = -3;

	/**
	 * DELETE_FAILURED_CHILD 因为存在子数据，删除失败.
	 */
	public static final Integer DELETE_FAILURED_CHILD = 2;

	/**
	 * UPDATE_FAILURED 更新失败.
	 */
	public static final Integer UPDATE_FAILURED = -1;

	/**
	 * UPDATE_SUCCESSED 更新成功.
	 */
	public static final Integer UPDATE_SUCCESSED = 1;


	public static final String USERDEFAULTKEY = "666666";
	
	/**
	 * DES加密  密钥
	 */
	public static final String DES_KEY = "C252E794520946CC934E21D2A65BF060";

	/**
	 * webservice模式
	 */
	public static final int WEBSERVICE = 1;

	/**
	 * socket模式
	 */
	public static final int SOCKET = 2;

	/**
	 * http模式
	 */
	public static final int HTTP = 3;

	/**
	 * socket服务器IP
	 */
	public static final String SOCKET_IP = "127.0.0.1";
	
	/**
	 * 表示接口参数需要加密 1
	 */
	public static final String PARAM_ISENCRYPT = "1";

	/**
	 * 与人脸识别算法服务器通信模式(http)
	 */
	public static final String FACE_SERVER_MODEL_HTTP = "http";
	
	/**
	 * 随机码编码
	 */
	public static final String RAND_CHECK_CODE = "randCheckCode";
	
	
	/**
	 * 新建人脸库
	 */
	public static final String BUSINESS_CODE_ADD_FACE = "addFaces";
	
	/**
	 * 证脸比对
	 */
	public static final String BUSINESS_CODE_CHECK_PERSON = "checkPerson";
	
	/**
	 * 脸脸对比
	 */
	public static final String BUSINESS_CODE_COMPARE_FACES = "compareFaces";
	
	/**
	 * 脸脸对比 （含1:N接口）
	 */
	public static final String BUSINESS_CODE_COMPARE_FACES1 = "compareFaces1";
	
	/**
	 * 按脸检索
	 */
	public static final String BUSINESS_CODE_SEARCH_BY_IMAGE = "searchCustByImg";
	
	/**
	 * 按证检索
	 */
	public static final String BUSINESS_CODE_SEARCH_BY_INFO = "searchCustByInfo";
	
	/**
	 * 图片审核通知
	 */
	public static final String BUSINESS_CODE_POST_AUDIT_RESULT = "postAuditResult";
	
	/**
	 * 预警名单确认接口
	 */
	public static final String BUSINESS_CODE_WARNING_CONFIRM = "confirmWarning";
	
	/**
	 * 添加水印
	 */
	public static final String BUSINESS_CODE_ADD_WATERMARK = "addwatermark";
	
	/**
	 * 验证水印
	 */
	public static final String BUSINESS_CODE_VALIDATE_WATERMARK = "validatewatermark";
	
	/**
	 * 图像压缩
	 */
	public static final String BUSINESS_CODE_COMPRESS = "imageCompress";
	
	/**
	 * 图片特征比对
	 */
	public static final String BUSINESS_CODE_SIMBYFEATURE = "simByFeature";
	
	/**
	 * 提取特征
	 */
	public static final String BUSINESS_CODE_FEATURE = "feature";
	
	/**
	 * 人脸检测
	 */
	public static final String BUSINESS_CODE_DETECT = "detect";
	
	public static final String DEFAULT_INTERFACE_TYPE = "0799";
	
	public static final String VER_OLD = "v0";
	
	public static final String VER_NEW = "v1";
	
	//策略类型
	public static final long STEP_TYPE = 6L;
	
	//后端hack编码
	public static final String HACK_CODE = "0666";
	
	//后端hack标识
	public static final String HACK_FLAG_OPEN = "true";
	public static final String HACK_FLAG_CLOSE = "false";
	
	// 工作流变量
	public static List<String> PARENT_GROUP = null;
	/**
	 * 接口类型枚举
	 * @author zhuyf
	 *
	 */
	public static Map<String,String> INTERFACE_TYPE_MAP = new HashMap<String,String>();
	
	static {
		INTERFACE_TYPE_MAP.put("compareFaces", "0700");
		INTERFACE_TYPE_MAP.put("compareFaces1", "0701");
	}
	
	public interface Config {
		public static final String DOMAIN_WWW = IConfig.getProperty("domain.www");
		public static final String FILE_STORAGE_WAY = IConfig.getProperty("file.storage.way");
		public static final String IBIS_FILE_PATH = IConfig.getProperty("ibis.file.path");
		public static final String BIOLOGY_SIZE = IConfig.getProperty("file.biology.size");
	}
	
}
