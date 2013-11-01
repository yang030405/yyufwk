package com.yyu.fwk.common;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DurationExecuter<T> implements Executer {
	
	private static Log log = LogFactory.getLog(Executer.class);
	
	private String message;
	
	public DurationExecuter(String message){
		this.message = message;
	}
	
	@Override
	public void execute() throws Exception{
		Date startTime = new Date();
		action();
		Date endTime = new Date();
		log.info(message + " costs " + (endTime.getTime() - startTime.getTime()) + " ms.");
	}

	public abstract void action() throws Exception;
	
	
	// example
	public static void main(String[] args) throws Exception {
		
		Executer p = new DurationExecuter<String>("print"){
			@Override
			public void action() throws Exception {
				// do stuff
				System.out.println("1111");
			}
		};
		p.execute();
	}
}
