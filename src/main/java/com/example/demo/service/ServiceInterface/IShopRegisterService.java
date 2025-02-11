package com.example.demo.service.ServiceInterface;


import com.example.demo.entity.ShopRegister;

import java.util.List;

public interface IShopRegisterService {
    public boolean insertShop(ShopRegister shop);
    public boolean updateShop(ShopRegister shop);
    public boolean deleteShop(ShopRegister shop);
    public ShopRegister getShopByID(int id);
    public List<ShopRegister> getAllShops();
    public boolean sendMail(String email, String subject, String content);
}
