package com.application.controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.dto.AccountDto;
import com.application.entities.Account;
import com.application.model.AccountRole;
import com.application.model.ChangePassword;
import com.application.repositories.AccountRepository;
import com.application.services.MailService;
import com.application.utilities.MapperUtility;

@Controller
public class RegularAccountController {
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MapperUtility mapperUtility;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private MailService mailService;

	@RequestMapping("/change-password")
	public String  forwardToChangePassword(Model model) {
		Account user = (Account) session.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}
		return "/views/changePassword.jsp";
	}
	@PostMapping("/change-password")
	public String  changePassword(Model model,ChangePassword changePassword) {
		Account user = (Account) session.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}
		if(checkValidInput(changePassword.getNewPassword(),changePassword.getOldPassword(),changePassword.getRePassword()) == false) {
			model.addAttribute("message", "Mật khẩu từ 3 - 16 kí tự và không chứa kí tự đặc biệt");
			return "/views/changePassword.jsp";	
		}
		if(!user.getPassword().equals(changePassword.getOldPassword())) {
			model.addAttribute("message", "Mật khẩu không chính xác");
			return "/views/changePassword.jsp";
		}
		if(!changePassword.getNewPassword().equals(changePassword.getRePassword())) {
			model.addAttribute("message", "Mật khẩu xác nhận không trùng khớp");
			return "/views/changePassword.jsp";
		}
		if(!user.getPassword().equals(changePassword.getOldPassword())) {
			model.addAttribute("message", "Mật khẩu không chính xác");
			return "/views/changePassword.jsp";
		}
		user.setPassword(changePassword.getNewPassword());
		accountRepository.save(user);
		model.addAttribute("messageSuccess", "Thay đổi mật khẩu thành công");
		return "/views/changePassword.jsp";
	}
	@RequestMapping("/register")
	public String  forwwardToRegister(Model model,@ModelAttribute("register")AccountDto dto) {
		dto.setId(-1);
		dto.setAdmin(AccountRole.CUSTOMER.getId());
		dto.setActivated(false);
		return "/views/register.jsp";
	}
	@PostMapping("/register")
	public String  register(Model model,@RequestParam("rePassword")String repassword,@Valid @ModelAttribute("register")AccountDto dto,BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			System.err.println(repassword+" -");
			return "/views/register.jsp";
		}
		if(accountRepository.findById(dto.getId()).isPresent() || !checkLength(dto.getPassword(), 3, 15) || !checkLength(dto.getUsername(), 5, 20)) {
			model.addAttribute("message", "Thông tin không hợp lệ");
			return "/views/register.jsp";				
		}
		System.err.println(repassword);
		if(dto.getPassword().trim().equals(repassword) == false) {
			model.addAttribute("repasswordError", "Mật khẩu xác nhận không khớp");
			return "/views/register.jsp";
		}
		List<Account> accounts = accountRepository.checkAccountIsExists(dto.getUsername(), dto.getEmail());
		if(accounts.size() >0) {
			model.addAttribute("message", "Tài khoản hoặc email đã được sử dụng");
			return "/views/register.jsp";	
		}
		dto.setAdmin(AccountRole.CUSTOMER.getId());
		dto.setActivated(false);
		dto.setVerifyDate(new Date());
		dto.setVerifyCode(UUID.randomUUID().toString());
		Account account = mapperUtility.accountConvertDtoToEntity(dto);
//		return "/views/register.jsp";
		try {
			Account accountSaved = accountRepository.save(account);
			String url=request.getRequestURL().toString()+"/"+accountSaved.getUsername()+"?verifyCode="+accountSaved.getVerifyCode();
			String body ="<p>Xin chào bạn .Bạn đã đăng kí tài khoản . Vui lòng "
					+ "<a href=\""+url+"\">XÁC THỰC</a> "
					+ "tài khoản qua đường dẫn</p>";
			mailService.send(accountSaved.getEmail(), "Xác thực tài khoản", body);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Có lỗi xảy ra");
			return "/views/register.jsp";	
		}
		return"redirect:/register-success";
	}
	@RequestMapping("/register-success")
	public String sendInfor(Model model) {
		model.addAttribute("views", "/views/_infor.jsp");
		model.addAttribute("message", "Vui lòng truy cập email của bạn để xác thực  tài khoản");
		return "/views/home.jsp";
	}
	@RequestMapping("/register-infor")
	public String sendInfor2(Model model) {
		model.addAttribute("views", "/views/_infor.jsp");
		model.addAttribute("error", "Thông tin không hợp lệ");
		return "/views/home.jsp";
	}
	@RequestMapping(path="/register/{id}",params = "verifyCode")
	public String verifyAccount(Model model,@PathVariable("id")String username,@RequestParam("verifyCode")String code) {
		if(username == null) {
			return"redirect:/register-infor";
		}
		Account account = accountRepository.findByUsername2(username);
		if(account == null) {
			return"redirect:/register-infor";
		}
		if(!account.getVerifyCode().equals(code)) {
			return"redirect:/register-infor";	
		}
		account.setActivated(1);
		account.setVerifyCode(null);
		accountRepository.save(account);
		model.addAttribute("views", "/views/_infor.jsp");
		model.addAttribute("message", "Xác thực tài khoản thành công ");
		return "/views/home.jsp";
	}
	private boolean checkValidInput(String ...input) {
		for (String string : input) {
			string = removeSpecialDigit(string);
			if(string.length()<3 || string.length()>16) {
				return false;
			}
		}
		return true;
	}
	private String removeSpecialDigit(String input) {
		return input.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
	}
	private Boolean checkLength(String input,int min,int max) {
		System.err.println(removeSpecialDigit(input));
		return (removeSpecialDigit(input).length() >= min && removeSpecialDigit(input).length()<max);
	}
}
