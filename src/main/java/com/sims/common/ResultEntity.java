package com.sims.common;

import com.sims.common.enums.GlobalMessage;

public class ResultEntity {

	  /**
	   * respCode:应答码.
	   */
	  private String respCode;
	  /**
	   * respDesc:应答描述.
	   */
	  private String respDesc;
	  /**
	   * data:返回body真正对象，报错或者没有结果则为空.
	   */
	  private Object data;

	  public ResultEntity buildData(Object data) {
	    setData(data);
	    return this;
	  }

	  public ResultEntity buildGlobalMessage(GlobalMessage message) {
	    return buildRespCode(message.getRespCode()).buildRespDesc(message.getRespDesc());
	  }

	  public ResultEntity buildRespCode(String respCode) {
	    setRespCode(respCode);
	    return this;
	  }

	  public ResultEntity buildRespDesc(String respDesc) {
	    setRespDesc(respDesc);
	    return this;
	  }

	  public Object getData() {
	    return data;
	  }

	  public String getRespCode() {
	    return respCode;
	  }

	  public String getRespDesc() {
	    return respDesc;
	  }

	  public void setData(Object data) {
	    this.data = data;
	  }

	  public void setRespCode(String respCode) {
	    this.respCode = respCode;
	  }

	  public void setRespDesc(String respDesc) {
	    this.respDesc = respDesc;
	  }

	}

