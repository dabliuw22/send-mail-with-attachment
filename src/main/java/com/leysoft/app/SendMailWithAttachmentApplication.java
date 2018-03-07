package com.leysoft.app;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leysoft.app.service.inter.MailService;

@SpringBootApplication
public class SendMailWithAttachmentApplication implements CommandLineRunner {

	@Autowired
	private MailService mailService;
	
	public static void main(String[] args) {
		SpringApplication.run(SendMailWithAttachmentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			mailService.sendMailWithText(new String[] {"mail_one@mail.com", "mail_two@mail.com"}, "Ejemplo", "Mensaje ejemplo");
			File[] files = {new File("/home/user/Escritorio/file1.txt"), new File("/home/user/Escritorio/file2.txt")};
			mailService.sendMailWithAttachment(new String[] {"mail_one@mail.com", "mail_two@mail.com"}, "Ejemplo Attachment", "Mensaje ejemplo", files);
		} catch (Exception e) {
		}
	}
}
