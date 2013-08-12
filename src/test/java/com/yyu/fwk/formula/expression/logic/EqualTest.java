package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.Equal;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class EqualTest extends OperatorTestBase {

	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Equal(left, right);
	}
	
	@Test
	public void testEqualOperator(){
		String leftStr = "aa";
		String rightStr = "aa";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Equal equal = new Equal(left, right);
		Assert.assertEquals(OperatorEnum.EQUAL, equal.getOperator());
	}
	
	@Test
	public void testStringEqualTrueSuccess(){
		String leftStr = "aa";
		String rightStr = "aa";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testStringEqualFalseSuccess(){
		String leftStr = "aa";
		String rightStr = "bb";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testDoubleEqualTrueSuccess(){
		String leftStr = "1.1";
		String rightStr = "1.10";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testDoubleEqualFalseSuccess(){
		String leftStr = "1.1";
		String rightStr = "1.11";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testDoubleStringEqualFalseSuccess(){
		String leftStr = "1.1";
		String rightStr = "aa";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
