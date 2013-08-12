package com.yyu.fwk.formula.stackversion.expression;

import java.util.Iterator;

import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.exception.LengthExceedException;
import com.yyu.fwk.util.BeanUtil;

public class ExpressionIterator implements Iterator<String> {
	public enum ValueType{
		Variable, Operator, Literal
	}
	
	private String nextValue;
	private ValueType valueType;
	
	public static final int MAX_VAR_LENGTH = 100;
	private char[] varnamechars = new char[MAX_VAR_LENGTH];
	private char[] expChar;
	private int expCharIndexer = 0;
	private boolean litralWithQuote = false;
	
	public ExpressionIterator(String expStr){
		this.expChar = expStr.toCharArray();
		this.litralWithQuote = false;
	}
	
	public ExpressionIterator(String expStr, boolean litralWithQuote){
		this.expChar = expStr.toCharArray();
		this.litralWithQuote = litralWithQuote;
	}
	
	@Override
	public boolean hasNext() {
		if(expCharIndexer >= expChar.length){
			return false;
		}
		
		String cstr = null;
		while(true){
			cstr = String.valueOf(expChar[expCharIndexer]);
			if(BeanUtil.WHITESPACE.equals(cstr)){
				expCharIndexer++;
				if(expCharIndexer >= expChar.length){
					return false;
				}
				continue;
			}
			break;
		}
		
		if(!OperatorEnum.isDefine(cstr)){
			try {
				nextValue = extractVarname();
			} catch (LengthExceedException e) {
				throw new RuntimeException("", e);
			}
		}else if(OperatorEnum.SINGLE_QUOTE.getChar().equals(cstr)){
			nextValue = extractLiteral();
		}else{
			nextValue = extractSymbol();
		}
		
		return true;
	}

	/**
	 * 返回一个已经trim的value。
	 */
	@Override
	public String next() {
		return nextValue;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("no char could be removed in the expression.");
	}
	
	public ValueType getValueType(){
		return valueType;
	}

	private String extractLiteral(){
		String literalValue = "";
		String cstr = "";
		do{
			literalValue += cstr;
			cstr = String.valueOf(expChar[++expCharIndexer]);
		}while(!OperatorEnum.SINGLE_QUOTE.getChar().equals(cstr));
		expCharIndexer++; // 指向该次右边单引号的下一个位置
		valueType = ValueType.Literal;
		if(litralWithQuote){
			literalValue = "'" + literalValue.trim() + "'";
		}
		return literalValue.trim();
	}
	
	private String extractSymbol(){
		String symbol;
		String c1 = String.valueOf(expChar[expCharIndexer]);
		if(expCharIndexer + 1 >= expChar.length){
			expCharIndexer += 1;
			symbol = c1;
		}else{
			String c2 = String.valueOf(expChar[expCharIndexer + 1]);
			if(OperatorEnum.isDefine(c1) && OperatorEnum.isDefine(c2)){
				expCharIndexer += 2;
				symbol = c1 + c2;
				if(!OperatorEnum.isDefine(symbol)){
					symbol = c1;
					expCharIndexer--;
				}
			}else{
				expCharIndexer += 1;
				symbol = c1;
			}
		}
		valueType = ValueType.Operator;
		return symbol.trim();
	}
	
	private String extractVarname() throws LengthExceedException{
		int varLengthCount = 0;
		int offset = expCharIndexer;
		boolean hasNext = false;
		do{
			if(varLengthCount++ > MAX_VAR_LENGTH){
				clearVarname(MAX_VAR_LENGTH - 1);
				throw new LengthExceedException("Var name [" + String.valueOf(varnamechars) + "] exceed max length [" + MAX_VAR_LENGTH + "]");
			}
			
			hasNext = expCharIndexer < expChar.length;
			if(hasNext){
				char c = expChar[expCharIndexer];
				String cstr = String.valueOf(c);
				hasNext = !OperatorEnum.isDefine(cstr);
				if(hasNext){
					varnamechars[expCharIndexer++ - offset] = c;
				}
			}
		}while(hasNext);
		
		String varname = String.valueOf(varnamechars);
		
		clearVarname(varLengthCount - 1);
		valueType = ValueType.Variable;
		return varname.trim();
	}
	
	private void clearVarname(int end){
		for(int i = 0; i <= end; i++){
			varnamechars[i] = 0;
		}
	}
}
