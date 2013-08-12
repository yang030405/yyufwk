package com.yyu.fwk.formula;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.yyu.fwk.formula.expression.ExpressionIteratorTest;
import com.yyu.fwk.formula.expression.calculate.AddTest;
import com.yyu.fwk.formula.expression.calculate.DivideTest;
import com.yyu.fwk.formula.expression.calculate.MultiplyTest;
import com.yyu.fwk.formula.expression.calculate.SubtractTest;
import com.yyu.fwk.formula.expression.logic.AndTest;
import com.yyu.fwk.formula.expression.logic.EqualTest;
import com.yyu.fwk.formula.expression.logic.GreatEqualTest;
import com.yyu.fwk.formula.expression.logic.GreatTest;
import com.yyu.fwk.formula.expression.logic.LittleEqualTest;
import com.yyu.fwk.formula.expression.logic.LittleTest;
import com.yyu.fwk.formula.expression.logic.NotTest;
import com.yyu.fwk.formula.expression.logic.OrTest;
import com.yyu.fwk.formula.expression.variable.LiteralTest;
import com.yyu.fwk.formula.expression.variable.OperatorTest;
import com.yyu.fwk.formula.expression.variable.VariableTest;

@RunWith(Suite.class)  
@Suite.SuiteClasses({   
	AddTest.class,
	DivideTest.class,
	MultiplyTest.class,
	SubtractTest.class,
	
	AndTest.class,
	EqualTest.class,
	GreatTest.class,
	GreatEqualTest.class,
	LittleTest.class,
	LittleEqualTest.class,
	NotTest.class,
	OrTest.class,
	
	LiteralTest.class,
	OperatorTest.class,
	VariableTest.class,
	
	ExpressionIteratorTest.class,
	CalculatorTest.class
})  
public class FormulaTestSuite {
}
