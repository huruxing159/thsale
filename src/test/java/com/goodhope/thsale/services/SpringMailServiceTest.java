package com.goodhope.thsale.services;

import org.junit.Before;
import org.junit.Test;

import com.goodhope.thsale.factory.SpringBeanFactory;

public class SpringMailServiceTest {

	private SpringMailService springMailService;

	@Before
	public void setUp() {
		springMailService = (SpringMailService) SpringBeanFactory.getBean("springMailService");
	}

	@Test
	public void testSendSimpleMail() {

	}

	@Test
	public void testSendMimeMail() {
		springMailService.sendMimeMail("hu.ruxing@goodhope.net", "test", "test",new String[]{ "/testMailFile.properties"});
	}

	public void setSpringMailService(SpringMailService springMailService) {
		this.springMailService = springMailService;
	}

}
