package com.yyu.fwk.formula.stackversion.expression.variable;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;

public class Operator implements Expression {
	
	protected OperatorEnum operator;

	public Operator(OperatorEnum operator) {
		this.operator = operator;
	}

	@Override
	public Object interpreter(Map<String, String> var) throws Exception {
		return operator;
	}
	
	@Override
	public OperatorEnum getOperator(){
		return operator;
	}
	
	@Override
	public String toString(){
		return operator.getChar();
	}
}
