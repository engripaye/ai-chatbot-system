package com.engripaye.ai_chatbot_system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String senderRole; //  CUSTOMER, AGENT, AI
    private String content;
    private LocalDateTime timeStamp;
    private Double confidence; // AI Confidence score
    private boolean requireHandOff;



}
