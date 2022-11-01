package com.application.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.application.interfaces.MailServiceInterface;
import com.application.model.MailInfor;
@Service
public class MailService implements MailServiceInterface{
	List<MailInfor> list2 = new ArrayList<>();
	@Autowired
	private JavaMailSender sender;
	JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	@Override
	public void send(MailInfor mailInfor) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		// thiết lập thông tin cần thiết cho mail (message,cho phép đính file,encoding)
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mailInfor.getFrom());
		helper.setTo(mailInfor.getTo());
		helper.setSubject(mailInfor.getTitle());
		helper.setText(mailInfor.getBody(),true);
		helper.setReplyTo(mailInfor.getFrom());
		String cc[] = mailInfor.getCc();
		if(cc != null && cc.length >0) {
			helper.setCc(cc);
		}
		String bcc[] = mailInfor.getBcc();
		if(bcc != null && bcc.length >0) {
			helper.setBcc(bcc);
		}
		List<File> list = mailInfor.getList();
		if(list.size()>0) {
			for (File file : list) {
				helper.addAttachment(file.getName(), file);
			}
		}
		sender.send(message);
	}

	@Override
	public void send(String to, String title, String body) throws MessagingException {
		this.send(new MailInfor(title, to, body));
		
	}

}
