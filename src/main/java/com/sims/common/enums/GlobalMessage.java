package com.sims.common.enums;

public enum GlobalMessage {

	  RESP_SUCESS("200", "请求已完成"), RESP_FAIL("500", "因为意外情况，服务器不能完成请求"), RESP_NOT_AUTH("400",
	      "请求无效"), RESP_FORBIDDEN("403",
	          "没有权限访问"), RESP_NOLOGIN("413", "没有登录！"), RESP_NO_RESOURCE("404", "没有资源");

	  private String respCode;
	  private String respDesc;

	  private GlobalMessage(String respCode, String respDesc) {
	    this.respCode = respCode;
	    this.respDesc = respDesc;
	  }

	  public String getRespCode() {
	    return respCode;
	  }

	  public String getRespDesc() {
	    return respDesc;
	  }
	}
