package com.inventory.model;

import java.util.List;

import com.inventory.entity.Products;

import lombok.Data;

@Data
public class ProductResponse {
	
	private List<Products> products;

}
