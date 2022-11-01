package com.application.utilities;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.AccountDto;
import com.application.dto.CategoryDto;
import com.application.dto.OrderDto;
import com.application.dto.ProductDto;
import com.application.entities.Account;
import com.application.entities.Category;
import com.application.entities.Order;
import com.application.entities.OrderDetail;
import com.application.entities.Product;
import com.application.model.AccountRole;

@Service
public class MapperUtility {
	@Autowired
	private ModelMapper mapper;

	public Category categoryConvertDtoToEntity(CategoryDto dto) {
		Category entity = new Category(dto.getId(), dto.getName());
		return entity;
	}

	public CategoryDto categoryConvertEntityToDto(Category entity) {
		CategoryDto dto = new CategoryDto(entity.getId(), entity.getName());
		return dto;
	}

	public Product productConvertDtoToEntity(ProductDto dto) {
		Category category = new Category(dto.getIdCategory(), null);
		Product entity = new Product(dto.getId(), dto.getName(), dto.getImage(), BigDecimal.valueOf(dto.getPrice()),
				dto.getQuantity(), dto.getCreatedDate(), dto.getAvailable() ? 1 : 0, category, null);
		return entity;
	}

	public ProductDto productConvertEntityToDto(Product entity) {
		ProductDto dto = new ProductDto(entity.getId(), entity.getName(), entity.getImage(),
				entity.getPrice().floatValue(), entity.getAvailable() == 1 ? true : false, entity.getQuantity(),
				entity.getCreatedDate(), entity.getCategory().getId());
		return dto;
	}

	public AccountDto accountConvertEntityToDto(Account entity) {
		AccountRole role = entity.getAdmin() == 0 ? AccountRole.ADMIN
				: (entity.getAdmin() == 1 ? AccountRole.STAFF : AccountRole.CUSTOMER);
		AccountDto dto = new AccountDto(entity.getId(), entity.getUsername(), entity.getPhoto(), entity.getPassword(),
				entity.getFullname(), entity.getEmail(), entity.getAdmin(), entity.getActivated() == 1 ? true : false,entity.getVerifyCode(),entity.getVerifyDate());
		return dto;
	}

	public Account accountConvertDtoToEntity(AccountDto dto) {
//		Integer role = dto.getAdmin()==AccountRole.ADMIN?0:(dto.getAdmin()==AccountRole.STAFF?1:2);
		Account entity = new Account(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getFullname(),
				dto.getEmail(), dto.getPhoto(), dto.getActivated() ? 1 : 0, dto.getAdmin(), dto.getVerifyCode(),dto.getVerifyDate(),null);
		return entity;
	}

	public Order orderConvertDtoToEntity(OrderDto dto) {
//		Integer role = dto.getAdmin()==AccountRole.ADMIN?0:(dto.getAdmin()==AccountRole.STAFF?1:2);
		Account account = null;
		if(dto.getAccount() != null) {
			account = new Account();
			account.setId(dto.getAccount());
		}
		Order entity = new Order(dto.getId(), dto.getCreateDate(), dto.getAddress(), dto.getNumberPhone(),
				dto.getNote(), dto.getStatus(), null, account,null);
		return entity;
	}

	public OrderDto orderConvertEntityToDto(Order entity) {
		Integer id = null;
		if(entity.getAccount()!= null) {
			id = entity.getAccount().getId();
		}
		OrderDto dto = new OrderDto(entity.getId(), entity.getAddress(), entity.getNote(), entity.getNumberPhone(),
				entity.getCreateDate(),id , entity.getStatus());
		return dto;
	}
	public OrderDetail sendDataForOrderDetail(Product product,Order order) {
		OrderDetail orderDetail = new OrderDetail(null,product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())), product.getQuantity(), order, product);
		System.err.println(orderDetail.getOrder().getId());
		return orderDetail;
	}

}
