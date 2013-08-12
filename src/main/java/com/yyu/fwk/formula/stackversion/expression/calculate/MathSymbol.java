package com.yyu.fwk.formula.stackversion.expression.calculate;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.exception.NumberCastException;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.variable.Symbol;
import com.yyu.fwk.util.BeanUtil;

public abstract class MathSymbol extends Symbol {

	private Double leftVariable;
	private Double rightVariable;
	
	public MathSymbol(Expression left, Expression right){
		super(left, right);
	}
	
	@Override
	public Object interpreter(Map<String, String> var) throws NumberCastException, Exception {
		String lstr = left.interpreter(var) == null ? "0" : left.interpreter(var).toString();
		String rstr = right.interpreter(var) == null ? "0" : right.interpreter(var).toString();
		if(!BeanUtil.isNum(lstr)){
			throw new NumberCastException("Cannot convert left variable [" + lstr + "] to double.");
		}
		if(!BeanUtil.isNum(rstr)){
			throw new NumberCastException("Cannot convert right variable [" + rstr + "] to double.");
		}
		
		leftVariable = Double.valueOf(lstr);
		rightVariable = Double.valueOf(rstr);
		
		return calclate();
	}
	
	public Double getLeftVariable(){
		return this.leftVariable;
	}
	
	public Double getRightVariable(){
		return this.rightVariable;
	}
	
	public String parseDouble(Double d){
		return String.format("%.2f", d);
	}
	
	public abstract Object calclate();
}
