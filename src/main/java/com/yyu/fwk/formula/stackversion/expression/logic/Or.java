package com.yyu.fwk.formula.stackversion.expression.logic;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.variable.Symbol;

public class Or extends Symbol {

	public Or(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public Object interpreter(Map<String, String> var) throws Exception {
		String lstr = left.interpreter(var) == null ? "false" : left.interpreter(var).toString();
		String rstr = right.interpreter(var) == null ? "false" : right.interpreter(var).toString();
		
		boolean lb = Boolean.valueOf(lstr);
		boolean rb = Boolean.valueOf(rstr);
		
		return lb || rb;
	}
	
	@Override
	public OperatorEnum getOperator(){
		return OperatorEnum.OR;
	}
}
