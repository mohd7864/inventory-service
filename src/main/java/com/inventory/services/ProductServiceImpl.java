package com.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inventory.entity.Products;
import com.inventory.model.Product;
import com.inventory.model.MessageResponse;
import com.inventory.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	RabbitMQService amqService;

	@Autowired
	ProductRepository repository;

	@Override
	public ResponseEntity<MessageResponse> createProduct(Product product) {
		amqService.sendMessage(product);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			log.error("Exception Occurred: " + ex);
		}
		MessageResponse resp = new MessageResponse();
		resp.setCode(201);
		resp.setMsg("Product is created");
		amqService.getMessage(resp);
		return new ResponseEntity<>(resp, HttpStatus.CREATED);
	}

	@Override
	public List<Products> getProducts() {

		return repository.findAll();

	}

	@CacheEvict(value = "product")
	public void evictProducts() {
		log.info("Products Evicted");
	}

	@Override
	public ResponseEntity<MessageResponse> updateProduct(Long pId, Product product) {
		product.setProductId(pId);
		amqService.updateMessage(product);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			log.error("Exception Occurred: " + ex);
		}
		MessageResponse resp = new MessageResponse();
		resp.setCode(200);
		resp.setMsg("Product is updated");
		amqService.getMessage(resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@Cacheable(value = "product")
	public List<Products> getAllProducts() {
		log.info("Reloading the data in Cache...");
		return this.getProducts();
	}

}
