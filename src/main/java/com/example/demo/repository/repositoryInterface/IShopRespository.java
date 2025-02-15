package com.example.demo.repository.repositoryInterface;

import com.example.demo.dto.ShopDto;
import com.example.demo.entity.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IShopRespository {
    public Shop findShopbyID(int id);
    public boolean addShop(Shop shop , int shopownerId);
    public boolean updateShop(Shop shop);
    public int getLastShopID();
    public Shop findShopbyOwnerId(int id);
    public Shop findDetailShop(int id);
    public Set<Shop> findShopByCart(int id);
    public List<Shop> findShopByCartItem(String id);
    public  int getShipCost(double weight, int shop_id);
    public List<ShopDto> findAllShops();
    public Shop findShopbyIDHung(int id);
}
