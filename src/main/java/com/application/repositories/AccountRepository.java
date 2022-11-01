package com.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entities.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("SELECT ac FROM Account ac WHERE ac.admin=:role")
	public List<Account> getAllAccountByRole(@Param("role") Integer role);
	@Query("SELECT ac FROM Account ac WHERE ac.username =:user AND  ac.activated = 1")
	public Account findByUsername(@Param("user") String u);
	@Query("SELECT ac FROM Account ac WHERE ac.username =:user AND  ac.activated = 0")
	public Account findByUsername2(@Param("user") String u);
	@Query("SELECT ac FROM Account ac WHERE ac.username=:us OR ac.email=:e")
	public List<Account> checkAccountIsExists(@Param("us") String role,@Param("e")String email);
	

}
