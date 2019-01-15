package com.sims.common.exception;

/**
 * 基本异常
 * @Date 2017-12-19 20:05
 * @since 1.6
 */
public class BaseServiceException extends RuntimeException {
    
	private static final long serialVersionUID = 1865681849808053725L;

	public BaseServiceException() {
        super();
    }

    public BaseServiceException(String message) {
        super(message);
    }

    public BaseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseServiceException(Throwable cause) {
        super(cause);
    }

}
