package com.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.entity.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Long> {

}
