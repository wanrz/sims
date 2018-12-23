package com.sims.common;

/**
 * 获取单个实体的Json Response
 *
 * @param <T>
 */
public class JsonEntityResult<T> extends JsonResult {

	private T entity = null;

	public JsonEntityResult() {
		super();
	}

	public JsonEntityResult(T entity) {
		super();
		this.entity = entity;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
