package com.yyu.fwk.formula.stackversion;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.ExpressionIterator;
import com.yyu.fwk.formula.stackversion.expression.ExpressionParser;
import com.yyu.fwk.util.BeanUtil;

public class Calculator {
	
	private String expressionString;
	private Expression expression;
	
	public Calculator(String expStr) {
		if(BeanUtil.isBlank(expStr)){
			throw new NullPointerException("the expression cannot be null.");
		}
		expressionString = expStr;
		init();
	}

	private void init(){
		ExpressionIterator iter = new ExpressionIterator(expressionString);
		expression = new ExpressionParser().parse(iter);
	}
	
	public Object execute(Map<String, String> var) throws Exception {
		return expression.interpreter(var);
	}
	
	public String getInputExpression(){
		return expressionString;
	}
	
	public String getParsedExpression(){
		return expression.toString();
	}
}