package com.engripaye.ai_chatbot_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;
    private String customerId;
    private String agentId;
    private List<String> messageIds;
    private boolean active;

}
