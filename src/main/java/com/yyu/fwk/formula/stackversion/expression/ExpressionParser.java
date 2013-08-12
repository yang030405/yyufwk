package com.yyu.fwk.formula.stackversion.expression;

import java.util.Stack;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.ExpressionIterator.ValueType;
import com.yyu.fwk.formula.stackversion.expression.variable.Literal;
import com.yyu.fwk.formula.stackversion.expression.variable.Operator;
import com.yyu.fwk.formula.stackversion.expression.variable.Variable;

public class ExpressionParser {

	private Stack<Expression> stack = new Stack<Expression>();
	
	private OperatorEnum previousOperator;
	private OperatorEnum currentOperator;
	
	public Expression parse(ExpressionIterator iter){
		while(iter.hasNext()){
			String value = iter.next();
			ValueType valueType = iter.getValueType();
			if(valueType == ValueType.Operator && OperatorEnum.isDefine(value)){
				OperatorEnum tempCurrentOperator = OperatorEnum.convert(value);
				if(OperatorEnum.LEFT_BRACKET == tempCurrentOperator){
					Expression expression = new ExpressionParser().parse(iter);
					
					if(previousOperator != null && previousOperator.getPriority() < currentOperator.getPriority()){
						Expression ope = stack.pop();
						Expression left = null;
						if(!ope.getOperator().isUnary()){
							left = stack.pop();
						}
						Expression newExpression = ExpressionFactory.getSymbolExpression(left, ope.getOperator(), expression);
						stack.push(newExpression);
						currentOperator = previousOperator;
						previousOperator = null;
					}else if(previousOperator == null){
						stack.push(expression);
					}
					continue;
				}
				if(OperatorEnum.RIGHT_BRACKET == tempCurrentOperator){
					break;
				}

				// otherwise, it's an operator
				setOperator(tempCurrentOperator);

				if(previousOperator == null){
					stack.push(new Operator(tempCurrentOperator));
				}else{
					if(previousOperator.getPriority() >= tempCurrentOperator.getPriority()){
						Expression right = stack.pop();
						Expression ope = stack.pop();
						Expression left = null;
						if(!ope.getOperator().isUnary()){
							left = stack.pop();
						}
						Expression expression = ExpressionFactory.getSymbolExpression(left, ope.getOperator(), right);
						stack.push(expression);
						Expression tempCurrentOperatorExpression = new Operator(tempCurrentOperator);
						stack.push(tempCurrentOperatorExpression);
						currentOperator = tempCurrentOperator;
						previousOperator = null;
					}else{
						Expression ope = new Operator(tempCurrentOperator);
						stack.push(ope);
					}
				}
				
			} else if(valueType == ValueType.Literal) {
				Expression exp = new Literal(value);
				if(previousOperator != null && previousOperator.getPriority() < currentOperator.getPriority()){
					Expression ope = stack.pop();
					Expression left = null;
					if(!ope.getOperator().isUnary()){
						left = stack.pop();
					}
					Expression expression = ExpressionFactory.getSymbolExpression(left, ope.getOperator(), exp);
					stack.push(expression);
					currentOperator = previousOperator;
					previousOperator = null;
				}else{
					stack.push(exp);
				}
			} else {
				Expression exp = new Variable(value);
				if(previousOperator != null && previousOperator.getPriority() < currentOperator.getPriority()){
					Expression ope = stack.pop();
					Expression left = null;
					if(!ope.getOperator().isUnary()){
						left = stack.pop();
					}
					Expression expression = ExpressionFactory.getSymbolExpression(left, ope.getOperator(), exp);
					stack.push(expression);
					currentOperator = previousOperator;
					previousOperator = null;
				}else{
					stack.push(exp);
				}
			}
		}
		
		if(stack.size() > 1){
			Expression right = stack.pop();
			Expression ope = stack.pop();
			Expression left = null;
			if(!ope.getOperator().isUnary()){
				left = stack.pop();
			}
			Expression expression = ExpressionFactory.getSymbolExpression(left, ope.getOperator(), right);
			stack.push(expression);
		}
		
		return stack.pop();
	}
	
	private void setOperator(OperatorEnum currentOperator){
		this.previousOperator = this.currentOperator;
		this.currentOperator = currentOperator;
	}
}
