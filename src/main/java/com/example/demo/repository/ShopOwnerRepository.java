package com.example.demo.repository;

import com.example.demo.dto.IdentifyDto;
import com.example.demo.entity.ShopOwner;
import com.example.demo.mapper.ShopOwnerMapper;
import com.example.demo.repository.repositoryInterface.IShopOwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ShopOwnerRepository extends AbstractRepository<ShopOwner> implements IShopOwnerRepository {

    @Override
    public List<ShopOwner> findAll() {
        String sql = "select * from shop_owner";
        List<ShopOwner> list = super.findAll(sql, new ShopOwnerMapper());
        return list;

    }
    @Override
    public ShopOwner findById(int id) {
        String sql = "SELECT * "
                + "FROM shop_owner WHERE shop_owner_id=?";
        ShopOwner shopOwner = super.findOne(sql, new ShopOwnerMapper(), id);
        return shopOwner;
    }

    @Override
    public boolean insert(ShopOwner shopOwner) {
        String sql = "insert into shop_owner(shop_owner_email,shop_owner_password, shop_owner_avatar, status) values(?,?,?,0)";
        super.save(sql,shopOwner.getEmail(),shopOwner.getPassword(),shopOwner.getAvatar());
        return true;
    }

    @Override
    public boolean delete(ShopOwner shopOwner) {
        String sql = "delete from shop_owner where shop_owner_id=?";
        super.delete(sql,shopOwner.getId());
        return true;
    }

    @Override
    public ShopOwner findByEmail(String email) {
        String sql = "select * from shop_owner where shop_owner_email=?";
        ShopOwner shopOwner = super.findOne(sql,new ShopOwnerMapper(), email);
        return shopOwner;
    }

    @Override
    public int checkExist(String email) {
        String sql = "select shop_owner_id from shop_owner where shop_owner_email=? union select admin_id from admin where admin_email=?";
        List<ShopOwner> list = super.findAll(sql, new ShopOwnerMapper(), email, email);
        return list.size();
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "update shop_owner set status=? where shop_owner_id=?";
        super.save(sql,status,id);
        return 1;
    }

    @Override
    public boolean updateIdentify(IdentifyDto identifyDto, String imageFront, String imageBack, String province, String district, String ward) {
        String sql = "UPDATE `shop_owner` SET `shop_owner_name` = ?, `identification_number` = ?, " +
                "`identification_image_front` = ?, `identification_image_back` = ? , shop_owner_province = ? ,shop_owner_district = ? ,shop_owner_ward = ?" +
                "WHERE `shop_owner_id` = ?;";
        return super.save(sql, identifyDto.getName(), identifyDto.getNumber(), imageFront, imageBack, province, district, ward, identifyDto.getId());
    }

    @Override
    public int updatePassword(int id, String password) {
        String sql = "UPDATE shop_owner SET shop_owner_password = ? WHERE shop_owner_id = ?";
        super.save(sql, password, id);
        return 1;
    }
    @Override
    public boolean updatePasswordShopOwner(String password, int id) {
        String sql = "UPDATE shop_owner SET shop_owner_password = ? WHERE shop_owner_id = ?";
        super.save(sql, password, id);
        return true;
    }

    @Override
    public boolean forgotPasswordShopOwnerCode(String code, String email) {
        String sql = "update shop_owner set forgot_password_code = ? where shop_owner_email = ?";
        boolean check = super.save(sql, code, email);
        return check;
    }

    @Override
    public boolean deletePasswordShopOwnerCode(String email) {
        String sql = "update shop_owner set forgot_password_code = null where shop_owner_email = ?";
        return super.save(sql, email);
    }

    @Override
    public ShopOwner resetPasswordShopOwnerByCode(String code) {
        String sql = "select * from shop_owner where forgot_password_code = ?";
        ShopOwner shopOwner = super.findOne(sql, new ShopOwnerMapper(), code);
        return shopOwner;
    }

    @Override
    public int checkCodeShopOwner(String code) {
        String sql = "SELECT count(*) FROM `shop_owner` WHERE forgot_password_code = ?";
        return super.selectCount(sql, code);
    }
}
