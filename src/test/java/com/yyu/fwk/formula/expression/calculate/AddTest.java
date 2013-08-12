package com.yyu.fwk.formula.expression.calculate;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.expression.OperatorTestBase;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.calculate.Add;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class AddTest extends OperatorTestBase {
	
	@Override
	public Expression getSymbol(Expression left, Expression right){
		return new Add(left, right);
	}
	
	@Test
	public void testAddOperator(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		
		Expression left = new Literal(leftStr);
		Expression right = new Literal(rightStr);
		Add add = new Add(left, right);
		Assert.assertEquals(OperatorEnum.ADD, add.getOperator());
	}
	
	@Test
	public void testAddSuccess(){
		String leftStr = "1.10";
		String rightStr = "2.20";
		String expected = "3.30";
		
		executeOperatorSuccess(leftStr, rightStr, expected);
	}
	
	@Test
	public void testAddFailWithNonNumLeft(){
		String leftStr = "aa";
		String rightStr = "2.20";
		String expected = "Cannot convert left variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
	
	@Test
	public void testAddFailWithNonNumRight(){
		String leftStr = "1.10";
		String rightStr = "aa";
		String expected = "Cannot convert right variable [aa] to double.";
		
		executeOperatorFail(leftStr, rightStr, expected);
	}
}
