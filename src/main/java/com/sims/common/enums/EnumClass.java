package com.sims.common.enums;

/**
 * 枚举类
 *
 */
public class EnumClass {

	/**
	 * 加密类型枚举
	 *
	 */
	public enum EncryptTypeEnum {
		
		NORMAL("1"),
		DES("2"),
		AES("3"),
		SM4("4");		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		EncryptTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 缓存类型枚举
	 *
	 */
	public enum CacheTypeEnum {
		
		REDIS("jedisCache"),
		LOCAL("localCache"),
		NO("noCache");		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		CacheTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 通信协议类型枚举
	 */
	public enum ProtocolTypeEnum {
		
		HTTP("http"),
		WEBSERVICE("webservice"),
		SOCKET("socket");		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		ProtocolTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 数据类型枚举
	 */
	public enum DataTypeEnum {
		
		XML("xml"),
		JSON("json"),
		OTHER("other"),;		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		DataTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 字典类型枚举
	 */
	public enum DicTypeEnum {
		
		/**
		 * 引擎类型
		 */
		ENGINE_TYPE("5"),
		/**
		 * 交易代码
		 */
		TRADING_CODE("6"),
		/**
		 * 渠道
		 */
		CHANNEL("3"),
		/**
		 * 监控项
		 */
		MONITOR("10"),
		/**
		 * 报警类型
		 */
		ALARM("11")
		;			
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		DicTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}	
	
	/*
	 * 文件存取方式枚举
	 */
	public enum FileAccessEnum {
		
		/**
		 * 本地
		 */
		LOCAL("local"),
		/**
		 * http
		 */
		HTTP("http"),
		/**
		 * https
		 */
		HTTPS("https");			
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		FileAccessEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 图片高清，水印枚举
	 */
	public enum FileTypeEnum {
		
		/**
		 * 高清或者原始
		 */
		HDTV(1),
		
		/**
		 * 视频
		 */
		VIDEO(3),
		
		/**
		 * 水印
		 */
		WATERMARK(2);
			
		
		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		FileTypeEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	/*
	 * 审批状态类型枚举
	 */
	public enum DicCheckTypeEnum {
		
		/**
		 * 审批状态：通过
		 */
		CHECK_STATUS_PASS("1"),	
		
		/**
		 * 审批状态：不通过
		 */
		CHECK_STATUS_NO_PASS("0"),
		
		/**
		 * 审批状态：待审核
		 */
		CHECK_STATUS_WAITING("2");
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		DicCheckTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 联网核查状态枚举
	 */
	public enum NetCheckStatusEnum {
		
		/**
		 * 是
		 */
		YES(1),
		/**
		 * 否
		 */
		NO(0);
			
		
		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		NetCheckStatusEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 联网核查渠道
	 */
	public enum NetCheckChannelEnum {
		
		/**
		 * 云从联网核查渠道
		 */
		PBC("0100"),
		/**
		 * 其他核查渠道
		 */
		PSB("0101");
		
		
		private final String value;
		
		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		NetCheckChannelEnum(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 存库类型枚举
	 */
	public enum LibTypeEnum {
		
		/**
		 * 特征库
		 */
		LIB_TYPE("lib"),
		/**
		 * 流水库
		 */
		FLOW_TYPE("flow"),
		/**
		 * 联网核查挡板库
		 */
		NETWORK_BAFFLE("network");
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		LibTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 是否开启联网核查挡板枚举
	 */
	public enum NetworkBaffleCheckEnum {
		
		ON("1"),
		OFF("2");		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		NetworkBaffleCheckEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	} 
	
	/*
	 * 联网核查状态枚举
	 */
	public enum NetworkCheckCodeEnum {
		
		TIMEOUT("timeout"),
		SUCCESS("success"),
		FAIL("fail"),;		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		NetworkCheckCodeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 操作类型枚举
	 */
	public enum DicOptTypeEnum {
		
		/**
		 * 操作类型：新增
		 */
		OPERATE_STATUS_NEW("1"),	
		
		/**
		 * 操作类型：修改
		 */
		OPERATE_STATUS_MODIFY("2"),
		
		/**
		 * 操作类型：删除
		 */
		OPERATE_STATUS_DELETE("3");
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		DicOptTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	
	/*
	 * 状态枚举
	 */
	public enum StatusEnum {
		
		/**
		 * 是
		 */
		YES(1),
		/**
		 * 否
		 */
		NO(0);
			
		
		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		StatusEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/*
	 * 服务接口枚举
	 */
	public enum InterFaceEnum {
		/**
		 * 注册
		 */
		REG("reg"),
		/**
		 * 证特征比对
		 */
		CHECK_PERSON("checkPerson"),
		/**
		 * 两证特征比对
		 */
		CHECK_PERSON_EX("checkPersonEx"),
		/**
		 * 特征比对
		 */
		COMPARE("compare"),
		/**
		 * 按照客户信息检索
		 */
		SEARCH_BY_PERSON("searchByPerson"),
		/**
		 * 按照图片信息检索
		 */
		SEARCH_BY_IMG("searchByImg"),
		/**
		 * 身份证识别
		 */
		OCR_IDCARD("ocrIDCard"),
		/**
		 * 活体检测
		 */
		CHECK_LIVENESS("checkLiveness"),
		/**
		 * 银行卡识别
		 */
		OCR_BANK_CARD("ocrBankCard"),
		
		/**
		 * 唇语检测
		 */
		LIP_READ("lipRead"),
		
		/**
		 * 营业执照识别
		 */
		OCR_BLIC_CARD("blic");		
		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		InterFaceEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 维尔指纹接口枚举
	 */
	public enum EllcomFingerprintEnum {
		/**
		 * 注册
		 */
		REG("ellcomReg"),
		
		/**
		 * 指指比对
		 */
		COMPARE("ellcomCompare"),
		
		/**
		 * 证指比对
		 */
		CHECK_PERSON("ellcomCheck");

		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		EllcomFingerprintEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 曙光指静脉接口枚举
	 */
	public enum SgFingerveinEnum {
		/**
		 * 注册
		 */
		REG("veinReg"),
		
		/**
		 * 指指比对
		 */
		COMPARE("veinCompare"),
		
		/**
		 * 证指比对
		 */
		CHECK_PERSON("veinCheck");

		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		SgFingerveinEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/*
	 * 日立指静脉接口枚举
	 */
	public enum HitachiFingerveinEnum {
		/**
		 * 注册
		 */
		REG("hitachiReg"),
		
		/**
		 * 指指比对
		 */
		COMPARE("hitachiCompare"),
		
		/**
		 * 证指比对
		 */
		CHECK_PERSON("hitachiCheck");

		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		HitachiFingerveinEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	
	/*
	 * 图片名称枚举
	 */
	public enum ImgNameEnum {
		/**
		 * 人脸图片
		 */
		FACE_IMG("faceImg"),
		/**
		 * 证件照
		 */
		IDCARD_IMG("idcardImg"),
		/**
		 * 联网核查图片
		 */
		NET_CHECK_IMG("netCheckImg"),
		/**
		 * 身份证正面照
		 */
		FRONT_IMG("frontImg"),
		/**
		 * 身份证反面照
		 */
		BLACK_IMG("backImg"),
		/**
		 * 银行卡照片
		 */
		BANK_IMG("bankImg"),
		
		/**
		 * 营业执照照片
		 */
		BLIC_IMG("blicImg");		
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		ImgNameEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
		
	/*
	 * 身份证正反面枚举
	 */
	public enum IDCardTypeEnum {
		
		/**
		 * 正面
		 */
		FRONT(1),
		/**
		 * 反面
		 */
		BLACK(0),
		/**
		 * 正反面
		 */
		ALL(2);
			
		
		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		IDCardTypeEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/*
	 * 指静脉特征名称枚举
	 */
	public enum FingerveinNameEnum {
		/**
		 * 注册特征
		 */
		REGIST("registFingervein"),
		
		/**
		 * 验证特征
		 */
		VERIFY("verifyFingervein");

		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		FingerveinNameEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 监控交易类型枚举
	 */
	public enum MonitorTradeTypeEnum {
		/**
		 * 交易总量
		 */
		TRADE_SUM("1201"),
		
		/**
		 * 交易成功
		 */
		TRADE_SUCCESS("1202"),
		
		/**
		 * 交易失败
		 */
		TRADE_FAIL("1203"),
		
		/**
		 * 平均响应时间
		 */
		TRADE_TIME("1204");

		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		MonitorTradeTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	/*
	 * 流水保存方式枚举
	 */
	public enum FlowSaveWayEnum {
		/**
		 * 同步
		 */
		SYNC("1"),
		
		/**
		 * 异步
		 */
		ASYNC("2");

		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		FlowSaveWayEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	
	/**
	 * 引擎类型枚举
	 * @author zhuyf
	 *
	 */
	public enum EngineTypeEnum {
		
		FACE("0500"),
		FINGERVEIN("0502"),
		FINGERPRINT("0501");	
		
		private final String value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		EngineTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}		
}
