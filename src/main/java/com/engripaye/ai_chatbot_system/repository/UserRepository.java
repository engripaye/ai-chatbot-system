package com.engripaye.ai_chatbot_system.repository;

import com.engripaye.ai_chatbot_system.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
