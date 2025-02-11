package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.ShopAddress;

import java.util.List;

public interface IShopAddressService {
    List<ShopAddress> findShopAddressByShopId(int shopId);
    boolean updateShopAddress(ShopAddress shopAddress);
}
