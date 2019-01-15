package com.sims.common.exception;

/**
 * 校验异常
 *
 * @Date 2017-12-26 17:10
 * @since 1.6
 */
public class CheckException extends RuntimeException {
   
	private static final long serialVersionUID = -1091855491654829991L;

	public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

}
