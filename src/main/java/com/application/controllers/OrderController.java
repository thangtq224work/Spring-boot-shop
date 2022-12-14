package com.application.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.dto.OrderDto;
import com.application.entities.Account;
import com.application.entities.Order;
import com.application.entities.OrderDetail;
import com.application.entities.Product;
import com.application.model.OrderStatus;
import com.application.repositories.AccountRepository;
import com.application.repositories.OrderDetailRepository;
import com.application.repositories.OrderRepository;
import com.application.repositories.ProductRepository;
import com.application.utilities.MapperUtility;

@Controller
@RequestMapping("/admin")
public class OrderController {
	@Autowired
	private MapperUtility mapperUtility;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private HttpSession session;

	@RequestMapping("/orders")
	public String orderPage(Model model) {
		Sort sort = Sort.by(Direction.DESC, "createDate");
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Order> page = orderRepository.findAll(pageable);
		model.addAttribute("list", page.toList());
		model.addAttribute("page", page);
		model.addAttribute("view", "/views/orderList.jsp");
		return "/views/dashboard.jsp";
	}

	@RequestMapping(path = "/orders", params = "page")
	public String orderPageWithPaging(Model model, @RequestParam("page") String input) {
		Integer pageNumber = null;
		try {
			pageNumber = Integer.parseInt(input);
		} catch (Exception e) {
			return "redirect:/admin/orders";
		}
		Sort sort = Sort.by(Direction.DESC, "createDate");
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort);
		Page<Order> page = orderRepository.findAll(pageable);
		model.addAttribute("list", page.toList());
		model.addAttribute("page", page);
		model.addAttribute("view", "/views/orderList.jsp");
		return "/views/dashboard.jsp";
	}

	@RequestMapping(path = "/order/save", method = RequestMethod.POST)
	public String newcategory(@Valid @ModelAttribute("order") OrderDto dto, BindingResult result, Model model,
			@RequestParam("username") String username) {
		if (dto.getId() == null) {
			return "redirect:/admin/order/new";
		}
		Account account = null;
		if (result.hasErrors()) {
			sendataForNewOrder(model);
			model.addAttribute("errors", result.getAllErrors());
			return "/views/dashboard.jsp";
		}
		if (!username.trim().isEmpty()) {
			account = accountRepository.findByUsername(username);
			if (account == null) {
				model.addAttribute("InvalidAccount", "T??i kho???n ????ng nh???p kh??ng t???n t???i");
				sendataForNewOrder(model);
				return "/views/dashboard.jsp";
			}
			dto.setAccount(account.getId());
		}
		dto.setCreateDate(new Date());
		dto.setStatus(OrderStatus.WAITINGCONFIRM);
		Order order = mapperUtility.orderConvertDtoToEntity(dto);
		Account userLogin = (Account) session.getAttribute("user");
		order.setCreateBy(userLogin);
		try {
			orderRepository.save(order);
			session.setAttribute("alertSuccess", "Th??m h??a ????n th??nh c??ng");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("alertError", "Th??m h??a ????n th???t b???i");
		}
		return "redirect:/admin/orders";
	}

	@RequestMapping(path = "/order/update", method = RequestMethod.POST)
	public String updatecategory(Model model, @Valid @ModelAttribute("order") OrderDto dto, BindingResult result) {
		if (dto.getId() == null) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			sendataForNewOrder(model);
			return "/views/dashboard.jsp";
		}
//		if (categoryRepository.findById(dto.getId()).isEmpty()) {
//			session.setAttribute("alert", "Danh m???c kh??ng t???n t???i");
//			return "redirect:/admin/categories";
//		}
		Order orderUpdated = (Order) session.getAttribute("entity");
		if(orderUpdated == null) {
			return "redirect:/admin/orders";		
		}
		dto.setCreateDate(orderUpdated.getCreateDate());
		dto.setAccount(orderUpdated.getAccount() != null ? orderUpdated.getAccount().getId() : null);
		dto.setStatus(orderUpdated.getStatus());
		Order entity = mapperUtility.orderConvertDtoToEntity(dto);
		if (orderUpdated.getCreateBy() != null) {
			entity.setCreateBy(orderUpdated.getCreateBy());
		}
		try {
			orderRepository.save(entity);
			session.removeAttribute("entity");
			session.setAttribute("alertSuccess", "C???p nh???t h??a ????n th??nh c??ng");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/orders";
	}

	@RequestMapping("/order/new")
	public String forwardNewcategoryPage(Model model, @ModelAttribute("order") OrderDto dto) {
		dto.setId(-1);
		sendataForNewOrder(model);
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/order/edit/{id}")
	public String editOrder(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Th??ng tin ch???nh s???a kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Optional<Order> entityOption = orderRepository.findById(id);
		if (entityOption.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		Order entity = entityOption.get();
		session.setAttribute("entity", entity);
		model.addAttribute("order", mapperUtility.orderConvertEntityToDto(entity));
		sendataForEditOrder(model);
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/order/confirm/{id}")
	public String confirmOrder(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Th??ng tin ????n h??ng kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		Order order = optional.get();
		if (order.getStatus() != OrderStatus.WAITINGCONFIRM) {
			session.setAttribute("alertError", "H??a ????n kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
		for (OrderDetail item : order.getOrderDetailEntities()) {
			map.put(item.getProduct().getId(), item.getQuantity());
		}
		List<Product> list = new ArrayList<>();
		for (Product item : productRepository.findAllById(map.keySet())) {
			if (item.getQuantity() < map.get(item.getId())) {
				session.setAttribute("alertError", "H??ng trong kho t???m th???i kh??ng ?????");
				return "redirect:/admin/orders";
			} else {
				item.setQuantity(item.getQuantity() - map.get(item.getId()));
				list.add(item);
			}
		}
		productRepository.saveAll(list);
		order.setStatus(OrderStatus.WAITING);
		orderRepository.save(order);
		session.setAttribute("alertSuccess", "????n h??ng ???? ???????c x??c nh???n");
		return "redirect:/admin/orders";
	}

	@RequestMapping("/order/cancel/{id}")
	public String cancelOrder(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Th??ng tin ????n h??ng kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		Order order = optional.get();
		if (order.getStatus() == OrderStatus.WAITINGCONFIRM) {
			order.setStatus(OrderStatus.CANCEL);
			orderRepository.save(order);
			session.setAttribute("alertSuccess", "H???y ????n h??ng th??nh c??ng");
		} else if (order.getStatus() == OrderStatus.WAITING) {

			Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>();
			for (OrderDetail item : order.getOrderDetailEntities()) {
				map.put(item.getProduct().getId(), item.getQuantity());
			}
			List<Product> list = new ArrayList<>();
			for (Product item : productRepository.findAllById(map.keySet())) {
				item.setQuantity(item.getQuantity() + map.get(item.getId()));
				list.add(item);
			}
			productRepository.saveAll(list);
			order.setStatus(OrderStatus.CANCEL);
			orderRepository.save(order);
			session.setAttribute("alertSuccess", "H???y ????n h??ng th??nh c??ng");
		} else {
			session.setAttribute("alertError", "????n h??ng kh??ng h???p l???");
		}
		return "redirect:/admin/orders";
	}

	@RequestMapping("/order/success/{id}")
	public String successOrder(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Th??ng tin ????n h??ng kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Optional<Order> optional = orderRepository.findById(id);
		if (optional.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		Order order = optional.get();
		if (order.getStatus() == OrderStatus.WAITING) {
			order.setStatus(OrderStatus.SUCCESS);
			orderRepository.save(order);
			session.setAttribute("alertSuccess", "????n h??ng th??nh c??ng");
		} else {
			session.setAttribute("alertError", "????n h??ng kh??ng h???p l???");
		}
		return "redirect:/admin/orders";
	}

	@RequestMapping("/order/infor/{id}")
	public String inforOrder(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.parseInt(input);
		} catch (Exception e) {
			session.setAttribute("alertError", "Th??ng tin h??a ????n kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Optional<Order> entityOption = orderRepository.findById(id);
		if (entityOption.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng t???n t???i");
			return "redirect:/admin/orders";
		}
		Order entity = entityOption.get();
		double total = 0;
		for (OrderDetail item : entity.getOrderDetailEntities()) {
			total += item.getPrice().doubleValue();
		}
		model.addAttribute("totalPrice", total);
		List<Product> products = productRepository.getProducts();
//		session.setAttribute("entity", entity);
		model.addAttribute("products", products);
		model.addAttribute("entity", entity);
		model.addAttribute("view", "/views/_adminOrderInfor.jsp");
		return "/views/dashboard.jsp";
	}

	@RequestMapping("/remove-from-order/{idOrder}/{id}")
	public String removeFromOrder(@PathVariable("idOrder") String idOrderString, @PathVariable("id") String idString) {
		Integer idOrder = null;
		Integer id = null;
		try {
			id = Integer.valueOf(idString);
			idOrder = Integer.valueOf(idOrderString);
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/admin/orders";
		}

		OrderDetail orderDetail = orderDetailRepository.getOrderDetail(idOrder, id);
		if (orderDetail == null) {
			return "redirect:/admin/orders";
		}
		Order order = orderRepository.findById(idOrder).get();
		Account userLogin = (Account) session.getAttribute("user");
		Account createBy = order.getCreateBy();
		if (createBy != null) {
			if (createBy.getId() != userLogin.getId()) {
				session.setAttribute("alertError", "Kh??ng th??? x??a s???n ph???m kh???i ????n h??ng");
				return "redirect:/admin/order/infor/"+idOrder;
			}
		} else {
			session.setAttribute("alertError", "Kh??ng th??? x??a s???n ph???m kh???i ????n h??ng");
			return "redirect:/admin/order/infor/"+idOrder;
		}
		orderDetailRepository.delete(orderDetail);
		return "redirect:/admin/order/infor/" + idOrder;
	}

	@RequestMapping("/add-to-order/{idOrder}/{id}")
	public String addToOrder(@PathVariable("idOrder") String idOrderString, @PathVariable("id") String idString) {
		Integer idOrder = null;
		Integer id = null;
		try {
			id = Integer.valueOf(idString);
			idOrder = Integer.valueOf(idOrderString);
		} catch (Exception e) {
			session.setAttribute("alertError", "H??a ????n kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		Product product = null;
		OrderDetail orderDetail = orderDetailRepository.getOrderDetailIfExists(idOrder, id);
		Optional<Order> optional = orderRepository.findById(idOrder);
		Order order = null;
		if (optional.isEmpty()) {
			session.setAttribute("alertError", "H??a ????n kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		order = optional.get();
		if (order.getStatus() != OrderStatus.WAITINGCONFIRM) {
			session.setAttribute("alertError", "Ch??? c?? th??? th??m s???n ph???m v??o h??a ????n ??ang ch???");
			return "redirect:/admin/order/infor/"+idOrder;
		}

		Account userLogin = (Account) session.getAttribute("user");
		Account createBy = order.getCreateBy();
		if (createBy != null) {
			if (createBy.getId() != userLogin.getId()) {
				session.setAttribute("alertError", "Kh??ng th??? th??m s???n ph???m v??o ????n h??ng");
				return "redirect:/admin/order/infor/"+idOrder;
			}
		} else {
			session.setAttribute("alertError", "Kh??ng th??? th??m s???n ph???m v??o ????n h??ng");
			return "redirect:/admin/order/infor/"+idOrder;
		}
		product = productRepository.getProduct(id);
		if (product == null) {
			session.setAttribute("alertError", "S???n ph???m kh??ng h???p l???");
			return "redirect:/admin/orders";
		}
		if (orderDetail == null) {

			if (product.getQuantity() == 0) {
				session.setAttribute("alertError", "S???n ph???m t???m th???i h???t h??ng");
				return "redirect:/admin/orders";
			}
			product.setQuantity(1);
			orderDetail = new OrderDetail(null, product.getPrice(), 1, order, product);
		} else {
			if (product.getQuantity() == orderDetail.getQuantity()) {
				session.setAttribute("alertError", "S???n ph???m t???m th???i h???t h??ng");
				return "redirect:/admin/orders";
			}
			orderDetail.setQuantity(orderDetail.getQuantity() + 1);
			orderDetail.setPrice(orderDetail.getPrice().add(orderDetail.getPrice()));
		}
		orderDetailRepository.save(orderDetail);
		return "redirect:/admin/order/infor/" + idOrder;
	}

	@RequestMapping("/clear-order/{idOrder}")
	public String clearOrder(@PathVariable("idOrder") String idOrderString) {
		Integer idOrder = null;
		try {
			idOrder = Integer.valueOf(idOrderString);
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/admin/orders";
		}
		Order order = orderRepository.findById(idOrder).get();
		Account userLogin = (Account) session.getAttribute("user");
		Account createBy = order.getCreateBy();
		if (createBy != null) {
			if (createBy.getId() != userLogin.getId()) {
				session.setAttribute("alertError", "Kh??ng th??? x??a s???n ph???m kh???i ????n h??ng");
				return "redirect:/admin/order/infor/"+idOrder;
			}
		} else {
			session.setAttribute("alertError", "Kh??ng th??? x??a s???n ph???m kh???i ????n h??ng");
			return "redirect:/admin/order/infor/"+idOrder;
		}
		int tmp = orderDetailRepository.clearOrder(idOrder);
		System.err.println(tmp);
		return "redirect:/admin/order/infor/" + idOrder;
	}

	public void sendataForNewOrder(Model model) {
//		model.addAttribute("statusOrder", OrderStatus.map);
//		for (Map.Entry<Integer, String> item : OrderStatus.map.entrySet()) {
//			System.err.println(item);
//		}
		model.addAttribute("text", "T???o m???i");
		model.addAttribute("uri", "save");
		model.addAttribute("view", "/views/orderEdit.jsp");
		model.addAttribute("message", "T???o m???i h??a ????n");
	}

	public void sendataForEditOrder(Model model) {
//		model.addAttribute("statusOrder", OrderStatus.map);
		model.addAttribute("text", "C???p nh???t");
		model.addAttribute("uri", "update");
		model.addAttribute("view", "/views/orderEdit.jsp");
		model.addAttribute("message", "C???p nh???t h??a ????n");
	}
}
