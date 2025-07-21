package com.engripaye.ai_chatbot_system.service;

import com.engripaye.ai_chatbot_system.model.Chat;
import com.engripaye.ai_chatbot_system.model.Message;
import com.engripaye.ai_chatbot_system.repository.ChatRepository;
import com.engripaye.ai_chatbot_system.repository.MessageRepository;
import com.engripaye.ai_chatbot_system.repository.UserRepository;
import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    @Getter
    private final UserRepository userRepository;

    public ChatService(ChatClient chatClient, MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.chatClient = chatClient;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Message processCustomerMessage(String customerId, String question) {

        // find or create chat

        Chat chat = chatRepository.findByCustomerId(customerId).stream()
                .filter(Chat::isActive)
                .findFirst()
                .orElseGet(() -> {
                    Chat newChat = new Chat();
                    newChat.setId(UUID.randomUUID().toString());
                    newChat.setCustomerId(customerId);
                    newChat.setMessageIds(new ArrayList<>());
                    return chatRepository.save(newChat);
                });

        // save customer message
        Message customerMessage = new Message();
        customerMessage.setId(UUID.randomUUID().toString());
        customerMessage.setChatId(chat.getId());
        customerMessage.setSenderId(customerId);
        customerMessage.setSenderRole("CUSTOMER");
        customerMessage.setContent(question);
        customerMessage.setTimeStamp(LocalDateTime.now());
        messageRepository.save(customerMessage);
        chat.getMessageIds().add(customerMessage.getId());
        chatRepository.save(chat);

        // Generate Ai Response
        String prompt = "You are a customer support chatbot.  Provide a concise, helpful response to: " + question;
        String aiResponse = chatClient.prompt(prompt).call().content();
        double confidence = estimateConfidence(aiResponse);

        Message aiMessage = new Message();
        aiMessage.setId(UUID.randomUUID().toString());
        aiMessage.setChatId(chat.getId());
        aiMessage.setSenderRole("AI");
        aiMessage.setContent(aiResponse);
        aiMessage.setTimeStamp(LocalDateTime.now());
        aiMessage.setConfidence(confidence);
        aiMessage.setRequireHandOff(confidence < 0.8); // Handoff if confidence <80%
        messageRepository.save(aiMessage);
        chatRepository.save(chat);

        return aiMessage;
    }

    public Message processAgentMessage(String chatId, String agentId, String content){
        Message agentMessage = new Message();
        agentMessage.setId(UUID.randomUUID().toString());
        agentMessage.setChatId(chatId);
        agentMessage.setSenderId(agentId);
        agentMessage.setSenderRole("AGENT");
        agentMessage.setContent(content);
        agentMessage.setTimeStamp(LocalDateTime.now());
        messageRepository.save(agentMessage);

        Chat chat = chatRepository.findById(chatId).orElseThrow();
        chat.setAgentId(agentId);
        chat.getMessageIds().add(agentMessage.getId());
        chatRepository.save(chat);

        return agentMessage;
    }

    public List<Message> getChatHistory(String chatId) {
        return messageRepository.findByChatId(chatId);
    }

    public List<Chat> getAgentChats(String agentId) {

        return chatRepository.findByAgentId(agentId);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    private double estimateConfidence(String response){

        return response.length() > 50 ? 0.9 : 0.7;
    }

}
