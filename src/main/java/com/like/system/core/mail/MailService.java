package com.like.system.core.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	
	private JavaMailSender javaMailSender;
		
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void send(EmailMessage emailMessage) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setTo(emailMessage.to());
		mimeMessageHelper.setSubject(emailMessage.subject());
		mimeMessageHelper.setText(emailMessage.message(), true);
		
		javaMailSender.send(mimeMessage);		
	}
}
