package com.application.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String numberPhone;
	@Column(nullable = true)
	private String note;
	@Column(nullable = false)
	private Integer status;
	@OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
	private List<OrderDetail> orderDetailEntities;
	@ManyToOne()
	@JoinColumn(name="user_id")
	private Account account;
	@ManyToOne()
	@JoinColumn(name="create_by")
	private Account createBy;
}
