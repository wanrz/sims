package com.sims.common.util;

import java.security.SecureRandom;
import java.util.UUID;

public class UUIDUtil {

	private UUIDUtil() {
		throw new IllegalAccessError("Utility class");
	}

	private static SecureRandom random = new SecureRandom();

	/**
	 * getUUID:获得一个UUID. <br/>
	 *
	 * @return String 返回一个UUID
	 * @since JDK 1.7
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @param number
	 *          int
	 * @return
	 */
	/**
	 * getUUID:获得指定数目的UUID. <br/>
	 *
	 * @param number
	 *          需要获得的UUID数量
	 * @return String[] UUID数组
	 * @since JDK 1.7
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return new String[0];
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		long nextLong = random.nextLong();
		return Math.abs(nextLong);
	}

}
