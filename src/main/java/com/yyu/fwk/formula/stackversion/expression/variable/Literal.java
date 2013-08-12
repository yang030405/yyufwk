package com.yyu.fwk.formula.stackversion.expression.variable;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;

public class Literal implements Expression {
	private String literal;
	
	public Literal(String literal){
		this.literal = literal;
	}
	
	@Override
	public Object interpreter(Map<String, String> var) throws Exception {
		return literal;
	}

	@Override
	public OperatorEnum getOperator(){
		return null;
	}
	
	@Override
	public String toString(){
		return literal;
	}
}
