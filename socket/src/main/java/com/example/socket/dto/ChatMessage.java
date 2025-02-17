package com.example.socket.dto;



public class ChatMessage {
    private String sender;
    private String content;
    private boolean yourself;
    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, boolean yourself) {
        this.sender = sender;
        this.content = content;
        this.yourself = yourself;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isYourself() {
        return yourself;
    }

    public void setYourself(boolean yourself) {
        this.yourself = yourself;
    }
}
