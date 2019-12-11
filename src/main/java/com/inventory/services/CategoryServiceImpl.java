package com.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inventory.entity.Categories;
import com.inventory.model.Category;
import com.inventory.model.MessageResponse;
import com.inventory.repositories.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	RabbitMQService amqService;

	@Autowired
	CategoryRepository repository;

	@Override
	public ResponseEntity<MessageResponse> createCategory(Category category) {
		amqService.sendMessage(category);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			log.error("Exception Occurred: " + ex);
		}
		MessageResponse resp = new MessageResponse();
		resp.setCode(201);
		resp.setMsg("Category is created");
		amqService.getMessage(resp);
		return new ResponseEntity<>(resp, HttpStatus.CREATED);
	}

	@Override
	public List<Categories> getCategories() {

		return repository.findAll();

	}

	@CacheEvict(value = "category")
	public void evictCategories() {
		log.info("Evict post-top");
	}

	@Override
	public ResponseEntity<MessageResponse> updateCategory(Long catId, Category category) {
		category.setCatId(catId);
		amqService.updateMessage(category);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			log.error("Exception Occurred: " + ex);
		}
		MessageResponse resp = new MessageResponse();
		resp.setCode(200);
		resp.setMsg("Category is updated");
		amqService.getMessage(resp);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@Cacheable(value = "category")
	public List<Categories> getAllCategories() {
		log.info("Reloading the data in Cache...");
		return this.getCategories();
	}

}
