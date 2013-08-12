package com.yyu.fwk.formula.stackversion.expression.logic;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.exception.UnformalExpressionException;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.variable.Symbol;

public class Not extends Symbol {

	public Not(Expression right) {
		super(null, right);
	}

	@Override
	public Object interpreter(Map<String, String> var) throws Exception {
		String rstr = right.interpreter(var) == null ? "false" : right.interpreter(var).toString();
		
		boolean rb = false;
		
		if(rstr == null){
			throw new UnformalExpressionException("thr boolean value cannot be null.");
		}else if(rstr.equals("true")){
			rb = true;
		}else if(rstr.equals("false")){
			rb = false;
		}else{
			throw new UnformalExpressionException("thr boolean value cannot be [" + rstr + "].");
		}
		
		return !rb;
	}
	
	@Override
	public OperatorEnum getOperator(){
		return OperatorEnum.NOT;
	}
}
