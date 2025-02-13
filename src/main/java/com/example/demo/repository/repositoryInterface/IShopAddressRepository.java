package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ShopAddress;

public interface IShopAddressRepository {
    public boolean addShopAddress(ShopAddress shopAddress,int shopId);
}
