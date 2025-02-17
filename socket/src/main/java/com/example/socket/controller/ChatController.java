package com.example.socket.controller;

import com.example.socket.dto.ChatMessage;
import com.example.socket.dto.MessagesDto;
import com.example.socket.entity.ConversationEntity;
import com.example.socket.entity.Customer;
import com.example.socket.entity.MessagerEntity;
import com.example.socket.respository.ChatReposiotry;
import com.example.socket.respository.CustomerRepository;
import com.example.socket.service.ChatService;
import com.example.socket.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    TokenService tokenService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    ChatReposiotry chatReposiotry;
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @MessageMapping("/sendMessage")  // Đầu vào từ client// Gửi ra cho các subscribers
    public void sendMessage(MessagerEntity  messagerEntity, @Header("Authorization") String authorizationHeader) throws Exception {
        String token = getToken(authorizationHeader);
        chatService.insertMessage(messagerEntity, token);

        //nơi nhận thông báo thay đổi
        String conversationDestination = "/topic/messages";
        //gửi thay đổi về nơi nhận thông báo
        brokerMessagingTemplate.convertAndSend(conversationDestination,messagerEntity);
        // Tin nhắn sẽ được tự động gửi lại tới client
    }

    @GetMapping("/get-all-conversations")
    public ResponseEntity getAllConversations(HttpServletRequest request) throws Exception {
        //lay tokne
        String authorizationHeader = request.getHeader("Authorization");
        String token = getToken(authorizationHeader);

        //goi service truyền vào token
        List<ConversationEntity> list = chatService.getAllConversations(token);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/get-messages/{id}")
    public ResponseEntity getMessages(@PathVariable int id) throws Exception {
        List<MessagesDto> list = chatReposiotry.getMessages(id);
        return ResponseEntity.ok(list);
    }

    public String getToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Lấy token (bỏ tiền tố "Bearer ")
            return authorizationHeader.substring(7);
            // Thực hiện xác thực token và xử lý tin nhắn
            // Bạn có thể sử dụng thư viện JWT để xác thực token
        } else {
            return null;
        }
    }


}
