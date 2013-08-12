package com.yyu.fwk.formula.expression.variable;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.stackversion.expression.variable.Literal;

public class LiteralTest {
	
	@Test
	public void testStringLiteralTest(){
		String value = "vvv";
		Literal literal = new Literal(value);
		try{
			Assert.assertNull(literal.getOperator());
			Assert.assertEquals(value, literal.interpreter(null).toString());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testDoubleLiteralTest(){
		String value = "1.10";
		Literal literal = new Literal(value);
		try{
			Assert.assertNull(literal.getOperator());
			Assert.assertEquals(value, literal.interpreter(null).toString());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
