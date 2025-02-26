package com.example.socket.mapper;

import com.example.socket.entity.MessagerEntity;

import javax.annotation.processing.Messager;
import java.sql.ResultSet;

public class MessagerMapper implements  RowMapper<MessagerEntity>{
    @Override
    public MessagerEntity mapRow(ResultSet rs) {
        MessagerEntity messagerEntity = new MessagerEntity();
        try {
            messagerEntity.setId(rs.getInt("id"));
            messagerEntity.setContent(rs.getString("content"));
            messagerEntity.setSenderEmail(rs.getString("sender_email"));
            messagerEntity.setDate(rs.getString("sent_at"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return messagerEntity;
    }
}
