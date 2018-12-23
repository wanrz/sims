package com.sims.common.util;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class JsonUtil {  
    private static SerializeConfig mapping = new SerializeConfig();  
   
    /** 
     * 默认的处理时间 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(Object jsonText) {  
        return JSON.toJSONString(jsonText,  
                SerializerFeature.WriteDateUseDateFormat);  
    }  
   
    /** 
     * 自定义时间格式 
     *  
     * @param jsonText 
     * @return 
     */  
    public static String toJSON(String dateFormat, Object jsonText) {  
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));  
        return JSON.toJSONString(jsonText, mapping);  
    }  

    /**
     * 将json字符串转换为对象
     * @param jsonText
     * @return
     */
    public static <T> T parse(String jsonText, Class<T> clazz){
    	return JSON.parseObject(jsonText, clazz);
    }
    
    /**
     * 将json字符串转换为List
     * @param jsonText
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(String jsonText, Class<T> clazz){
    	return JSON.parseArray(jsonText, clazz);
    }
    
    public static void main(String[] args) {
    	/*Result result = new Result("111","交易成功");
    	result.getRequest().put("id", 1);
    	result.getData().put("sim", 0.98);
    	result.getData().put("test", 0);
    	System.out.println(JsonUtil.toJSON(result));*/
    }
}