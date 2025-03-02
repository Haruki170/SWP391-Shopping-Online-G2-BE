package com.example.socket.service;

import com.example.socket.entity.ConversationEntity;
import com.example.socket.entity.Customer;
import com.example.socket.entity.MessagerEntity;
import com.example.socket.entity.Shop;
import com.example.socket.respository.ChatReposiotry;
import com.example.socket.respository.CustomerRepository;
import com.example.socket.respository.ShopRepository;
import com.example.socket.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    TokenService tokenService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ChatReposiotry chatReposiotry;
    public List<ConversationEntity> getAllConversations(String token) throws Exception {

        //láy email từ token
        String email = tokenService.getSubjectFromToken(token);

        //lấy role
        String role = tokenService.extractRoleFromToken(token);

        if(role.equals("customer")) {

            //tìm customer theo email
            Customer customer = customerRepository.findByEmail(email);

            //gọi tất cả cuộc trò chuyện theo customer id sắp xếp theo tin nhắn mới nhất
            List<ConversationEntity> list = chatReposiotry.allConversationCustomer(customer.getCustomerId(), false);

            //mooxi cuộc trò chuyện thì gọi 1 tin nhắn mới nhất
            for(ConversationEntity c: list) {
                c.setMessage(chatReposiotry.GetNewMessage(c.getId()));
            }
            //trả vê lisst
            return list;
        }
        else{
            Shop shop= shopRepository.findByEmail(email);
            List<ConversationEntity> list = chatReposiotry.allConversationCustomer(shop.getId(), true);
            for(ConversationEntity c: list) {
                c.setMessage(chatReposiotry.GetNewMessage(c.getId()));
            }
            return list;

        }
    }

    public boolean insertMessage(MessagerEntity messagerEntity, String token) throws Exception {
        if(messagerEntity.getConversationId() != 0){
            chatReposiotry.insertMessage(messagerEntity);
        }
        else{
            Customer customer = customerRepository.findByEmail(tokenService.getSubjectFromToken(token));
            int id = chatReposiotry.insertConversation(customer.getCustomerId(), messagerEntity.getShopId());
            if(id != 0){
                messagerEntity.setConversationId(id);
                chatReposiotry.insertMessage(messagerEntity);
            }
        }
        return true;
    }
}
