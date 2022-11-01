package com.application.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entities.Category;
import com.application.entities.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p WHERE p.available = 1")
	public List<Product> getProducts();
	@Query("SELECT p FROM Product p WHERE p.category =:category")
	public Page<Product> getProductsByCategory(@Param("category") Category category,Pageable pageable);
	@Query("SELECT p FROM Product p WHERE p.id=:id AND p.available = 1")
	public Product getProduct(@Param("id") Integer id);
}
