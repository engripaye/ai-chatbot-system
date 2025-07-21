package com.engripaye.ai_chatbot_system.webSocket;

import com.corundumstudio.socketio.SocketIOServer;
import com.engripaye.ai_chatbot_system.model.Message;
import com.engripaye.ai_chatbot_system.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChatWebSocketHandler {

    private final SocketIOServer socketIOServer;
    private final ChatService chatService;
    private final ObjectMapper objectMapper;


    public ChatWebSocketHandler(SocketIOServer socketIOServer, ChatService chatService, ObjectMapper objectMapper) {
        this.socketIOServer = socketIOServer;
        this.chatService = chatService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void start() {
        socketIOServer.addEventListener("message", Map.class, (client, data, askSender) -> {
            String userId = (String) data.get("userId");
            String role = (String) data.get("role");
            String content = (String) data.get("content");
            String chatId = (String) data.get("chatId");

            Message response;
            if ("CUSTOMER".equals(role)) {
                response = chatService.processCustomerMessage(userId, content);

            } else if ("AGENT".equals(role)) {
                response = chatService.processAgentMessage(chatId, userId, content);

            }else {
                return;
            }

            socketIOServer.getBroadcastOperations().sendEvent("message", objectMapper.writeValueAsString(response));


        });
        socketIOServer.start();
    }


}
