package com.leysoft.app.service.imple;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leysoft.app.service.inter.MailService;

@Service
public class MailServiceImp implements MailService {
	
	@Value(value = "${support.email.from}")
	private String from;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendMailWithText(String[] to, String subject, String message) throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(message);
		mail.setFrom(from);
		mailSender.send(mail);
	}

	@Override
	public void sendMailWithHtml(String[] to, String subject, String message) throws Exception {
		MimeMessage mail = mailSender.createMimeMessage();
		Context context = new Context();
		context.setVariable("message", message);
		String html = templateEngine.process("mail_template", context);
		MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(html, true);
		helper.setFrom(from);
		mailSender.send(mail);
	}

	@Override
	public void sendMailWithAttachment(String[] to, String subject, String message, File[] files) throws Exception {
		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(message);
		helper.setFrom(from);
		int length = files.length;
		if(length > 0) {
			FileSystemResource[] fileResources = new FileSystemResource[length];
			for(int i = 0; i < length; i++) {
				fileResources[i] = new FileSystemResource(files[i]);
				helper.addAttachment(fileResources[i].getFilename(), files[i]);
			}
		}
		mailSender.send(mail);
	}
}