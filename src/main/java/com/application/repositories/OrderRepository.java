package com.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entities.Account;
import com.application.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Query("SELECT o FROM Order o WHERE o.account=:id")
	public List<Order> getOrders(@Param("id") Account account);
	@Query("SELECT o FROM Order o WHERE o.account=:acid AND o.id=:id")
	public Order getOrderById(@Param("acid") Account account,@Param("id") Integer id);
	
}
