package com.yyu.fwk.performance;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Performance {

	private static Log log;
	
	private static Performance p = new Performance();
	
	private Performance(){
	}
	
	public static Performance getInstance(Class<?> clazz){
		log = LogFactory.getLog(clazz);
		return p;
	}
	
	public <T> T execute(String message, Action<T> action) throws Exception{
		Date startTime = new Date();
		T result = action.action();
		Date endTime = new Date();
		log.info("action costs [" + (endTime.getTime() - startTime.getTime()) + "] milliseconds with message:<" + message + ">");
		return result;
	}
	
	public interface Action<A> {
		public A action() throws Exception;
	}
}
