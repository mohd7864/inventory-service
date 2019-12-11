package com.inventory.model;

import java.util.List;

import com.inventory.entity.Categories;

import lombok.Data;

@Data
public class CategoryResponse {
	
	private List<Categories> categories;

}
