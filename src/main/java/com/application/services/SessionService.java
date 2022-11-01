package com.application.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
	@Autowired
	private HttpSession session;
	
	public void addSession(String name,Object value) {
		session.setAttribute(name, value);
	}
	public Object getSession(String name) {
		return session.getAttribute(name);
	}
	public void removeSession(String name) {
		session.removeAttribute(name);
	}
}
