package com.application.model;

import lombok.Getter;

@Getter
public enum AccountRole {
	ADMIN(0,"Chủ"),STAFF(1,"Nhân viên"),CUSTOMER(2,"Khách hàng");
	Integer id;
	String name;
	private AccountRole(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
