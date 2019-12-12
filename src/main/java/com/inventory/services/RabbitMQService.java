package com.inventory.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.model.Product;
import com.inventory.model.MessageResponse;

@Service
public class RabbitMQService {

	@Autowired
	private AmqpTemplate amqpTemplate;

	private String EXCHANGE = "products-exchange";

	private String ROUTING_KEY = "products";

	private String ROUTING_KEY_UPD = "products-upd";

	private String ROUTING_KEY_GET = "products-gt";

	public void sendMessage(Product product) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, product);
	}

	public void updateMessage(Product product) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_UPD, product);
	}
	
	public void getMessage(MessageResponse resp) {
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_GET, resp);
	}

}
