package com.lingxi.framework.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils extends FastJsonHttpMessageConverter {

    /**
     * json --&gt; list
     * @param json		json字符串
     * @return			列表
     */
    public static List<Map<String, Object>> toList(String json) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (json.length() > 2) {
        	JSONArray jsonArray = JSONArray.parseArray(json);
            int iSize = jsonArray.size();
            for (int i = 0; i < iSize; i ++) {
            	JSONObject jsonObj = jsonArray.getJSONObject(i);
                resultList.add(toMap(jsonObj.toString()));
            }
        }
        return resultList;
    }
    
	/**
	 * json --&gt; map
	 * @param json		json字符串
	 * @return 			集合
	 */
	public static Map<String, Object> toMap(String json) {
	    Map<String, Object> resultMap = new HashMap<String, Object>();
		if (json.length() > 2) {
		    JSONObject jsonObj = JSONObject.parseObject(json);
		    for (Object key : jsonObj.keySet()) {
		        Object value = jsonObj.get(key);
		        if (value != null) {
		            resultMap.put((String) key, value);
		        } else {
		            throw new IllegalStateException("Could not decode value for key: " + key);
		        }
		    }
		}
		return resultMap;
	}
	
	/**
	 * Object --&gt; json
	 * @param obj		java对象
	 * @return 			json字符串
	 */
	public static String toJson(Object obj) {
		return JSONObject.toJSONString(obj);
	}
	
	/**
	 * Object --&gt; json
	 * @param obj				java对象
	 * @param prettyFormat		是否格式化
	 * @return					json字符串
	 */
	public static String toJson(Object obj, boolean prettyFormat) {
		return JSONObject.toJSONString(obj, prettyFormat);
	}
}
