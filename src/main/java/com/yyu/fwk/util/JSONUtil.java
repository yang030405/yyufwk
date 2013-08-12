package com.yyu.fwk.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtil {
	static GsonBuilder gbuilder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping();// 不转义html标记
	static Gson gson = gbuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();// 格式时间

	public static String toJson(Object beanClazz) {
		return gson.toJson(beanClazz);
	}
	
	public static Object fromJson(String jsonStr, Type type) {
		return gson.fromJson(jsonStr, type);
	}
	
	public static void main(String[] args) {
		String[] campaignIds = new String[]{"1234"};
		
		String cidsjson = JSONUtil.toJson(campaignIds);
		
		System.out.println(cidsjson);
		
		Type type = new TypeToken<String[]>(){}.getType();
		
		String[] cids = (String[])JSONUtil.fromJson(cidsjson, type);
		System.out.println(cids);
	}
}
