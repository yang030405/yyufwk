package com.yyu.fwk.db.exception;

public class MultiRecordFoundException extends Exception {
	private static final long serialVersionUID = 2643950354958177034L;

	public MultiRecordFoundException(String msg){
		super(msg);
	}
}
