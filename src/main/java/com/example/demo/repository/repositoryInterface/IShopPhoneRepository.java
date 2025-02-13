package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ShopPhone;

import java.util.List;

public interface IShopPhoneRepository {
    public boolean saveShopPhone(ShopPhone shopPhone, int shopid);
    public List<ShopPhone> getShopPhoneByShopId(int id);
    public boolean deleteShopPhone(int id);
    public boolean updateShopPhone(ShopPhone shopPhone);
}
