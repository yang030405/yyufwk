package com.yyu.fwk.common;

public interface Callback<T> {
	public  T callback(Object ... params) throws Exception;
}
