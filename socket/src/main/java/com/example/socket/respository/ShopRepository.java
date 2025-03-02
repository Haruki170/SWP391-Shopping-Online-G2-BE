package com.example.socket.respository;

import com.example.socket.entity.MessagerEntity;
import com.example.socket.entity.Shop;
import com.example.socket.mapper.ShopMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopRepository extends AbstractRepository<Shop> {
    public Shop findByEmail(String email) {
        String sql = "SELECT shop.shop_id, shop.shop_name, shop.shop_logo FROM shop_owner AS so\n" +
                "JOIN shop on shop.shop_owner_id = so.shop_owner_id \n" +
                "WHERE so.shop_owner_email = ?";
        return super.findOne(sql, new ShopMapper(), email);
    }


}
