package com.example.socket.mapper;

import com.example.socket.dto.MessagesDto;

import java.sql.ResultSet;

public class MessagesDtoMapper implements RowMapper<MessagesDto> {

    @Override
    public MessagesDto mapRow(ResultSet rs) {
        MessagesDto messagesDto = new MessagesDto();
        try {
            messagesDto.setId(rs.getInt("message_id"));
            messagesDto.setAvatar(rs.getString("avatar"));
            messagesDto.setSender(rs.getString("sender_email"));
            messagesDto.setContent(rs.getString("content"));
            messagesDto.setDate(rs.getString("sent_at"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return messagesDto;
    }
}
