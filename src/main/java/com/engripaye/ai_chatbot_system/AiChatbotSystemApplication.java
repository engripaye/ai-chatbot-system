package com.engripaye.ai_chatbot_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-secrets.properties")
public class AiChatbotSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiChatbotSystemApplication.class, args);
	}

}
