package com.application.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false,name = "username")
	private String username;
	@Column(nullable = false,name = "password")
	private String password;
	@Column(nullable = false,name = "fullname",unique = true)
	private String fullname;
	@Column(nullable = false,name = "email")
	private String email;
	@Column(nullable = true,name = "photo")
	private String photo = null;
	@Column(nullable = false,name = "activated")
	private Integer activated = 0;
	@Column(nullable = false,name = "admin")
	private Integer admin = 0;
	@Column(nullable = true,name = "verifyCode")
	private String verifyCode;
	@Column(nullable = true,name = "verifyDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date verifyDate;
	@OneToMany(mappedBy = "account")
	private List<Order> orderEntities;
}
