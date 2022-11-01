package com.application.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.dto.AccountDto;
import com.application.dto.CategoryDto;
import com.application.entities.Account;
import com.application.entities.Category;
import com.application.model.AccountRole;
import com.application.repositories.AccountRepository;
import com.application.repositories.CategoryRepository;
import com.application.utilities.MapperUtility;
import com.application.utilities.UploadUtility;

@Controller
@RequestMapping("/admin")
public class AccountController {
	private String delim = File.separator;
	private String folder = System.getProperty("user.dir") + delim + "src" + delim + "main" + delim + "webapp" + delim
			+ "image" + delim;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MapperUtility mapperUtility;
	@Autowired
	private UploadUtility uploadUtility;
	@Autowired
	private HttpSession session;

	@RequestMapping("/accounts")
	public String showAccountPage(Model model) {
		List<Account> list = accountRepository.findAll();
		AccountRole[] array = AccountRole.values();
		model.addAttribute("list", list);
		model.addAttribute("items", array);
		model.addAttribute("view", "/views/accountList.jsp");
		return "/views/dashboard.jsp";
	}
	@RequestMapping(path="/accounts",params = "filter")
	public String showAccountPageWithFilter(Model model,@RequestParam("filter")String input) {
		Integer role = null;
		try {
			role = Integer.valueOf(input);
		} catch (Exception e) {
			return "redirect:/admin/accounts";
		}
		List<Account> list = null;
		if(role ==-1) {
			list = accountRepository.findAll();
		}
		else {
			list = accountRepository.getAllAccountByRole(role);			
		}
		AccountRole[] array = AccountRole.values();
		model.addAttribute("list", list);
		model.addAttribute("items", array);
		model.addAttribute("check", role);
		model.addAttribute("view", "/views/accountList.jsp");
		return "/views/dashboard.jsp";
	}

	@RequestMapping(path = "/account/save", method = RequestMethod.POST)
	public String newAccount(@Valid @ModelAttribute("account") AccountDto dto, BindingResult result, Model model,
			@RequestParam("repassword") String repass, @RequestParam("imageupload") MultipartFile multipartFile) {
		if (dto.getId() == null) {
			return "redirect:/admin/account/new";
		}
		if (!dto.getPassword().equals(repass)) {
			model.addAttribute("errorPass", "Mật khẩu xác nhận không hợp lệ ");
			sendataForNewAccount(model, dto);
			model.addAttribute("errors", result.getAllErrors());
			return "/views/dashboard.jsp";
		}
		if (result.hasErrors()) {
			sendataForNewAccount(model, dto);
			model.addAttribute("errors", result.getAllErrors());
			return "/views/dashboard.jsp";
		}
		File fileSaved = uploadUtility.uploadImage(multipartFile, folder);
		dto.setPhoto(fileSaved != null ? fileSaved.getName() : null);
		Account entity = mapperUtility.accountConvertDtoToEntity(dto);
		try {
			accountRepository.save(entity);
			session.setAttribute("alertSuccess", "Thêm tài khoản thành công");
		} catch (Exception e) {
			session.setAttribute("alert", "Thêm tài khoản thất bại");
		}
		return "redirect:/admin/accounts";
	}

	@RequestMapping(path = "/account/update", method = RequestMethod.POST)
	public String updateAccount(Model model, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result,
			 @RequestParam("imageupload") MultipartFile multipartFile) {
		if (dto.getId() == null) {
			session.setAttribute("alertError", "Tài khoản không tồn tại");
			return "redirect:/admin/accounts";
		}
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			sendataForEditAccount(model, dto);
			return "/views/dashboard.jsp";
		}

		Account userLogin = (Account) session.getAttribute("user");
		if(userLogin.getAdmin() != dto.getAdmin() && userLogin.getId() == dto.getId()) {
			session.setAttribute("alertError", "Không thể cập nhật vai trò của chính mình");
			return "redirect:/admin/accounts";
		}
		File fileSaved = uploadUtility.uploadImage(multipartFile, folder);
		dto.setPhoto(fileSaved != null ? fileSaved.getName() : null);
		Account entity = mapperUtility.accountConvertDtoToEntity(dto);
		Account data = (Account) session.getAttribute("accountUpdate");
		entity.setPassword(data.getPassword());
		entity.setUsername(data.getUsername());
		entity.setVerifyCode(data.getVerifyCode());
		entity.setVerifyDate(data.getVerifyDate());
		String oldImage = data.getPhoto();
		try {
			accountRepository.save(entity);
			session.setAttribute("alertSuccess", "Cập nhật tài khoản thành công");
			session.removeAttribute("accountUpdate");
			if(fileSaved!=null) {
				uploadUtility.deleteImage(folder + oldImage);
			}

		} catch (Exception e) {
			session.setAttribute("alertError", "Cập nhật thất bại");
		}
		return "redirect:/admin/accounts";
	}

	@RequestMapping("/account/new")
	public String forwardNewcategoryPage(Model model, @ModelAttribute("account") AccountDto dto) {
		dto.setId(-1);
		dto.setActivated(true);
		sendataForNewAccount(model, dto);
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/account/edit/{id}")
	public String forwardUpdateAccountPage(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Thông tin chỉnh sửa không hợp lệ");
			return "redirect:/admin/accounts";
		}
		Optional<Account> entityOption = accountRepository.findById(id);
		if (entityOption.isEmpty()) {
			session.setAttribute("alert", "Tài khoản không tồn tại");
			return "redirect:/admin/accounts";
		}
		Account entity = entityOption.get();
		session.setAttribute("accountUpdate", entity);
		sendataForEditAccount(model, mapperUtility.accountConvertEntityToDto(entity));
		return "/views/dashboard.jsp";
	}
	@RequestMapping("/account/delete/{id}")
	public String deleteAccount(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alert", "Thông tin không hợp lệ");
			return "redirect:/admin/accounts";
		}
		Optional<Account> option = accountRepository.findById(id);
		if(option.isEmpty()) {
			session.setAttribute("alert", "Tài khoản không tồn tại");
			return "redirect:/admin/accounts";
		}
		Account account = option.get();
		account.setActivated(0);
		accountRepository.save(account);
		session.setAttribute("alert", "Xóa tài khoản thành công");
		return "redirect:/admin/accounts";
	}
	public void sendataForNewAccount(Model model, AccountDto dto) {
		model.addAttribute("account", dto);
		AccountRole[] roles = AccountRole.values();
		model.addAttribute("role", roles);
		model.addAttribute("text", "Tạo mới");
		model.addAttribute("uri", "save");
		model.addAttribute("view", "/views/accountEdit.jsp");
		model.addAttribute("message", "Tạo mới tài khoản");
	}

	public void sendataForEditAccount(Model model, AccountDto dto) {
		model.addAttribute("account", dto);
		AccountRole[] roles = AccountRole.values();
		model.addAttribute("role", roles);
		model.addAttribute("text", "Cập nhật");
		model.addAttribute("uri", "update");
		model.addAttribute("view", "/views/accountEdit.jsp");
		model.addAttribute("message", "Cập nhật tài khoản");
	}
}
