package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.And;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class AndTest extends OperatorTestBase {

	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new And(left, right);
	}
	
	@Test
	public void testAndOperator(){
		String leftStr = "true";
		String rightStr = "false";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		And and = new And(left, right);
		Assert.assertEquals(OperatorEnum.AND, and.getOperator());
	}
	
	@Test
	public void testAndTrueTrue_True(){
		String leftStr = "true";
		String rightStr = "true";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testAndTrueFalse_False(){
		String leftStr = "true";
		String rightStr = "false";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testAndFalseTrue_False(){
		String leftStr = "false";
		String rightStr = "true";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testAndFalseFalse_False(){
		String leftStr = "false";
		String rightStr = "false";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
