package com.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.application.entities.OrderDetail;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer > {
	@Query("SELECT od FROM OrderDetail od WHERE od.order.id=:idOrder AND od.id=:id")
	public OrderDetail getOrderDetail(@Param("idOrder")Integer idOrder,@Param("id")Integer id);
	@Query("SELECT od FROM OrderDetail od WHERE od.order.id=:idOrder AND od.product.id=:id")
	public OrderDetail getOrderDetailIfExists(@Param("idOrder")Integer idOrder,@Param("id")Integer id);
	@Modifying
	@Transactional
	@Query("DELETE FROM OrderDetail od WHERE od.order.id=:idOrder")
	public int clearOrder(@Param("idOrder")Integer idOrder);
}
