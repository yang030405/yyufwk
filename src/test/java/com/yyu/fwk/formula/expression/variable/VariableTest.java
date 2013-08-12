package com.yyu.fwk.formula.expression.variable;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.yyu.fwk.formula.stackversion.expression.variable.Variable;

public class VariableTest {
	
	@Test
	public void testVariableTest(){
		Map<String, String> var = new HashMap<String, String>();
		var.put("kkkey", "value");
		String key = "kkkey";
		
		Variable variable = new Variable(key);
		try{
			Assert.assertNull(variable.getOperator());
			Assert.assertEquals(var.get(key), variable.interpreter(var).toString());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
