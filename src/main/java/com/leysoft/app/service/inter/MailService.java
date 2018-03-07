package com.leysoft.app.service.inter;

import java.io.File;

public interface MailService {
	
	public void sendMailWithText(String[] to, String subject, String message) throws Exception;
	
	public void sendMailWithHtml(String[] to, String subject, String message) throws Exception;
	
	public void sendMailWithAttachment(String[] to, String subject, String message, File[] files) throws Exception;
}