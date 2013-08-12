package com.yyu.fwk.util;

import junit.framework.Assert;

import org.junit.Test;

public class PropertiesLoaderTest {

	@Test
	public void testGetProperties(){
		String promaxsize = PropertyUtil.get("database.poolsize.max");
		Assert.assertEquals("10", promaxsize);
	}
}
