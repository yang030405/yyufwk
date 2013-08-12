package com.yyu.selenium2;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.yyu.selenium2.pages.BaiduMainPage;
import com.yyu.selenium2.pages.PageFactory;

public class Selenium2SeleniumTest {
	
	@Test
	public void testPageOpen() throws Exception{
		WebDriver webdriver = new FirefoxDriver(); 
		webdriver.get("http://www.baidu.com"); 
		Assert.assertEquals("百度一下，你就知道", webdriver.getTitle());
		try{
			webdriver.findElement(By.id("aa"));
			Assert.fail("aaaaaa");
		}catch(NoSuchElementException e){
			Assert.assertTrue(e.getMessage().contains("Unable to locate element: {\"method\":\"id\",\"selector\":\"aa\"}"));
		}
		webdriver.quit(); 
	}
	
	@Test
	public void testSearchInBaidu() throws Exception{
		WebDriver webdriver = new FirefoxDriver();
		webdriver.findElement(By.id(""));
		webdriver.get("http://www.baidu.com");
		WebElement kwinput = webdriver.findElement(By.id("kw"));
		CharSequence cs = "test".subSequence(0, "test".length());
		kwinput.sendKeys(cs);
		
		WebElement submitButton = webdriver.findElement(By.id("su"));
		submitButton.submit();
		
		Thread.sleep(2 * 1000);
		
		WebElement pagination = webdriver.findElement(By.id("page"));
		WebElement paginationTextElement = pagination.findElement(By.className("nums"));
		Assert.assertEquals("百度为您找到相关结果约100,000,000个", paginationTextElement.getText());
		
		webdriver.quit(); 
	}
	
	@Test
	public void testPage() throws Exception {
		BaiduMainPage baiduMainPage = PageFactory.initPage(BaiduMainPage.class, "http://www.baidu.com");
		WebElement input = baiduMainPage.getWebSearchingInput();
		input.sendKeys("test");
		WebElement submitButton = baiduMainPage.getWebSearchingSubmitButton();
		submitButton.submit();
	}
}
