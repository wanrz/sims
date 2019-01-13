package com.sims.common.util;

import java.util.Collection;

import com.sims.common.exception.CheckException;



/**
 * 校验工具类
 *
 * @author liaoyanbo
 * @Date 2017-12-26 17:06
 * @since 1.6
 */
public class CheckUtil {
	
    private CheckUtil() {
		super();
	}
    
	public static void check(boolean condition, String msg, Object... args) {
        if (!condition) {
            fail(msg, args);
        }
    }
    /**
     * 
     * <p>
     * Title: fail
     * </p>  
     * <p>
     * Description:校验失败
     * </p>  
     * @param msg
     * @param args
     */
    private static void fail(String msg, Object... args) {
        throw new CheckException(String.format(msg, args));
    }
    /**
     * 
     * <p>
     * Title: checkIn
     * </p>  
     * <p>
     * Description: 校验是否包含
     * </p>  
     * @param obj
     * @param objs
     * @param msg
     * @param args
     */
    public static <T> void checkIn(T obj, Collection<T> objs, String msg, Object... args) {
        check(objs.contains(obj), msg, args);
    }
    /**
     * 
     * <p>
     * Title: notEmpty
     * </p>  
     * <p>
     * Description:非空校验 
     * </p>  
     * @param str
     * @param msg
     * @param args
     */
    public static void notEmpty(String str, String msg, Object... args) {
        check(str != null && str.length() > 0, msg, args);
    }

    /**  
     * <p>
     * Title: notEmpty
     * </p>  
     * <p>
     * Description: 非空校验 
     * </p>  
     * @param arr
     * @param msg
     * @param args  
     */  
    public static <T> void notEmpty(T[] arr, String msg, Object... args) {
        check(arr != null && arr.length > 0, msg, args);
    }

    /**  
     * <p>
     * Title: notEmpty
     * </p>  
     * <p>
     * Description: 非空校验 
     * </p>  
     * @param col
     * @param msg
     * @param args  
     */  
    public static <T> void notEmpty(Collection<T> col, String msg, Object... args) {
        check(col != null && !col.isEmpty(), msg, args);
    }
}
