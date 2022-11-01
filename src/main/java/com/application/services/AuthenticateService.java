package com.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.entities.Account;
import com.application.model.AccountRole;
import com.application.repositories.AccountRepository;

@Service
public class AuthenticateService {
	@Autowired
	private AccountRepository accountRepository;
	public Account loginIsSuccess(String u,String p) {
		Account entity = accountRepository.findByUsername(u);
		if(entity == null) {
			return null;
		}
		if(!entity.getPassword().equals(p)) {
			return null;
		}
		return entity;
	}
}
