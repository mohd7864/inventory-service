package com.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="products")
@Data
public class Products implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
	@Column(name="product_name")
	private String product_name;
	@Column(name="product_brand")
	private String product_brand;
	@Column(name="units")
	private Integer units;
	@Column(name="price")
	private Double price;
	

}
