package com.example.socket.respository;

import com.example.socket.dto.MessagesDto;
import com.example.socket.entity.ConversationEntity;
import com.example.socket.entity.MessagerEntity;
import com.example.socket.mapper.ConversationMapper;
import com.example.socket.mapper.MessagerMapper;
import com.example.socket.mapper.MessagesDtoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatReposiotry extends AbstractRepository<MessagerEntity> {
    public int searchConversation(int customerId, int shopId) {
        String sql = "SELECT id FROM conversations WHERE customer_id = ? AND shop_id = ?";
        return super.selectCount(sql, customerId, shopId);
    }

    public int insertConversation(int customerId, int shopId) {
        String sql = "INSERT INTO conversations (customer_id, shop_id) VALUES (?, ?)";
        return super.saveAndReturnId(sql, customerId, shopId);
    }

    public boolean insertMessage(MessagerEntity messagerEntity) {
        String sql = "INSERT INTO messages (conversation_id, sender_email, content) VALUES (?, ?, ?)";
        return super.save(sql, messagerEntity.getConversationId(), messagerEntity.getSenderEmail(), messagerEntity.getContent());
    }

    public List<ConversationEntity> allConversationCustomer(int id, boolean isShop) {
        String sql = """
                SELECT v.id,\s
                       s.shop_name,\s
                       s.shop_id,\s
                       s.shop_logo,
                       c.customer_id,\s
                       c.customer_name,\s
                       c.customer_avatar,
                       MAX(m.sent_at) AS last_message_time
                FROM conversations AS v
                JOIN customer AS c ON v.customer_id = c.customer_id
                JOIN shop AS s ON s.shop_id = v.shop_id
                LEFT JOIN messages AS m ON v.id = m.conversation_id
                """;
        if (isShop) {
            sql += "WHERE v.shop_id = ? ";
        } else {
            sql += "WHERE v.customer_id = ? ";
        }
        sql += "GROUP BY v.id, s.shop_name, s.shop_id, s.shop_logo, c.customer_id, c.customer_name, c.customer_avatar\n" +
                "ORDER BY last_message_time DESC;";
        AbstractRepository<ConversationEntity> a = new AbstractRepository<ConversationEntity>();
        return a.findAll(sql, new ConversationMapper(), id);
    }

    public MessagerEntity GetNewMessage(int conversationId) {
        String sql = "SELECT * \n" +
                "FROM messages \n" +
                "WHERE conversation_id = ? \n" +
                "ORDER BY sent_at DESC \n" +
                "LIMIT 1;";
        AbstractRepository<MessagerEntity> a = new AbstractRepository<>();
        return a.findOne(sql, new MessagerMapper(), conversationId);
    }

    public List<MessagesDto> getMessages(int conversationId) {
        String sql = """
                SELECT\s
                     m.id AS message_id,
                     m.content,
                     m.sent_at,
                     m.sender_email,
                     CASE\s
                     WHEN c.customer_avatar IS NOT NULL THEN c.customer_avatar
                     ELSE s.shop_logo
                     END AS avatar
                     FROM messages m
                     LEFT JOIN customer c ON m.sender_email = c.customer_email
                     LEFT JOIN shop_owner so ON m.sender_email = so.shop_owner_email
                     LEFT JOIN shop s ON so.shop_owner_id = s.shop_owner_id
                     WHERE m.conversation_id = ?
                     ORDER BY m.sent_at ASC;
                """;
        AbstractRepository<MessagesDto> a = new AbstractRepository<>();
        return a.findAll(sql, new MessagesDtoMapper(), conversationId);
    }


}
