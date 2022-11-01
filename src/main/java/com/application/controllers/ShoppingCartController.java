package com.application.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.dto.OrderDto;
import com.application.entities.Account;
import com.application.entities.Order;
import com.application.entities.OrderDetail;
import com.application.entities.Product;
import com.application.model.OrderStatus;
import com.application.repositories.OrderDetailRepository;
import com.application.repositories.OrderRepository;
import com.application.services.ShoppingCartService;
import com.application.utilities.MapperUtility;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private MapperUtility mapperUtility;
	@Autowired
	private HttpSession session;

	@RequestMapping("/add-to-cart/{id}")
	public String addToCart(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.valueOf(input);

		} catch (Exception e) {
			return "redirect:/home";
		}
		shoppingCartService.add(id);
		session.setAttribute("cartSuccess", "Thêm sản phẩm thành công");
		return "redirect:/home";
	}
	@RequestMapping("/remove/{id}")
	public String removeFromCart(Model model, @PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.valueOf(input);

		} catch (Exception e) {
			return "redirect:/home";
		}
		shoppingCartService.remove(id);
		return "redirect:/cart";
	}
	@RequestMapping("/clear-cart")
	public String clearCart(Model model) {
		shoppingCartService.clear();
		return "redirect:/cart";
	}
	@RequestMapping("/cart")
	public String showCart(Model model) {
		model.addAttribute("views", "/views/_cart.jsp");
		model.addAttribute("totalPrice", shoppingCartService.getAmount());
		model.addAttribute("list", shoppingCartService.getItems());
		return "/views/home.jsp";
	}
	@ResponseBody
	@RequestMapping("/cart/{id}/{quantity}")
	public String showCart(Model model,@PathVariable("id") Integer id,@PathVariable("quantity")Integer quantity) {
		if(quantity>0) {
			shoppingCartService.update(id, quantity);			
		}
		return shoppingCartService.getAmount()+"";
	}
	@RequestMapping("/cart/get-amount")
	public String getAmount() {
		
		return "/views/home.jsp";
	}
	@RequestMapping("/checkout")
	public String checkout(Model model,@ModelAttribute("order") OrderDto dto) {
		if(shoppingCartService.getItems().size()==0) {
			session.setAttribute("cartSuccess", "Giỏ hàng rỗng");
			return "redirect:/home";
		}
		dto.setId(-1);
		model.addAttribute("views", "/views/_checkout.jsp");
		model.addAttribute("totalPrice", shoppingCartService.getAmount());
		model.addAttribute("list", shoppingCartService.getItems());
		return "/views/home.jsp";
	}
	@RequestMapping(path="/checkout",method = RequestMethod.POST)
	public String checkout2(Model model,@Valid @ModelAttribute("order") OrderDto dto,BindingResult result) {
		if(shoppingCartService.getItems().size()==0) {
			session.setAttribute("cartSuccess", "Giỏ hàng rỗng");
			return "redirect:/home";
		}
		dto.setCreateDate(new Date());
		dto.setStatus(OrderStatus.WAITINGCONFIRM);
		if(result.hasErrors()) {
			model.addAttribute("views", "/views/_checkout.jsp");
			model.addAttribute("totalPrice", shoppingCartService.getAmount());
			model.addAttribute("list", shoppingCartService.getItems());
			model.addAttribute("errors", result.getAllErrors());
			return "/views/home.jsp";
		}
		try {
			Account account = (Account) session.getAttribute("user");
			if(account == null) {
				return "redirect:/home";
			}
			dto.setAccount(account.getId());
			Order order = orderRepository.save(mapperUtility.orderConvertDtoToEntity(dto));
			List<OrderDetail> orderDetails = new ArrayList<>();
			for (Product object : shoppingCartService.getItems()) {
				orderDetails.add(mapperUtility.sendDataForOrderDetail(object, order));
			}
			orderDetailRepository.saveAll(orderDetails);
			shoppingCartService.clear();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "redirect:/home";
	}
	@RequestMapping("/my-order")
	public String myOrder(Model model) {
		Account account = (Account) session.getAttribute("user");
		List<Order> list = orderRepository.getOrders(account);
		model.addAttribute("views", "/views/_myOrder.jsp");
		model.addAttribute("list", list);
		return "/views/home.jsp";
	}
	@RequestMapping("/order/infor/{id}")
	public String inforOrder(Model model,@PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		} catch (Exception e) {
			return "redirect:/home";
		}
		Account account = (Account) session.getAttribute("user");
		Order order = orderRepository.getOrderById(account, id);
		if(order == null) {
			session.setAttribute("cartSuccess", "Đơn hàng không tồn tại");
			return "redirect:/home";
		}
		model.addAttribute("views", "/views/_orderInfor.jsp");
		model.addAttribute("order", order);
		double total = 0;
		for (OrderDetail item : order.getOrderDetailEntities()) {
			total+= item.getPrice().doubleValue();
		}
		model.addAttribute("total", total);
		return "/views/home.jsp";
	}
	@RequestMapping("/order/cancel/{id}")
	public String cancelOrder(Model model,@PathVariable("id") String input) {
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		} catch (Exception e) {
			return "redirect:/home";
		}
		Account account = (Account) session.getAttribute("user");
		Order order = orderRepository.getOrderById(account, id);
		if(order == null) {
			session.setAttribute("cartSuccess", "Hủy đơn hàng thất bại");
			return "redirect:/home";
		}
		if(order.getStatus() == OrderStatus.CANCEL||order.getStatus() == OrderStatus.SUCCESS) {
			session.setAttribute("cartSuccess", "Hủy đơn hàng thất bại");
			return "redirect:/home";	
		}
		order.setStatus(OrderStatus.CANCEL);
		orderRepository.save(order);
		session.setAttribute("cartSuccess", "Hủy đơn hàng thành công");
		return "redirect:/my-order";
	}
}
