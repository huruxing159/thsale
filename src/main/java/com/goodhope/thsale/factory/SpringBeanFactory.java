package com.goodhope.thsale.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory {

	public static Object getBean(String alias) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:applicationContext.xml", "classpath:applicationContext-mail.xml", "classpath:applicationContext-util.xml" });
		return context.getBean(alias);
	}
}