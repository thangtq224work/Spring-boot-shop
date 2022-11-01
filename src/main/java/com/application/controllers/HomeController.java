package com.application.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.entities.Category;
import com.application.entities.Product;
import com.application.repositories.CategoryRepository;
import com.application.repositories.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;

	@RequestMapping("/home")
	public String home(Model model) {
		Pageable pageable = PageRequest.of(0, 12);
		List<Category> categories = categoryRepository.findAll();
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("categories", categories);
		model.addAttribute("page", page);
		model.addAttribute("products", page.toList());
		model.addAttribute("views", "/views/_content.jsp");
		return "/views/home.jsp";
	}
	@RequestMapping(path="/home",params = "page")
	public String homeWithPage(Model model,@RequestParam("page") String input) {
		Integer pageNumber = null;
		try {
			pageNumber = Integer.parseInt(input);
		} catch (Exception e) {
			return "redirect:/home";
		}
		Pageable pageable = PageRequest.of(pageNumber-1, 12);
		List<Category> categories = categoryRepository.findAll();
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("categories", categories);
		model.addAttribute("page", page);
		model.addAttribute("products", page.toList());
		model.addAttribute("views", "/views/_content.jsp");
		return "/views/home.jsp";
	}
	@RequestMapping("/home/{id}")
	public String home2(Model model,@PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			return "redirect:/home";
		}
		Optional<Category> optional = categoryRepository.findById(id);
		if(optional.isEmpty()) {
			return "redirect:/home";
		}
		Pageable pageable = PageRequest.of(0, 12);
		List<Category> categories = categoryRepository.findAll();
		Page<Product> page = productRepository.getProductsByCategory(optional.get(),pageable);
		model.addAttribute("categories", categories);
		model.addAttribute("products", page.toList());
		model.addAttribute("page", page);
		model.addAttribute("views", "/views/_content.jsp");
		return "/views/home.jsp";
	}
	@ResponseBody
	@RequestMapping("/home/show/{id}")
	public Page<Product> home3(Model model,@PathVariable("id") Integer id) {
		Optional<Category> optional = categoryRepository.findById(id);
		Pageable pageable = PageRequest.of(0, 12);
		Page<Product> page = productRepository.getProductsByCategory(optional.get(),pageable);
		return page;
	}

	
}
