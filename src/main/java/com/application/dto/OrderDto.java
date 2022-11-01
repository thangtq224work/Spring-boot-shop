package com.application.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

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
public class OrderDto {
	@NotNull
	private Integer id;
	@Length(min = 1, max = 255)
	private String address;
	@Length(min = 0, max = 255)
	private String note;
	@Length(min = 10, max = 11)
	private String numberPhone;
	@PastOrPresent
	private Date createDate;
	private Integer account;
	@Min(value = 0)
	@Max(value = 3)
	private Integer status;
}
