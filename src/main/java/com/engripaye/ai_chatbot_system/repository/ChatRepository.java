package com.engripaye.ai_chatbot_system.repository;

import com.engripaye.ai_chatbot_system.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByCustomerId(String customerId);
    List<Chat> findByAgentId(String agentId);
}
