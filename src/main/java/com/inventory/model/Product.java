package com.inventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
	
	private Long productId;
	private String productName;
	private String productBrand;
	private Integer units;
	private Double price;

}
