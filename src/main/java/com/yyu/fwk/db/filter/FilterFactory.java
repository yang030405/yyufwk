package com.yyu.fwk.db.filter;

import java.util.Collection;
import java.util.Iterator;

import com.yyu.fwk.db.filter.sqlfilter.AndFilter;
import com.yyu.fwk.db.filter.sqlfilter.OrFilter;
import com.yyu.fwk.util.BeanUtil;

final public class FilterFactory {
	
	private FilterFactory(){}
	
	public static <T> IFilter getFilter(String name, IFilter.Operator operator, T value){
		return new SQLFilter<T>(name, operator, value);
	}
	
	/**
	 * this filter is used for generating a SQL grammar 'where clause'
	 * @author YuYang
	 */
	static private class SQLFilter<T> implements IFilter {

		private String name;
		private IFilter.Operator operator;
		private T value;
		
		public SQLFilter(String name, IFilter.Operator operator, T value){
			this.name = name;
			this.operator = operator;
			this.value = value;
		}
		
		@Override
		public String toString(){
			return name + " " + operator.getSymbol() + " " + processValue(value);
		}
		
		@SuppressWarnings("rawtypes")
		private String processValue(T value){
			if(value instanceof String){
				return new String("'" + value + "'");
			}else if(value instanceof Collection){
				boolean isFirstTime = true;
				Iterator iter = ((Collection) value).iterator();
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				while(iter.hasNext()){
					Object o = iter.next();
					if(!isFirstTime){
						sb.append(",");
					}
					if(o instanceof String){
						sb.append("'" + o + "'");
					}else if(BeanUtil.isSimpleType(o.getClass())){
						sb.append(o);
					}else{
						throw new IllegalArgumentException("[" + value.getClass() + "] with value [" + value + "] is not supported.");
					}
					isFirstTime = false;
				}
				sb.append(")");
				return sb.toString();
			}else if(BeanUtil.isSimpleType(value.getClass())){
				return value.toString();
			}else{
				throw new IllegalArgumentException("[" + value.getClass() + "] with value [" + value + "] is not supported.");
			}
		}
		
		@Override
		public IFilter appendAnd(IFilter filter) {
			if(filter != null){
				return new AndFilter(this, filter);
			}
			return this;
		}

		@Override
		public IFilter appendOr(IFilter filter) {
			if(filter != null){
				return new OrFilter(this, filter);
			}
			return this;
		}
	}
}
