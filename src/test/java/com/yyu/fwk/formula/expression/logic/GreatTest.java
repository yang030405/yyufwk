package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.Great;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class GreatTest extends OperatorTestBase {
	
	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Great(left, right);
	}
	
	@Test
	public void testGreatOperator(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Great great = new Great(left, right);
		Assert.assertEquals(OperatorEnum.GREAT, great.getOperator());
	}
	
	@Test
	public void testGreatTrueSuccess(){
		String leftStr = "2.20";
		String rightStr = "1.10";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatFalseSuccess(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testGreatFalseWithEqualValue(){
		String leftStr = "1.10";
		String rightStr = "1.10";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
