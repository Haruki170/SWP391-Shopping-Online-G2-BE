package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ShopRegister;

import java.util.List;

public interface IShopRegisterRespository {
    public int insert(ShopRegister shop);
    public boolean update(ShopRegister shop);
    public ShopRegister findById(int id);
    public List<ShopRegister> getAll();
    public boolean delete(int id);
}
