package com.yyu.fwk.formula.stackversion.expression;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;

public interface  Expression {
	public Object interpreter(Map<String, String> var) throws Exception;
	public OperatorEnum getOperator();
}
