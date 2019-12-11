package com.inventory.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.entity.Categories;
import com.inventory.model.Category;
import com.inventory.model.MessageResponse;
import com.inventory.repositories.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageProcessor {
	
	@Autowired
	CategoryRepository catRepository;
	@Autowired
	CategoryService catservice;
	
	//private static final Logger log = LoggerFactory.getLogger(MessageProcessor.class);
	
	@RabbitListener(queues = "category-queue")
	public void receiveMessage(Category category) {
		log.info("Message Received {}",category);
		Categories categories = new Categories();
		categories.setCategory_name(category.getCategoryName());
		catRepository.saveAndFlush(categories);
		log.info("Category Message Processed Succesfully");
	}
	
	@RabbitListener(queues = "category-update")
	public void updMessage(Category category) {
		log.info("Message Received to update {}",category);
		Categories cat = catRepository.getOne(category.getCatId());
		cat.setCategory_name(category.getCategoryName());
		catRepository.saveAndFlush(cat);
		log.info("Category Message Updated Succesfully");
	}
	
	@RabbitListener(queues = "category-get")
	public void gtMessage(MessageResponse resp) {
		log.info("Message Received to update {}",resp);
		if(resp.getCode()==200 || resp.getCode() == 201) {
			catservice.evictCategories();
			log.info("Categories: "+catservice.getAllCategories());
		}
		log.info("Category Message Processed Succesfully");
	}

}
