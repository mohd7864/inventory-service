package com.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.entity.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {

}
