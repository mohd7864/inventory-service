package com.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.entity.Products;
import com.inventory.model.Product;
import com.inventory.model.MessageResponse;
import com.inventory.services.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/healthcheck")
	public ResponseEntity<String> healthcheck(){
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
	
	@PostMapping("/products")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody Product product){
		
		return productService.createProduct(product);
		
    }
	
	@Cacheable(value = "product")
	@GetMapping("/products")
    public List<Products> getMessage(){
		log.info("Returning Data from DB...");
		return productService.getProducts();
		
    }
	
	@PutMapping("/products/{pId}")
    public ResponseEntity<MessageResponse> putMessage(@PathVariable String pId,@RequestBody Product product){
		
		return productService.updateProduct(Long.valueOf(pId), product);
		
    }
	
	

}
