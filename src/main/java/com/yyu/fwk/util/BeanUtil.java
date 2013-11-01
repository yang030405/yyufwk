package com.yyu.fwk.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class BeanUtil {
	public static String WHITESPACE = " ";
	
	public static boolean isBlank(Object o) {
		if (o == null)
			return true;
		Class<?> clazz = o.getClass();
		if (isSimpleType(clazz)){
			return "".equals(o.toString().trim());
		} else if (clazz.isArray()){
			return Array.getLength(o) <= 0;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			return ((Collection<?>) o).size() <= 0;
		} else if (Map.class.isAssignableFrom(clazz)) {
			return ((Map<?, ?>) o).size() <= 0;
		}
		return false;
	}

	public static boolean isSimpleType(Class<?> clazz) {
		return (clazz.isPrimitive() 
				|| String.class.isAssignableFrom(clazz)
				|| Boolean.class.isAssignableFrom(clazz)
				|| Integer.class.isAssignableFrom(clazz)
				|| Long.class.isAssignableFrom(clazz)
				|| Float.class.isAssignableFrom(clazz)
				|| Character.class.isAssignableFrom(clazz)
				|| Short.class.isAssignableFrom(clazz)
				|| Double.class.isAssignableFrom(clazz)
				|| Byte.class.isAssignableFrom(clazz)
				|| BigDecimal.class.isAssignableFrom(clazz) 
				|| byte[].class.isAssignableFrom(clazz));
	}
	
	/**
	 * 1   : true
     * 1.  : true
     * 1.1 : true
     * 1.0 : true
     * .1  : true
     * 0.1 : true
     * 以上字符串前支持正负号
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str){
		if(str.trim().equals("-") || str.trim().equals("+")){
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?|(([0-9]+))?[.])$");
	}
}
