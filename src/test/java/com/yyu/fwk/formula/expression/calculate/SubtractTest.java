package com.yyu.fwk.formula.expression.calculate;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.calculate.Subtract;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class SubtractTest extends OperatorTestBase {
	
	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Subtract(left, right);
	}
	
	@Test
	public void testSubtractOperator(){
		String leftStr = "10";
		String rightStr = "4";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Subtract subtract = new Subtract(left, right);
		Assert.assertEquals(OperatorEnum.SUBTRACT, subtract.getOperator());
	}
	
	@Test
	public void testSubtractSuccess(){
		String leftStr = "10";
		String rightStr = "4";
		String expected = "6.00";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testSubtractFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testSubtractFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
}
