package com.goodhope.thsale.services;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SpringMailService {

	private MailSender javaMailSender;

	public void send(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		javaMailSender.send(message);

	}

	public void setJavaMailSender(MailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

}
