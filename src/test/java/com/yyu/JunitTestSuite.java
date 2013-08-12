package com.yyu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.yyu.fwk.formula.CalculatorTest;
import com.yyu.fwk.formula.FormulaTestSuite;
import com.yyu.fwk.performance.PerformanceTest;

@RunWith(Suite.class)  
@Suite.SuiteClasses({   
	FormulaTestSuite.class,
	CalculatorTest.class,
	PerformanceTest.class
})  
public class JunitTestSuite {

}
