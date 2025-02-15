package com.example.demo.repository;

import com.example.demo.entity.ShopPhone;
import com.example.demo.mapper.ShopPhoneMapper;
import com.example.demo.repository.repositoryInterface.IShopPhoneRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopPhoneRepository extends AbstractRepository<ShopPhone> implements IShopPhoneRepository {
    @Override
    public boolean saveShopPhone(ShopPhone shopPhone,int shopid) {
        String sql = "INSERT INTO `shop_phone`(`shop_phone_number`, `shop_id`)" +
                " VALUES (?,?)";
        super.save(sql, shopPhone.getPhoneNumber(), shopid);
        return true;
    }

    @Override
    public List<ShopPhone> getShopPhoneByShopId(int id) {
        String sql = "SELECT * FROM `shop_phone` WHERE `shop_id` = ?";
        return super.findAll(sql,new ShopPhoneMapper(),id);
    }

    @Override
    public boolean deleteShopPhone(int id) {
        String sql ="DELETE FROM `shop_phone` WHERE shop_phone_id = ?";
        return super.delete(sql,id);
    }

    @Override
    public boolean updateShopPhone(ShopPhone shopPhone) {
        String sql = "UPDATE `shop_phone` SET `shop_phone_number`= ? where shop_phone_id = ?";
        return super.save(sql,shopPhone.getPhoneNumber(),shopPhone.getId());
    }


}
