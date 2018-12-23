package com.sims.common.exception;

/**
 * ClassName:DataException <br/>
 * Description: 数据异常类类 <br/>
 * Date:     2015年5月16日 上午11:41:41 <br/>
 * @author   Jackson He
 * @version
 * @see
 */

public class DataException extends ServiceException {
	private static final long serialVersionUID = 4341895506977006786L;
	
	public DataException() {
		super();
	}
	
	public DataException(String errorMessage) {
		super(errorMessage);
	}
}
