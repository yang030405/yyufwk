package com.yyu.fwk.formula.stackversion.expression.logic;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.calculate.MathSymbol;

public class LittleEqual extends MathSymbol {

	public LittleEqual(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public Object calclate(){
		return getLeftVariable() <= getRightVariable();
	}
	
	@Override
	public OperatorEnum getOperator(){
		return OperatorEnum.LITTLE_EQUAL;
	}
}
