package com.yyu.fwk.formula.expression.logic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.logic.Little;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class LittleTest extends OperatorTestBase {
	
	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Little(left, right);
	}
	
	@Test
	public void testLittleOperator(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Little little = new Little(left, right);
		Assert.assertEquals(OperatorEnum.LITTLE, little.getOperator());
	}
	
	@Test
	public void testLittleTrueSuccess(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		String expected = "true";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testLittleFalseSuccess(){
		String leftStr = "2.20";
		String rightStr = "1.10";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testLittleFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testLittleFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testLittleFalseWithEqualValue(){
		String leftStr = "1.10";
		String rightStr = "1.10";
		String expected = "false";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
}
