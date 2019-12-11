package com.inventory.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inventory.entity.Categories;
import com.inventory.model.Category;
import com.inventory.model.MessageResponse;

public interface CategoryService {
	
	public ResponseEntity<MessageResponse> createCategory(Category category);
	public List<Categories> getCategories();
	public ResponseEntity<MessageResponse> updateCategory(Long catId, Category category);
	public void evictCategories();
	public List<Categories> getAllCategories();
	

}
