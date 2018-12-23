package com.sims.common.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.sims.common.annotation.ClassName;
import com.sims.common.annotation.FieldName;

public abstract class ObjectUtils extends org.apache.commons.lang3.ObjectUtils{

	protected static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class); 

	/**
	 * 获取当前服务器的ip
	 * @return
	 */
	public static String getServerIp() {
		
		//当前服务器ip
		String hostAddress = "";
		
		try {
			
			InetAddress ia=InetAddress.getLocalHost();
			String ipv4 = ia.getHostAddress();
	
			Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
			while (eni.hasMoreElements()) {
				NetworkInterface networkCard = eni.nextElement();
				List<InterfaceAddress> ncAddrList = networkCard.getInterfaceAddresses();
				Iterator<InterfaceAddress> ncAddrIterator = ncAddrList.iterator();
				while (ncAddrIterator.hasNext()) {
					InterfaceAddress networkCardAddress = ncAddrIterator.next();
					InetAddress address = networkCardAddress.getAddress();
					if (!address.isLoopbackAddress()) {
						hostAddress = address.getHostAddress();
						if(hostAddress.equals(ipv4)){
							return hostAddress;
						}						
				    }
				}
			}
		} catch(Exception e) {	
			logger.error("获取主机IP异常",e);
		}
		
		return hostAddress;
	}
	
	/**
	 * 将javaBean对象转化成map对象，并返回
	 *
	 * @param source
	 *            - javaBean对象
	 * @return 返回map对象
	 */
	public static Map<String, Object> beanToMap(Object source) {
		Map<String, Object> target = new HashMap<String, Object>();
		beanToMap(source, target);
		return target;
	}

	/**
	 * 将javaBean对象转化成map对象
	 *
	 * @param source
	 *            - javaBean对象
	 * @param target
	 *            - map对象
	 */
	public static void beanToMap(Object source, Map<String, Object> target) {
		beanToMap(source, target, null);
	}

	/**
	 * Title:
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param source
	 *            - javaBean对象
	 * @param target
	 *            - map对象
	 * @param ignoreProperties
	 *            - 过滤属性
	 */
	public static void beanToMap(Object source, Map<String, Object> target,
			String[] ignoreProperties) {
		try {
			Class<?> clazz = source.getClass();
			PropertyDescriptor[] propertyDescriptors = BeanUtil
					.getPropertyDescriptors(clazz, ignoreProperties);
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				Method getter = property.getReadMethod();
				target.put(key, getter.invoke(source, new Object[0]));
			}
		} catch (Exception e) {
			throw new RuntimeException("转换失败！");
		}
	}

	/**
	 * 将javaBean对象转化成map对象，排除指定key，并返回
	 *
	 * @param source
	 *            - javaBean对象
	 * @param ignoreProperties
	 *            - 过滤属性
	 * @return 返回map对象
	 */
	public static Map<String, Object> beanToMap(Object source,
			String[] ignoreProperties) {
		Map<String, Object> target = new HashMap<String, Object>();
		beanToMap(source, target, ignoreProperties);
		return target;
	}

	/**
	 *
	 * Title:创建一个唯一的uuid
	 * <p>
	 * Description:创建一个唯一的uuid
	 * </p>
	 *
	 * @return
	 */
	public static String createUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "").toLowerCase();
	}

	/**
	 *
	 * Title:根据类字段名称获取field
	 * <p>
	 * Description:根据类字段名称获取field
	 * </p>
	 *
	 * @param c
	 *            类class
	 * @param name
	 *            字段名称
	 * @return Field
	 * @author zhuyf
	 */
	public static Field getFieldByName(Class<?> c, String name) {
		Field f = null;
		try {
			f = c.getDeclaredField(name);
		} catch (Exception e) {
			f = null;
		}
		return f;
	}

	/**
	 *
	 * Title:获取对象中，指定字段的值
	 * <p>
	 * Description:获取对象中，指定字段的值
	 * </p>
	 *
	 * @param obj
	 *            对象
	 * @param f
	 *            字段对象
	 * @return Object
	 * @author zhuyf
	 */
	public static Object getFieldValue(Object obj, Field f) {
		Object retValue = null;
		f.setAccessible(true);
		try {
			retValue = f.get(obj);
		} catch (Exception e) {
			retValue = null;
		}
		return retValue;
	}
	
	/**
	 * 获取目标对象属性为name的值
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getFieldValue(Object obj, String name) {
		//返回值
		Object retValue = null;
		//获取属性对象
		Field f = getFieldByName(obj.getClass(),name);
		if(f == null) return retValue;		
		f.setAccessible(true);
		try {
			retValue = f.get(obj);
		} catch (Exception e) {
			retValue = null;
		}
		return retValue;
	}
	
	/**
	 * 设置目标对象属性为name的值
	 * @param obj 目标对象
	 * @param name 属性名称
	 * @param value 属性值
	 * @return
	 */
	public static void setFieldValue(Object obj, String name,Object value) {		
		//获取属性对象
		Field f = getFieldByName(obj.getClass(),name);
		if(f == null) return;		
		f.setAccessible(true);
		try {
			f.set(obj, value);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 获取对象别名
	 * @param obj
	 * @return
	 */
	public static String getClassAliasName(Class<?> classz) {
		//类别名
		String classAliasName = "";
		if(classz == null) return classAliasName;
		ClassName className = classz.getAnnotation(ClassName.class);
		if(className != null) {
			return className.name();
		}
		return classAliasName;
	}
	
	/**
	 * 获取对象字段别名
	 * @param obj
	 * @return
	 */
	public static Map<String,String> getFieldAliasName(Class<?> classz) {
		//字段别名
		Map<String,String> fieldMap = Maps.newHashMap();
		if(classz == null) return fieldMap;
		Field[] fields = classz.getDeclaredFields();
		if(fields == null || fields.length < 1) return fieldMap;
		for(Field field:fields) {
			FieldName filedName = field.getAnnotation(FieldName.class);
			if(filedName != null) {
				fieldMap.put(field.getName(), filedName.name());
			}
		}
		return fieldMap;
	}

	/**
	 *
	 * Title:判断对象，集合，map，字符串是否为空，为空返回true
	 * <p>
	 * Description:判断对象，集合，map，字符串是否为空，为空返回true
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @author zhuyf
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof String) && obj.toString().trim().equals("")) {
			return true;
		}
		if ((obj instanceof Object[]) && ((Object[])obj).length < 1) {
			return true;
		}
		if ((obj instanceof Collection<?>) && ((Collection<?>) obj).size() < 1) {
			return true;
		}
		if ((obj instanceof Map<?, ?>) && ((Map<?, ?>) obj).size() < 1) {
			return true;
		}
		return false;
	}

	/**
	 * 将map对象转化成javaBean对象
	 *
	 * @param source
	 *            - map对象
	 * @param target
	 *            - javaBean对象
	 */
	public static void mapToBean(Map<String, Object> source, Object target) {
		mapToBean(source, target, null);
	}

	/**
	 * 将map对象转化成javaBean对象
	 *
	 * @param source
	 *            - map对象
	 * @param target
	 *            - javaBean对象
	 * @param ignoreProperties
	 *            - 过滤属性
	 */
	public static void mapToBean(Map<String, Object> source, Object target,
			String[] ignoreProperties) {
		try {
			Class<?> clazz = target.getClass();
			PropertyDescriptor[] propertyDescriptors = BeanUtil
					.getPropertyDescriptors(clazz, ignoreProperties);
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!source.containsKey(key)) {
					continue;
				}
				Object value = source.get(key);
				Method setter = property.getWriteMethod();
				value = ConvertUtils.convert(value, property.getPropertyType());
				setter.invoke(target, value);
			}
		} catch (Exception e) {
			throw new RuntimeException("转换失败！", e);
		}
	}

	/**
	 *
	 * Title:将对象转成byte
	 * <p>
	 * Description:将对象转成byte
	 * </p>
	 *
	 * @param obj
	 *            对象
	 * @param defaultValue
	 *            缺省值
	 * @return
	 * @author zhuyf
	 */
	public static byte objToByte(Object obj, int defaultValue) {
		byte value = (byte) defaultValue;
		try {
			value = Byte.parseByte(obj.toString());
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 *
	 * Title:将对象转成int
	 * <p>
	 * Description:将对象转成int
	 * </p>
	 *
	 * @param obj
	 *            对象
	 * @param defaultValue
	 *            缺省值
	 * @return
	 * @author zhuyf
	 */
	public static int objToInt(Object obj, int defaultValue) {
		int value = defaultValue;
		try {
			value = Integer.parseInt(obj.toString());
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 *
	 * Title:将对象转成long
	 * <p>
	 * Description:将对象转成long
	 * </p>
	 *
	 * @param obj
	 *            对象
	 * @param defaultValue
	 *            缺省值
	 * @return
	 * @author zhuyf
	 */
	public static long objToLong(Object obj, long defaultValue) {
		long value = defaultValue;
		try {
			value = Long.parseLong(obj.toString());
		} catch (Exception e) {
		}
		return value;
	}
	/**
	 *
	 * Title:将对象转成double
	 * <p>
	 * Description:将对象转成double
	 * </p>
	 *
	 * @param obj
	 *            对象
	 * @param defaultValue
	 *            缺省值
	 * @return
	 * @author zhuyf
	 */
	public static double objToDouble(Object obj, double defaultValue) {
		double value = defaultValue;
		try {
			value = Double.parseDouble(obj.toString());
		} catch (Exception e) {
		}
		return value;
	}
	
	/**
	 * 从输入流中获取字节数据
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len=inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();		
	}
	
	/**
	 * 读取输入流
	 * @return
	 * @throws Exception 
	 */
	public static byte[] readSocketInputStream(InputStream in,int head) throws Exception {
		if(in == null) return null;
		//报文头长度
		byte[] headdata = new byte[head];
		int readLength = in.read(headdata);		
		int datalength = Integer.valueOf(new String(Arrays.copyOfRange(headdata, 0, readLength)));
		if(datalength < 1) return null;
		
		byte[] b = new byte[datalength];
		int readCount = 0; // 已经成功读取的字节的个数
		while (readCount < datalength) {
		   readCount += in.read(b, readCount, datalength-readCount);
		}
		return b;
	}

	private ObjectUtils() {
	}
	
	/**
	   * 注解到对象复制，只复制能匹配上的方法。
	   *
	   * @param annotation
	   * @param object
	   */
	  public static void annotationToObject(Object annotation, Object object) {
	    if (annotation != null) {
	      Class<?> annotationClass = annotation.getClass();
	      Class<?> objectClass = object.getClass();
	      for (Method m : objectClass.getMethods()) {
	        if (StringUtils.startsWith(m.getName(), "set")) {
	          try {
	            String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
	            Object obj = annotationClass.getMethod(s).invoke(annotation);
	            if (obj != null && !"".equals(obj.toString())) {
	              if (object == null) {
	                object = objectClass.newInstance();
	              }
	              m.invoke(object, obj);
	            }
	          } catch (Exception e) {
	            // 忽略所有设置失败方法
	          }
	        }
	      }
	    }
	  }

	  /**
	   * 序列化对象
	   *
	   * @param object
	   * @return byte[]
	   */
	  public static byte[] serialize(Object object) {
	    ObjectOutputStream oos = null;
	    ByteArrayOutputStream baos = null;
	    try {
	      if (object != null) {
	        baos = new ByteArrayOutputStream();
	        oos = new ObjectOutputStream(baos);
	        oos.writeObject(object);
	        return baos.toByteArray();
	      }
	    } catch (Exception e) {
	      logger.error("序列化对象失败",e);
	    }
	    return null;
	  }

	  /**
	   * 反序列化对象
	   *
	   * @param bytes
	   * @return Object
	   */
	  public static Object unserialize(byte[] bytes) {
	    ByteArrayInputStream bais = null;
	    try {
	      if (bytes != null && bytes.length > 0) {
	        bais = new ByteArrayInputStream(bytes);
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        return ois.readObject();
	      }
	    } catch (Exception e) {
	      logger.error("反序列化对象失败",e);
	    }
	    return null;
	  }
	  
	  /**
	   * 正则匹配
	   * @param data
	   * @param pattern
	   * @return
	   */
	  public static boolean matcher(String data,String patternStr) {
		  if(StringUtils.isBlank(patternStr) || StringUtils.isBlank(data)) return false;
		  Pattern pattern = Pattern.compile(patternStr);
		  Matcher matcher = pattern.matcher(data);
		  if(matcher.find()) return true;
		  return false;
	  }
	  
	  public static void main(String[] args) {
		  System.out.println(matcher("png","[jpg][png]"));
	  }

}
