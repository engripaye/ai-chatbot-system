package com.engripaye.ai_chatbot_system.controller;

import com.engripaye.ai_chatbot_system.model.Chat;
import com.engripaye.ai_chatbot_system.model.Message;
import com.engripaye.ai_chatbot_system.model.User;
import com.engripaye.ai_chatbot_system.service.ChatService;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chats")
    public List<Chat> getAllChats(){
        return chatService.getAllChats();
    }

    @GetMapping("/chats/{chatId}/history")
    public List<Message> getChatHistory(@PathVariable String chatId){
        return chatService.getChatHistory(chatId);
    }

    @GetMapping("/agent/{agentId}/chats")
    public List<Chat> getAgentChats(@PathVariable String agentId){
        return chatService.getAgentChats(agentId);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return chatService.saveUser(user);
    }
}
