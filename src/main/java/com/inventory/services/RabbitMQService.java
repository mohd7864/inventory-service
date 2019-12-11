package com.inventory.services;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.entity.Categories;
import com.inventory.model.Category;
import com.inventory.model.MessageResponse;

@Service
public class RabbitMQService {

	@Autowired
	private AmqpTemplate amqpTemplate;

	private String EXCHANGE = "category-exchange";

	private String ROUTING_KEY = "categories";

	private String ROUTING_KEY_UPD = "categories-upd";

	private String ROUTING_KEY_GET = "categories-gt";

	public void sendMessage(Category category) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, category);
	}

	public void updateMessage(Category category) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_UPD, category);
	}
	
	public void getMessage(MessageResponse resp) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_GET, resp);
	}

	/*
	 * public void getMessage(List<Categories> categories) {
	 * amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, categories); }
	 */
}
