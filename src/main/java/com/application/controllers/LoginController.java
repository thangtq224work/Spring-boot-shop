package com.application.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.entities.Account;
import com.application.model.AccountRole;
import com.application.services.AuthenticateService;

@Controller
public class LoginController {
	@Autowired
	private HttpSession session;
	@Autowired
	private AuthenticateService authenticateService;
	@RequestMapping(path = { "/login" }, method = RequestMethod.GET)
	public String showPageLogin() {
		return "/views/login.jsp";
	}
	@RequestMapping(path = { "/logout" }, method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute("user");
		return "redirect:/login";
	}
	@PostMapping(path = "/login",params = {"username","password"})
	public String Login(@RequestParam("username")String username,@RequestParam("password") String password,Model model) {
		Account account = authenticateService.loginIsSuccess(username, password);
		if(account == null) {
			model.addAttribute("message", "Đăng nhập thất bại");
			model.addAttribute("username", username);
			return "/views/login.jsp";
		}
		session.setAttribute("user", account);
		if(account.getAdmin() != AccountRole.CUSTOMER.getId()) {
			return "redirect:/admin/categories";
			
		}
		return "redirect:/home";

	}
}
