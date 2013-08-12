package com.yyu.fwk.formula.expression;

import junit.framework.Assert;

import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public abstract class OperatorTestBase {
	
	public void executeOperatorSuccess(String leftStr, String rightStr, String expected){
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Expression exp = getSymbol(left, right);
		try {
			Object actual = exp.interpreter(null);
			if(actual instanceof Double){
				Assert.assertEquals(expected, String.format("%.2f", actual));
			}else{
				Assert.assertEquals(expected, actual.toString());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	public void executeOperatorFail(String leftStr, String rightStr, String expected){
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Expression exp = getSymbol(left, right);
		try {
			exp.interpreter(null);
			Assert.fail("case should fail with [" + leftStr + "] and [" + rightStr + "].");
		} catch (Exception e) {
			Assert.assertEquals(expected, e.getMessage());
		}
	}
	
	public abstract Expression getSymbol(Expression left, Expression right);
}
