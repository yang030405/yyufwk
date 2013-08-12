package com.yyu.fwk.db.filter;

public interface IFilter {
	public IFilter appendAnd(IFilter filter);
	public IFilter appendOr(IFilter filter);
	
	public static final Operator EQ = Operator.EQ;
	public static final Operator NEQ = Operator.NEQ;
	public static final Operator IN = Operator.IN;
	
	static public enum Operator{
		EQ("="),
		NEQ("<>"),
		IN("in"),
		GTE(">="),
		GT(">"),
		LTE("<="),
		LT("<");
		
		private String symbol;
		private Operator(String symbol){
			this.symbol = symbol;
		}
		
		public String getSymbol(){
			return this.symbol;
		}
	}
}
