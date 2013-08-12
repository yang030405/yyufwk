package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.Or;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class OrTest extends OperatorTestBase {

	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Or(left, right);
	}
	
	@Test
	public void testOrOperator(){
		String leftStr = "true";
		String rightStr = "false";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Or and = new Or(left, right);
		Assert.assertEquals(OperatorEnum.OR, and.getOperator());
	}
	
	@Test
	public void testOrTrueTrue_True(){
		String leftStr = "true";
		String rightStr = "true";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testOrTrueFalse_True(){
		String leftStr = "true";
		String rightStr = "false";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testOrFalseTrue_True(){
		String leftStr = "false";
		String rightStr = "true";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testOrFalseFalse_False(){
		String leftStr = "false";
		String rightStr = "false";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
