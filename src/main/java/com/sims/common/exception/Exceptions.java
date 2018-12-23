package com.sims.common.exception;

/**
 * @author liaoyanbo
 * @Date 2017-12-19 20:08
 * @since 1.6
 */
public class Exceptions {
    private static final String UNKNOWN_ERROR = "未知错误";
    public static String resolveErrorMessage(Exception e){
        if(e instanceof BaseServiceException){
            return e.getMessage();
        }else if(e instanceof ServiceException){
            return ((ServiceException) e).getMessage();
        }
        return UNKNOWN_ERROR;
    }
}
