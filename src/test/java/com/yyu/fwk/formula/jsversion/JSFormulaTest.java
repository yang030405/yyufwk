package com.yyu.fwk.formula.jsversion;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Test;

public class JSFormulaTest {

	@Test
	public void testJSFormula() throws Exception{
		String exp = "a == 'a' && b == 2";
		ScriptEngineManager manager = new ScriptEngineManager();  
	    ScriptEngine engine = manager.getEngineByName("js");
	    engine.put("a", "'a'");
	    engine.put("b", "2");
	    Object result = engine.eval(exp).toString();
	    System.out.println(result);
	}
}
