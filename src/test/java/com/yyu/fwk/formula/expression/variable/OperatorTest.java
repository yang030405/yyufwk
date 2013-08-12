package com.yyu.fwk.formula.expression.variable;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.variable.Operator;

public class OperatorTest {

	@Test
	public void testOperatorTest(){
		OperatorEnum value = OperatorEnum.ADD;
		Operator operator = new Operator(value);
		try{
			Assert.assertEquals(value, operator.getOperator());
			Assert.assertEquals(value.name(), operator.interpreter(null).toString());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
