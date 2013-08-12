package com.yyu.selenium2.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class Page {
	private WebDriver page;
	
	public Page(WebDriver page){
		this.page = page;
	}
	
	public WebDriver getPage(){
		return page;
	}
	
	public String executeJS(String js){
		String result = (String)((JavascriptExecutor)page).executeScript(js);
		return result;
	}
}
