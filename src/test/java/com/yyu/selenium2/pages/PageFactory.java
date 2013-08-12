package com.yyu.selenium2.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.yyu.fwk.util.BeanUtil;
import com.yyu.selenium2.annotations.ByID;
import com.yyu.selenium2.annotations.ByJQuery;
import com.yyu.selenium2.annotations.ByXPath;

public class PageFactory {
	
	public static <T extends Page> T initPage(Class<? extends T> clazz, String url) throws Exception {
		return initPage(clazz, url, new FirefoxDriver());
	}
	
	public static <T extends Page> T initPage(Class<? extends T> clazz, String url, WebDriver driver) throws Exception {
		WebDriver _driver = driver;
		_driver.get(url);
		Constructor<? extends T> constructor = clazz.getConstructor(WebDriver.class);
		T initedPage = constructor.newInstance(_driver);
		Field[] fields = clazz.getDeclaredFields();
		if(!BeanUtil.isBlank(fields)){
			for(Field field : fields){
				if(field.isAnnotationPresent(ByXPath.class)){
					ByXPath xPathInstance = field.getAnnotation(ByXPath.class);
					String xPath = xPathInstance.value();
					WebElement theElement = _driver.findElement(By.xpath(xPath));
					field.setAccessible(true);
					field.set(initedPage, theElement);
				}else if(field.isAnnotationPresent(ByID.class)){
					ByID idInstance = field.getAnnotation(ByID.class);
					String id = idInstance.value();
					WebElement theElement = _driver.findElement(By.id(id));
					field.setAccessible(true);
					field.set(initedPage, theElement);
				}else if(field.isAnnotationPresent(ByJQuery.class)){
					ByJQuery jQueryInstance = field.getAnnotation(ByJQuery.class);
					String jQuery = jQueryInstance.value();
					//TODO weifeng would implement this method.
					WebElement theElement = _driver.findElement(By.id(jQuery));
					field.setAccessible(true);
					field.set(initedPage, theElement);
				}
			}
		}
		return initedPage;
	}
}
