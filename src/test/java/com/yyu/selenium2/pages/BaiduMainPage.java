package com.yyu.selenium2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.yyu.selenium2.annotations.ByJQuery;
import com.yyu.selenium2.annotations.ByXPath;

public class BaiduMainPage extends Page{
	
	@ByXPath(value="/html/body/div/div/div[2]/div/form")
	private WebElement webSearchingForm;
	
	@ByJQuery(value="#formid div[0] tr[0]")
	private WebElement submitButton;
	
	public BaiduMainPage(WebDriver page) {
		super(page);
	}
	
	public WebElement getWebSearchingForm(){
		return webSearchingForm;
	}
	
	public WebElement getWebSearchingInput(){
		return webSearchingForm.findElement(By.id("kw"));
	}
	
	public WebElement getWebSearchingSubmitButton(){
		return webSearchingForm.findElement(By.id("su"));
	}
}
