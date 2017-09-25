package com.tiger.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	/**
	 * 发送简单格式的邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendSimpleMail(String to,String subject,String content) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		
		try {
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发送一个html格式的邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
	public void sendHtmlMail(String to,String subject,String content) {
		
		MimeMessage message = sender.createMimeMessage();
		try {
			//true 表示需要创建一个mutipart message
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content);
			
			sender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 发送带附件的邮件
	 * @param to
	 * @param subject
	 * @param content
	 * @param filePath
	 */
	public void sendAttachmentsMail(String to, String subject, String content, String filePath){
		MimeMessage message = sender.createMimeMessage();

		try {
			//true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource file = new FileSystemResource(new File(filePath));
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
	        helper.addAttachment(fileName, file);
	        
			sender.send(message);
			
		} catch (MessagingException e) {
			
		}
	}
	
	/**
	 * 发送嵌入静态资源（一般是图片）的邮件
	 * @param to
	 * @param subject
	 * @param content 邮件内容，需要包括一个静态资源的id，比如：<img src=\"cid:rscId01\" >
	 * @param rscPath 静态资源路径和文件名
	 * @param rscId 静态资源id
	 */
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
		MimeMessage message = sender.createMimeMessage();

		try {
			//true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource res = new FileSystemResource(new File(rscPath));
			helper.addInline(rscId, res);
	        
			sender.send(message);
		} catch (MessagingException e) {
			
		}
	}
}
