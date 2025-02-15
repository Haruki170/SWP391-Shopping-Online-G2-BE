package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ShopPhone;

import java.util.List;

public interface IShopPhoneRespository {
    public List<ShopPhone> FindPhoneByShopID(int shopID);
}
