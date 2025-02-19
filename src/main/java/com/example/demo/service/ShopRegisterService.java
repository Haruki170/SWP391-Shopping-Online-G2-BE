package com.example.demo.service;

import com.example.demo.entity.ShopRegister;
import com.example.demo.repository.repositoryInterface.IShopRegisterRespository;
import com.example.demo.service.ServiceInterface.IShopRegisterService;
import com.example.demo.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopRegisterService implements IShopRegisterService {
    @Autowired
    IShopRegisterRespository shopRegisterRespository;
    @Autowired
    SendMail sendMail;

    @Override
    public boolean insertShop(ShopRegister shop) {
        return false;
    }

    @Override
    public boolean updateShop(ShopRegister shop) {
        return false;
    }

    @Override
    public boolean deleteShop(ShopRegister shop) {
        return false;
    }

    @Override
    public ShopRegister getShopByID(int id) {
        return  null;
    }

    @Override
    public List<ShopRegister> getAllShops() {
        return shopRegisterRespository.getAll();
    }

    @Override
    public boolean sendMail(String email, String subject, String content) {
        sendMail.sentMailRegister(email, subject, content);
        return true;
    }
}
