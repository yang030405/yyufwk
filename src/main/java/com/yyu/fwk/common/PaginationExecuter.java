package com.yyu.fwk.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yyu.fwk.util.BeanUtil;

public abstract class PaginationExecuter<T>{
	
	private List<T> list = new ArrayList<T>();
	private int pageSize = 10;
	private int totalPages = 0;
	
	public PaginationExecuter(List<T> list, int pageSize){
		this.list = list;
		this.pageSize = pageSize;
		init();
	}
	
	public PaginationExecuter(T[] array, int pageSize){
		this.list = BeanUtil.isBlank(array) ? null : Arrays.asList(array);
		this.pageSize = pageSize;
		init();
	}
	
	private void init(){
		if(!BeanUtil.isBlank(list)){
			this.totalPages = list.size() / pageSize + (list.size() % pageSize == 0 ? 0 : 1);
		}
	}
	
	public void execute() throws Exception{
		for(int currentPageNum = 1; currentPageNum <= totalPages; currentPageNum++){
			List<T> currentPage = list.subList((currentPageNum - 1) * pageSize, currentPageNum == totalPages ? list.size() : currentPageNum * pageSize);
			if(BeanUtil.isBlank(currentPage)){
				currentPage = new ArrayList<T>();
			}
			workWith(currentPage);
		}
	}
	
	public abstract void workWith(List<T> currentPage) throws Exception;
	
	
	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<String>();
		for(int i = 1; i <= 10; i++){
			list.add(String.valueOf(i));
		}
		
		new PaginationExecuter<String>(list, 3) {
			@Override
			public void workWith(List<String> currentPage) throws Exception {
				System.out.println(currentPage);
			}
		}.execute();
	}
}