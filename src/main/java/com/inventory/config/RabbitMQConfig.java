package com.inventory.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private String INSERT_QUEUE="products-queue";
    
    private String UPDATE_QUEUE="products-update";
    
    private String GET_QUEUE="products-get";

    private String EXCHANGE="products-exchange";

    private String ROUTING_KEY="products";
    
    private String ROUTING_KEY_UPD ="products-upd";
    
    private String ROUTING_KEY_GET ="products-gt";
    
    @Bean
    Queue insertqueue() {
        return new Queue(INSERT_QUEUE, true);
    }
    
    @Bean
    Queue updatequeue() {
        return new Queue(UPDATE_QUEUE, true);
    }
    
    @Bean
    Queue getqueue() {
        return new Queue(GET_QUEUE, true);
    }
    
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Binding binding1(Queue insertqueue, DirectExchange exchange) {
        return BindingBuilder.bind(insertqueue).to(exchange).with(ROUTING_KEY);
    }
    
    @Bean
    Binding binding2(Queue updatequeue, DirectExchange exchange) {
        return BindingBuilder.bind(updatequeue).to(exchange).with(ROUTING_KEY_UPD);
    }
    
    @Bean
    Binding binding3(Queue getqueue, DirectExchange exchange) {
        return BindingBuilder.bind(getqueue).to(exchange).with(ROUTING_KEY_GET);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
