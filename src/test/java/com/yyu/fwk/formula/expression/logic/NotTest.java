package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.Not;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class NotTest extends OperatorTestBase {

	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Not(right);
	}
	
	@Test
	public void testAndOperator(){
		String rightStr = "false";
		
		Expression right = new Literal(rightStr);
		Not not = new Not(right);
		Assert.assertEquals(OperatorEnum.NOT, not.getOperator());
	}
	
	@Test
	public void testNotTrue_False(){
		String rightStr = "true";
		String expected = "false";
		
		executeOperatorSuccess(null, rightStr, expected);
	}
	
	@Test
	public void testNotFalse_True(){
		String rightStr = "false";
		String expected = "true";
		
		executeOperatorSuccess(null, rightStr, expected);
	}
}
