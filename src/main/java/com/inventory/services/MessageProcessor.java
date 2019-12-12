package com.inventory.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.entity.Products;
import com.inventory.model.Product;
import com.inventory.model.MessageResponse;
import com.inventory.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageProcessor {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductService productService;
	
	@RabbitListener(queues = "products-queue")
	public void receiveMessage(Product product) {
		log.info("Message Received {}",product);
		Products products = new Products();
		products.setProduct_name(product.getProductName());
		products.setProduct_brand(product.getProductBrand());
		products.setUnits(product.getUnits());
		products.setPrice(product.getPrice());
		productRepository.saveAndFlush(products);
		log.info("Product Message Processed Succesfully");
	}
	
	@RabbitListener(queues = "products-update")
	public void updMessage(Product product) {
		log.info("Message Received to update {}",product);
		Products products = productRepository.getOne(product.getProductId());
		products.setProduct_name(product.getProductName());
		products.setProduct_brand(product.getProductBrand());
		products.setUnits(product.getUnits());
		products.setPrice(product.getPrice());
		productRepository.saveAndFlush(products);
		log.info("Product Message Updated Succesfully");
	}
	
	@RabbitListener(queues = "products-get")
	public void gtMessage(MessageResponse resp) {
		log.info("Message Received to update {}",resp);
		if(resp.getCode()==200 || resp.getCode() == 201) {
			productService.evictProducts();
			log.info("Products: "+productService.getAllProducts());
		}
		log.info("Product Message Processed Succesfully");
	}

}
