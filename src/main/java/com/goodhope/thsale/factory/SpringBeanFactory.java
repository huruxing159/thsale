package com.goodhope.thsale.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml", "classpath:applicationContext-mail.xml", "classpath:applicationContext-util.xml", "classpath:applicationContext-service.xml",
	// "classpath:applicationContext-timer.xml"
			});

	public static Object getBean(String alias) {
		return context.getBean(alias);
	}
}