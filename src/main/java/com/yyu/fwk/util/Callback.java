package com.yyu.fwk.util;

public interface Callback<T> {
	public  T callback(Object ... params) throws Exception;
}
