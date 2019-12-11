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

import com.inventory.entity.Categories;
import com.inventory.model.Category;
import com.inventory.model.CategoryResponse;
import com.inventory.model.MessageResponse;
import com.inventory.services.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoryController {
	
	@Autowired
	CategoryService catService;
	
	@GetMapping("/healthcheck")
	public ResponseEntity<String> healthcheck(){
		return new ResponseEntity<>("Success",HttpStatus.OK);
	}
	
	@PostMapping("/categories")
    public ResponseEntity<MessageResponse> postMessage(@RequestBody Category category){
		
		return catService.createCategory(category);
		
    }
	
	@Cacheable(value = "category")
	@GetMapping("/categories")
    public List<Categories> getMessage(){
		//catService.evictCategorie();
		log.info("Returning Data from DB...");
		return catService.getCategories();
		
    }
	
	@PutMapping("/categories/{catId}")
    public ResponseEntity<MessageResponse> putMessage(@PathVariable String catId,@RequestBody Category category){
		
		return catService.updateCategory(Long.valueOf(catId), category);
		
    }
	
	

}
