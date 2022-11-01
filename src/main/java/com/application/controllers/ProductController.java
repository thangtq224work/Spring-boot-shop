package com.application.controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.application.dto.ProductDto;
import com.application.entities.Account;
import com.application.entities.Category;
import com.application.entities.Product;
import com.application.repositories.CategoryRepository;
import com.application.repositories.ProductRepository;
import com.application.utilities.MapperUtility;
import com.application.utilities.UploadUtility;

@Controller
@RequestMapping("/admin")
public class ProductController {
	private String delim = File.separator;
	private String folder = System.getProperty("user.dir") + delim + "src" + delim + "main" + delim + "webapp" + delim
			+ "image" + delim;
	@Autowired
	private UploadUtility uploadUtility;
	@Autowired
	private HttpSession session;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private MapperUtility mapperUtility;

	@RequestMapping("/products")
	public String productPage(Model model) {
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("view", "productList.jsp");
		model.addAttribute("list", page.toList());
		model.addAttribute("page", page);
		return "/views/dashboard.jsp";
	}
	@RequestMapping(path="/products",params = "page")
	public String productPage2(Model model,@RequestParam("page") String input) {
		Integer pageNumber = null;
		try {
			pageNumber = Integer.parseInt(input);
		} catch (Exception e) {
			return "redirect:/admin/products";
		}
		
		Pageable pageable = PageRequest.of(pageNumber-1, 2);
		Page<Product> page = productRepository.findAll(pageable);
		model.addAttribute("view", "productList.jsp");
		model.addAttribute("list", page.toList());
		model.addAttribute("page", page);
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/product/edit/{id}")
	public String productPage(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alert", "Thông tin chỉnh sửa không hợp lệ");
			return "redirect:/admin/products";
		}
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isEmpty()) {
			session.setAttribute("alertError", "Chỉnh sửa thất bại");
			return "redirect:/admin/products";
		}

		sendAttributeForUpdate(model, null, mapperUtility.productConvertEntityToDto(optional.get()));
		return "/views/dashboard.jsp";
	}
	@RequestMapping("/product/delete/{id}")
	public String deleteAccount(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Thông tin không hợp lệ");
			return "redirect:/admin/products";
		}
		Optional<Product> option = productRepository.findById(id);
		if(option.isEmpty()) {
			session.setAttribute("alertError", "Tài khoản không tồn tại");
			return "redirect:/admin/products";
		}
		Product account = option.get();
		account.setAvailable(0);
		productRepository.save(account);
		session.setAttribute("alert", "Xóa sản phẩm thành công");
		return "redirect:/admin/products";
	}

	@RequestMapping("/product/new")
	public String openFormNewProduct(Model model) {
		ProductDto dto = new ProductDto();
		dto.setId(-1);
		dto.setAvailable(true);
		sendAttributeForSave(model, null, dto);
		return "/views/dashboard.jsp";
	}

	@RequestMapping(path = "/product/update", method = RequestMethod.POST)
	public String updateProduct(Model model, @Valid @ModelAttribute("product") ProductDto productDto,
			BindingResult result, @RequestParam("imageupload") MultipartFile multipartFile) {
		if(productDto.getId() == null) {
			session.setAttribute("alertError", "Sản phẩm không tồn tại");
			return "redirect:/admin/products";
		}
		if (result.hasErrors()) {
			sendAttributeForUpdate(model, result, productDto);
			return "/views/dashboard.jsp";
		}
		if (!productRepository.existsById(productDto.getId())
				|| !categoryRepository.existsById(productDto.getIdCategory())) {
			session.setAttribute("alertError", "Cập nhật thất bại");
			return "redirect:/admin/products";
		}
		productDto.setCreatedDate((Date) session.getAttribute("createdate"));
		String oldImage = productDto.getImage();
		File fileSaved = uploadUtility.uploadImage(multipartFile, folder);
		productDto.setImage(fileSaved != null ? fileSaved.getName() : oldImage);
		try {
			productRepository.save(mapperUtility.productConvertDtoToEntity(productDto));
			if(productDto.getImage().equals(oldImage)== false) {
				uploadUtility.deleteImage(folder + oldImage);
			}
			session.setAttribute("alertSuccess", "Cập nhật sản phẩm thành công");
			if(session.getAttribute("createdate") != null) {
				session.removeAttribute("createdate");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("RUN");
			session.setAttribute("alertError", "Cập nhật sản phẩm thất bại");
		}
		return "redirect:/admin/products";
	}

	@RequestMapping(path = "/product/save", method = RequestMethod.POST)
	public String saveProduct(Model model, @Valid @ModelAttribute("product") ProductDto productDto,
			BindingResult result, @RequestParam("imageupload") MultipartFile multipartFile) {

		if (result.hasErrors()) {
			sendAttributeForSave(model, result, productDto);
			return "/views/dashboard.jsp";
		}
		if (productRepository.existsById(productDto.getId())
				|| !categoryRepository.existsById(productDto.getIdCategory())) {
			session.setAttribute("alertError", "Thêm sản phẩm thất bại");
			return "redirect:/admin/products";
		}
		File fileSaved = uploadUtility.uploadImage(multipartFile, folder);
		productDto.setImage(fileSaved != null ? fileSaved.getName() : null);
		productDto.setCreatedDate(new Date());
		try {

			productRepository.save(mapperUtility.productConvertDtoToEntity(productDto));
			session.setAttribute("alertSuccess", "Thêm sản phẩm thành công");
		} catch (Exception e) {
			session.setAttribute("alertError", "Thêm sản phẩm thất bại");
		}
		return "redirect:/admin/products";
	}

	private void sendAttributeForSave(Model model, BindingResult result, ProductDto productDto) {
		List<Category> list = categoryRepository.findAll();
		if (result != null) {
			model.addAttribute("errors", result.getAllErrors());
		}
		model.addAttribute("categories", list);
		model.addAttribute("text", "Tạo mới");
		model.addAttribute("message", "Tạo mới sản phẩm");
		model.addAttribute("uri", "save");
		model.addAttribute("product", productDto);
		model.addAttribute("view", "productEdit.jsp");
	}

	private void sendAttributeForUpdate(Model model, BindingResult result, ProductDto productDto) {
		List<Category> list = categoryRepository.findAll();
		if (result != null) {
			model.addAttribute("errors", result.getAllErrors());
		}
		session.setAttribute("createdate", productDto.getCreatedDate());
		model.addAttribute("categories", list);
		model.addAttribute("text", "Cập nhật");
		model.addAttribute("message", "Cập nhật sản phẩm");
		model.addAttribute("uri", "update");
		model.addAttribute("product", productDto);
		model.addAttribute("view", "productEdit.jsp");
	}
}
