package com.yyu.fwk.formula.stackversion;

import java.util.HashMap;
import java.util.Map;

public enum OperatorEnum {
	LEFT_BRACKET("(", 8, true),
	RIGHT_BRACKET(")", 8, true),
	SINGLE_QUOTE("'", 8, true),
	MULTIPLY("*", 7, false),
	DIVIDE("/", 7, false),
	ADD("+", 6, false),
	SUBTRACT("-", 6, false),
	NOT("!", 5, true),
	GREAT(">", 4, false),
	GREAT_EQUAL(">=", 4, false),
	LITTLE("<", 4, false),
	LITTLE_EQUAL("<=", 4, false),
	EQUAL("=", 3, false),
	AND("&&", 2, false),
	OR("||", 1, false);
	
	private String c;
	private int priority = 0;
	private boolean unary = false;
	
	private OperatorEnum(String c, int priority, boolean unary){
		this.c = c;
		this.priority = priority;
		this.unary = unary;
	}
	
	private static Map<String, OperatorEnum> operatorMap = new HashMap<String, OperatorEnum>();
	private static String operatorchars = "";
	static{
		for(OperatorEnum o : OperatorEnum.values()){
			operatorMap.put(o.c, o);
			operatorchars += o.c;
		}
	}
	
	/**
	 * return the operator as a String. 
	 */
	public String getChar(){
		return this.c;
	}
	
	/**
	 * return the operaotr's priority. 
	 */
	public int getPriority(){
		return this.priority;
	}
	
	/**
	 * return true if the operator is a unary operator, otherwise, return false. 
	 */
	public boolean isUnary(){
		return this.unary;
	}
	
	public static OperatorEnum convert(String c){
		OperatorEnum o = operatorMap.get(c); 
		if(o != null){
			return o;
		}
		throw new RuntimeException("Cannot convert [" + c + "] to an Operator.");
	}
	
	public static boolean isDefine(String c){
		return operatorchars.contains(c);
	}
}
