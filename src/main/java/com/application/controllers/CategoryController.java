package com.application.controllers;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.dto.CategoryDto;
import com.application.entities.Category;
import com.application.repositories.CategoryRepository;
import com.application.utilities.MapperUtility;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private MapperUtility mapperUtility;
	@Autowired
	private HttpSession session;

	@RequestMapping("/categories")
	public String showcategoryPage(Model model) {
		List<Category> list = categoryRepository.findAll();
		model.addAttribute("list", list);
		model.addAttribute("view", "/views/categoryList.jsp");
		return "/views/dashboard.jsp";
	}

	@ResponseBody
	@RequestMapping("/categories/show")
	public Page<Category> showcategoryPage2(Model model) {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Category> list = categoryRepository.findAll(pageable);
		return list;
	}

	@RequestMapping(path = "/category/save", method = RequestMethod.POST)
	public String newcategory(@Valid @ModelAttribute("category") CategoryDto dto, BindingResult result, Model model) {
		if (dto.getId() == null) {
			return "redirect:/admin/category/new";
		}
		if (result.hasErrors()) {
			sendataForNewcategory(model);
			model.addAttribute("errors", result.getAllErrors());
			return "/views/dashboard.jsp";
		}
		Category entity = mapperUtility.categoryConvertDtoToEntity(dto);
		try {
			categoryRepository.save(entity);
			session.setAttribute("alertSuccess", "Thêm danh mục thành công");
		} catch (Exception e) {
			session.setAttribute("alert", "Thêm danh mục thất bại");
		}
		return "redirect:/admin/categories";
	}

	@RequestMapping(path = "/category/update", method = RequestMethod.POST)
	public String updatecategory(Model model, @Valid @ModelAttribute("category") CategoryDto dto,
			BindingResult result) {
		if (dto.getId() == null) {
			session.setAttribute("alert", "Danh mục không tồn tại");
			return "redirect:/admin/categories";
		}
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			sendataForNewcategory(model);
			return "/views/dashboard.jsp";
		}
		if (categoryRepository.findById(dto.getId()).isEmpty()) {
			session.setAttribute("alert", "Danh mục không tồn tại");
			return "redirect:/admin/categories";
		}
		Category entity = mapperUtility.categoryConvertDtoToEntity(dto);

		try {
			categoryRepository.save(entity);
			session.setAttribute("alertSuccess", "Cập nhật danh mục thành công");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/categories";
	}

	@RequestMapping("/category/new")
	public String forwardNewcategoryPage(Model model, @ModelAttribute("category") CategoryDto dto) {
		dto.setId(-1);
		sendataForNewcategory(model);
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/category/edit/{id}")
	public String forwardUpdatecategoryPage(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alert", "Thông tin chỉnh sửa không hợp lệ");
			return "redirect:/admin/categories";
		}
		Optional<Category> entityOption = categoryRepository.findById(id);
		if (entityOption.isEmpty()) {
			session.setAttribute("alert", "Danh mục không tồn tại");
			return "redirect:/admin/categories";
		}
		Category entity = entityOption.get();
		model.addAttribute("category", mapperUtility.categoryConvertEntityToDto(entity));
		sendataForEditcategory(model);
		return "/views/dashboard.jsp";
	}

	public void sendataForNewcategory(Model model) {
		model.addAttribute("text", "Tạo mới");
		model.addAttribute("uri", "save");
		model.addAttribute("view", "/views/categoryEdit.jsp");
		model.addAttribute("message", "Tạo mới danh mục");
	}

	public void sendataForEditcategory(Model model) {
		model.addAttribute("text", "Cập nhật");
		model.addAttribute("uri", "update");
		model.addAttribute("view", "/views/categoryEdit.jsp");
		model.addAttribute("message", "Cập nhật danh mục");
	}
}
