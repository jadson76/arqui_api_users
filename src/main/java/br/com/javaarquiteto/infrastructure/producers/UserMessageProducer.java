package br.com.javaarquiteto.infrastructure.producers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserMessageProducer {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	private Queue queue;  	
	
	
	public void sendMessage(String message) {
		rabbitTemplate.convertAndSend(queue.getName(), message);
	}

}
