package com.inventory.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inventory.entity.Products;
import com.inventory.model.Product;
import com.inventory.model.MessageResponse;

public interface ProductService {
	
	public ResponseEntity<MessageResponse> createProduct(Product product);
	public List<Products> getProducts();
	public ResponseEntity<MessageResponse> updateProduct(Long catId, Product product);
	public void evictProducts();
	public List<Products> getAllProducts();
	

}
