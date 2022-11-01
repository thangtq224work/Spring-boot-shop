package com.application.interfaces;

import javax.mail.MessagingException;

import com.application.model.MailInfor;

public interface MailServiceInterface {
	void send(MailInfor mailInfor) throws MessagingException;
	void send(String to,String title,String body) throws MessagingException;
}
