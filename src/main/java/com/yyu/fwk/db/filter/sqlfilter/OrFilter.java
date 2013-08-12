package com.yyu.fwk.db.filter.sqlfilter;

import com.yyu.fwk.db.filter.IFilter;


public class OrFilter implements IFilter {

	private IFilter left;
	private IFilter right;
	
	public OrFilter(IFilter left, IFilter right){
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(left);
		sb.append(" or ");
		sb.append(right);
		sb.append(")");
		return sb.toString();
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
