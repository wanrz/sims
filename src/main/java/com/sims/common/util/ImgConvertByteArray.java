package com.sims.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ImgConvertByteArray {

	private static final Logger logger = Logger.getLogger(ImgConvertByteArray.class);

	
	/**
	 * 二进制数据转图片
	 *
	 * @param imgData
	 * @param imgUrl
	 */
	public static void byteArray2Img(byte[] imgData, String imgUrl) {
		
		File image = new File(imgUrl);
		if (!image.getParentFile().exists()) {
			image.getParentFile().mkdirs();
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(image);
			fos.write(imgData);
		} catch (IOException e) {
			logger.error("保存图片异常",e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 图片转二进制数据
	 *
	 * @param imgUrl
	 * @return
	 */
	public static byte[] img2byteArray(String imgUrl) {

		FileInputStream fis = null;
		try {
			
			File file = new File(imgUrl);
			if (file.exists()) {
				int size = (int) file.length();
				byte[] img = new byte[size];
				
				fis = new FileInputStream(file);
				if ( fis != null)
				fis.read(img, 0, size);
				return img;
			}
		} catch (Exception e) {
			logger.error("获取图片数据异常",e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		
		return null;
	}
}
