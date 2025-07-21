package com.engripaye.ai_chatbot_system.repository;

import com.engripaye.ai_chatbot_system.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByChatId(String chatId);
}
