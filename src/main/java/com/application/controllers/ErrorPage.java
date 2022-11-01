package com.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPage {
	@RequestMapping("/access-denied")
	public String name() {
		return "/views/accessDeny.jsp";
	}
}
