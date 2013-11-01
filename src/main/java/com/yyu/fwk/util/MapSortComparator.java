package com.yyu.fwk.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MapSortComparator implements Comparator<Map<String, String>> {

	String[] orders;
	
	public MapSortComparator(String[] orders) {
		super();
		this.orders = orders;
	}

	/**
	 * o1 比 o2 小，返回 -1
	 * o1 比 o2 大，返回 1
	 * o1 和 o2 相等，返回 0
	 */
	@Override
	public int compare(Map<String, String> o1, Map<String, String> o2) {

		int result=0;
		
		for(String order : orders){
			if(order == null) continue;
			
			String[] orderDetail = order.trim().split(" ");
			if(orderDetail.length == 0) continue;
			
			String orderType="asc";		
			String field=orderDetail[0];
			if(orderDetail.length==2)
				orderType=orderDetail[1];
			
			String s1=o1.get(field);
			String s2=o2.get(field);
			
			if(s1==null||s2==null){
				if(s1!=null)
					result=-1;
				else if(s2!=null)
					result=1;
				
			}
			else{
				//先判断是不是数字，如果是数字，先按数字排序，否则按字符串排序
				Double d1=null;
				Double d2=null;
				
				try{
					d1=Double.parseDouble(o1.get(field));
					d2=Double.parseDouble(o2.get(field));
				}
				catch (NumberFormatException e) {
					//do nothing
				}
				 
				if(d1 != null && d2 != null){
					if(d2 > d1){
						result=1;
					}
					else if(d1 > d2){
						result=-1;
					}
				}
				else{
					result= o2.get(field).compareTo(o1.get(field));
				}
			}

			if(orderType.equals("asc")){
				result=result * (-1);
			}
			//如果排序出结果，直接跳出，否则继续比较后序字段的大小
			if(result != 0 ) break;
		}
			return result;

	}

	
	public static void main(String[] args) {
		Map<String, String> current = new HashMap<String, String>();
		
		Map<String, String> previous = new HashMap<String, String>();
				
		
		MapSortComparator c = new MapSortComparator("md5,str,f".split(","));
		System.out.println(c.compare(current, previous));
	}
}
