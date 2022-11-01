package com.application.dto;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
	@NotNull
	private Integer id;
	@NotBlank
	private String name;
	
	private String image;
	@NotNull
	@DecimalMin("1000.0")
	@DecimalMax("1000000000.0")
	private Float price;
	@NotNull
	private Boolean available;
	@NotNull
	private Integer quantity;
	private Date createdDate;
	private Integer idCategory;
}
