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
		String testFilePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "pom.xml";
		springMailService.sendMimeMail("hu.ruxing@goodhope.net", "test", "test", new String[] { testFilePath });
	}

	public void setSpringMailService(SpringMailService springMailService) {
		this.springMailService = springMailService;
	}

}
