package com.yyu.fwk.excel;

public class UnsupportExcelVersionException extends Exception {
	private static final long serialVersionUID = 2411677259007433633L;

	public UnsupportExcelVersionException(String message){
		super(message);
	}
}
