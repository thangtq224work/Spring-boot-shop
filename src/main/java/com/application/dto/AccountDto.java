package com.application.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.application.model.AccountRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
	@NotNull
	private Integer id;
	@Length(min = 5, max = 20,message = "Phải từ 5 đến 20 kí tự")
	private String username;
	private String photo;
	@Length(min = 3,max = 15)
	private String password;

	@Length(min = 5, max = 50)
	private String fullname;
	@Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	@Min(value = 0)
	@Max(value = 2)
	private Integer admin;
	@NotNull
	private Boolean activated;
	private String verifyCode;
	private Date verifyDate;
}
