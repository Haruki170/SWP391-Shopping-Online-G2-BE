package com.example.socket.entity;

public class MessagerEntity{
    private int id;
    private String content;
    private String date;
    private String senderEmail;
    private int conversationId;
    private int shopId;

    public MessagerEntity(int id, int conversationId, String senderEmail, String date, String content) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderEmail = senderEmail;
        this.date = date;
        this.content = content;
    }

    public int getConversationId() {
        return conversationId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public MessagerEntity() {
    }

    public MessagerEntity(int id, String content, String date, String senderEmail) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.senderEmail = senderEmail;
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

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
}
