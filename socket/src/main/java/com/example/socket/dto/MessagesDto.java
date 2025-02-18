package com.example.socket.dto;

public class MessagesDto {
    private int id;
    private String content;
    private String sender;
    private String avatar;
    private String date;

    public MessagesDto() {
    }

    public MessagesDto(int id, String content, String sender, String avatar, String date) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.avatar = avatar;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
