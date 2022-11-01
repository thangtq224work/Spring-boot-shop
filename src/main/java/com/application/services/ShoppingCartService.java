package com.application.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.application.entities.Product;
import com.application.interfaces.ShoppingCartInterface;
import com.application.repositories.ProductRepository;
@Service
@SessionScope
public class ShoppingCartService implements ShoppingCartInterface{
	@Autowired
	private ProductRepository productRepository;
	Map<Integer,Product> map = new HashMap<>();
	@Override
	public Product add(Integer id) {
		Product product = map.get(id);
		if(product != null) {
			this.update(id, product.getQuantity()+1);
		}
		else {
			product = this.isExists(id);
			if(product == null) {
				return null;
			}
			product.setQuantity(1);
			map.put(id, product);
		}
		return product;
	}
	
	public Product isExists(Integer id) {
		Optional<Product> optional = productRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}
		return optional.get().getQuantity()>0?optional.get():null;
	}

	@Override
	public void remove(Integer id) {
		if(map.get(id) != null) {
			map.remove(id);		
		}
	}

	@Override
	public Product update(Integer id, int qty) {
		Optional<Product> optional = productRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}
		if(optional.get().getQuantity()<qty) {
			return null;
		}
		Product item = map.get(id);
		item.setQuantity(qty);
		map.put(id, item);
		return item;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Collection<Product> getItems() {
		List<Product> list = new ArrayList<>(map.values());
		return list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public double getAmount() {
		double amount = 0;
		for (Entry<Integer, Product> item : map.entrySet()) {
			amount += (item.getValue().getPrice().multiply(BigDecimal.valueOf(item.getValue().getQuantity()))).doubleValue();
		}
		return amount;
	}

}
