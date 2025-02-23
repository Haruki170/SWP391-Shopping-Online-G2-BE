package com.example.socket.entity;

public class Shop {
    private int id;
    private String name;
    private String avatar;

    public Shop() {
    }

    public Shop(String name, String avatar, int id) {
        this.name = name;
        this.avatar = avatar;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
