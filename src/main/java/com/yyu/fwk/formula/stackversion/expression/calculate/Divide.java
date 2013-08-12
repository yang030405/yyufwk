package com.yyu.fwk.formula.stackversion.expression.calculate;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;

public class Divide extends MathSymbol {
	
	public Divide(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public Double calclate(){
		Double result = getLeftVariable() / getRightVariable();
		return Double.valueOf(parseDouble(result));
	}
	
	@Override
	public OperatorEnum getOperator(){
		return OperatorEnum.DIVIDE;
	}
}
