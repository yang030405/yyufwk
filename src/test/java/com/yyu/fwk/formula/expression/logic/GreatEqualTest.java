package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.GreatEqual;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class GreatEqualTest extends OperatorTestBase {
	
	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new GreatEqual(left, right);
	}
	
	@Test
	public void testGreatEqualOperator(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		GreatEqual greatEqual = new GreatEqual(left, right);
		Assert.assertEquals(OperatorEnum.GREAT_EQUAL, greatEqual.getOperator());
	}
	
	@Test
	public void testGreatEqualTrueSuccess(){
		String leftStr = "2.20";
		String rightStr = "1.10";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatEqualFalseSuccess(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatEqualFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatEqualFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatEqualTrueWithEqualValue(){
		String leftStr = "1.10";
		String rightStr = "1.10";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
