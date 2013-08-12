package com.yyu.fwk.formula.stackversion.expression.variable;

import com.yyu.fwk.formula.stackversion.expression.Expression;

public abstract class Symbol implements Expression {
	
	protected Expression left;
	protected Expression right;

	public Symbol(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString(){
		return "(" + left + getOperator().getChar() + right + ")";
	}
}
