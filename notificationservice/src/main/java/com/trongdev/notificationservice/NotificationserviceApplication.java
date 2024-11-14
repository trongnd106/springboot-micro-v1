package com.trongdev.notificationservice;

import org.apache.kafka.common.protocol.Message;
import org.apache.tomcat.util.net.WriteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class NotificationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

	@Bean
	public Consumer<String> consumeMessage(){
		return message -> {
			System.out.println("message: " + message);
		};
	}
}
