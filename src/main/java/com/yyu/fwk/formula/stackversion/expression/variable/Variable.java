package com.yyu.fwk.formula.stackversion.expression.variable;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;

public class Variable implements Expression {
	
	private String key;

	public Variable(String key) {
		this.key = key;
	}

	@Override
	public Object interpreter(Map<String, String> var) {
		return var.get(this.key);
	}
	
	@Override
	public OperatorEnum getOperator(){
		return null;
	}
	
	@Override
	public String toString(){
		return key;
	}
}
