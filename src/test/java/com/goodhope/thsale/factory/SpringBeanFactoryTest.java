package com.goodhope.thsale.factory;

import junit.framework.Assert;

import org.junit.Test;

public class SpringBeanFactoryTest {

	@Test
	public void testGetBean() {
		Object numberUtil = SpringBeanFactory.getBean("numberUtil");
		Assert.assertNotNull(numberUtil);
		Object propertyPlaceholderConfigure = SpringBeanFactory.getBean("propertyPlaceholderConfigure");
		Assert.assertNotNull(propertyPlaceholderConfigure);
	}

}
