package com.yyu.fwk.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONUtil {
	static GsonBuilder gbuilder = new GsonBuilder().disableHtmlEscaping();// 不转义html标记
	static Gson gson = gbuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();// 格式时间

	public static String toJson(Object beanClazz) {
		return gson.toJson(beanClazz);
	}
	
	public static Object fromJson(String jsonStr, Type type) {
		return gson.fromJson(jsonStr, type);
	}
	
	public static void main(String[] args) {
		Map<String, String> m = new LinkedHashMap<String, String>();
		m.put("Monday", "100");
		m.put("Tuesday", "150");
		m.put("Wednesday", "180");
		m.put("Thursday", "200");
		m.put("Friday", "210");
		m.put("Saturday", "150");
		m.put("Sunday", "100");
		
		System.out.println(JSONUtil.toJson(m));
	}
}
