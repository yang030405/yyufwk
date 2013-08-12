package com.yyu.fwk.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yyu.fwk.util.exception.PropertyExistException;

final public class PropertyUtil{
	private static Log log = LogFactory.getLog(PropertyUtil.class);
	
	private static final List<String> props = new ArrayList<String>();
	static{
		props.add("db");
	}
	
	private static final Map<String, String> propMap = new HashMap<String, String>();
	static{
		init();
	}
	
	private static void init(){
		for(String prop : props){
			prop += ".properties";
			Properties p = new Properties();
			try{
				p.load(PropertyUtil.class.getResourceAsStream("/" + prop));
				for(String key : p.stringPropertyNames()){
					String value = p.getProperty(key);
					if(!BeanUtil.isBlank(propMap.get(key))){
						throw new PropertyExistException(key + " has already exist.");
					}
					propMap.put(key, value);
				}
			}catch(Exception e){
				log.error("an error happen while reading property " + prop, e);
			}
		}
	}
	
	public static String get(String key){
		return propMap.get(key);
	}
}
