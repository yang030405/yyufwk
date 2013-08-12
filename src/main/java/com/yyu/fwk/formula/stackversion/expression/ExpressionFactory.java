package com.yyu.fwk.formula.stackversion.expression;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.calculate.Add;
import com.yyu.fwk.formula.stackversion.expression.calculate.Divide;
import com.yyu.fwk.formula.stackversion.expression.calculate.Multiply;
import com.yyu.fwk.formula.stackversion.expression.calculate.Subtract;
import com.yyu.fwk.formula.stackversion.expression.logic.And;
import com.yyu.fwk.formula.stackversion.expression.logic.Equal;
import com.yyu.fwk.formula.stackversion.expression.logic.Great;
import com.yyu.fwk.formula.stackversion.expression.logic.GreatEqual;
import com.yyu.fwk.formula.stackversion.expression.logic.Little;
import com.yyu.fwk.formula.stackversion.expression.logic.LittleEqual;
import com.yyu.fwk.formula.stackversion.expression.logic.Not;
import com.yyu.fwk.formula.stackversion.expression.logic.Or;
import com.yyu.fwk.formula.stackversion.expression.variable.Operator;

public class ExpressionFactory {
	
	public static Expression getSymbolExpression(Expression left, OperatorEnum operator, Expression right){
		Expression exp = null;
		switch(operator){
			case ADD:
				exp = new Add(left, right);
				break;
			case AND:
				exp = new And(left, right);
				break;
			case DIVIDE:
				exp = new Divide(left, right);
				break;
			case EQUAL:
				exp = new Equal(left, right);
				break;
			case GREAT:
				exp = new Great(left, right);
				break;
			case GREAT_EQUAL:
				exp = new GreatEqual(left, right);
				break;
			case LEFT_BRACKET : 
				exp = new Operator(operator);
				break;
			case LITTLE:
				exp = new Little(left, right);
				break;
			case LITTLE_EQUAL:
				exp = new LittleEqual(left, right);
				break;
			case MULTIPLY:
				exp = new Multiply(left, right);
				break;
			case NOT:
				exp = new Not(right);
				break;
			case OR:
				exp = new Or(left, right);
				break;
			case RIGHT_BRACKET:
				exp = new Operator(operator);
				break;
			case SUBTRACT:
				exp = new Subtract(left, right);
				break;
			default:
				throw new RuntimeException("Unsupported operator.");
		}
		return exp;
	}
}
