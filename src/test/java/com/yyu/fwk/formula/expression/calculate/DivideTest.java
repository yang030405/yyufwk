package com.yyu.fwk.formula.expression.calculate;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.calculate.Divide;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class DivideTest extends OperatorTestBase {

	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Divide(left, right);
	}
	
	@Test
	public void testDivideOperator(){
		String leftStr = "10";
		String rightStr = "4";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Divide divide = new Divide(left, right);
		Assert.assertEquals(OperatorEnum.DIVIDE, divide.getOperator());
	}
	
	@Test
	public void testDivideSuccess(){
		String leftStr = "10";
		String rightStr = "4";
		String expected = "2.50";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testDivideFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testDivideFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
}
