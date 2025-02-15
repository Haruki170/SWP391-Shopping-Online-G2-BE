package com.example.demo.service;

import com.example.demo.entity.ShopAddress;
import com.example.demo.repository.ShopAddressRepository;
import com.example.demo.service.ServiceInterface.IShopAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopAddressService implements IShopAddressService {

    @Autowired
    private ShopAddressRepository shopAddressRepository;

    @Override
    public List<ShopAddress> findShopAddressByShopId(int shopId) {
        return shopAddressRepository.findShopAddressByShopID(shopId);
    }

    @Override
    public boolean updateShopAddress(ShopAddress shopAddress) {
        boolean check = shopAddressRepository.updateShopAddress(shopAddress);
        return check;
    }


}
