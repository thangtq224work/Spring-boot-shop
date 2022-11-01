package com.application.interfaces;

import java.util.Collection;

import com.application.entities.Product;

public interface ShoppingCartInterface {
	Product add(Integer id);
	void remove(Integer id);
	Product update(Integer id,int qty);
	void clear();
	Collection<Product> getItems();
	int getCount();
	double getAmount();
}
