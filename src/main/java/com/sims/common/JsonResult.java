package com.sims.common;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;

/**
 * JSON Response Basic data
 *
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer",
		"javassistLazyInitializer" })
public class JsonResult {

	private boolean success = true;

	private String code = "";

	private String message = "";

	private Map<Object, Object> data = Maps.newHashMap();

	public JsonResult() {
		super();
	}

	public JsonResult(boolean success) {
		super();
		this.success = success;
	}
	
	public JsonResult(boolean success,String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public JsonResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
		this.success = false;
	}

	public static JsonResult success(String message){
        JsonResult jsonResult = new JsonResult(true, message);
        return jsonResult;
	}

	public void appendData(Map<?, ?> map) {
		this.data.putAll(map);
	}

	public void appendData(Object key, Object value) {
		this.data.put(key, value);
	}

	public String getCode() {
		return code;
	}

	public Map<Object, Object> getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
