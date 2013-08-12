package com.yyu.fwk.formula.stackversion.expression.logic;

import java.util.Map;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.Expression;
import com.yyu.fwk.formula.stackversion.expression.variable.Symbol;
import com.yyu.fwk.util.BeanUtil;

public class Equal extends Symbol {

	public Equal(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public Object interpreter(Map<String, String> var) throws Exception {
		Object lobj = left.interpreter(var);
		Object robj = right.interpreter(var);
		
		if(lobj == null && robj == null){
			return true;
		}else if(lobj == null ^ robj == null){
			return false;
		}
		
		String lstr = left.interpreter(var) == null ? "" : left.interpreter(var).toString();
		String rstr = right.interpreter(var) == null ? "" : right.interpreter(var).toString();
		
		if(BeanUtil.isNum(lstr) && BeanUtil.isNum(rstr)){
			Double ld = Double.valueOf(lstr);
			Double rd = Double.valueOf(rstr);
			return ld.equals(rd);
		}
		return lstr.equals(rstr);
	}
	
	@Override
	public OperatorEnum getOperator(){
		return OperatorEnum.EQUAL;
	}
}
