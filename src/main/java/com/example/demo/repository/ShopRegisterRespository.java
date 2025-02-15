package com.example.demo.repository;

import com.example.demo.entity.ShopRegister;
import com.example.demo.entity.ShopRegisterImage;
import com.example.demo.mapper.ShopRegisterImageMapper;
import com.example.demo.mapper.ShopRegisterMapper;
import com.example.demo.repository.repositoryInterface.IShopRegisterRespository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ShopRegisterRespository extends AbstractRepository<ShopRegister> implements IShopRegisterRespository {
    @Override
    public int insert(ShopRegister shop) {
        String sql = "INSERT INTO `shop_registration`( `name`, `phone`," +
                "`isOnline`, customer_id, `address`, `descriptions`, `status`, tax_number) VALUES (?,?,?,?,?,?,0,?)";
        return super.saveAndReturnId(sql, shop.getName(), shop.getPhone(), shop.getIsOnline(),shop.getCustomer().getId(),shop.getAddress(),shop.getDesc(),shop.getTaxNumber());
    }

    public boolean insertImage(String path , int id){
        String sql = "insert into `shop_registration_image` (image, shop_registration_id) values (?,?) ";
        return super.save(sql, path, id);
    }

   public List<ShopRegisterImage> getImage (int id){
        String sql = "select * from `shop_registration_image` where shop_registration_id=?";
        AbstractRepository a = new AbstractRepository();
        return a.findAll(sql, new ShopRegisterImageMapper(), id);
   }

    @Override
    public boolean update(ShopRegister shop) {
//        String sql = "UPDATE `shop_registration` SET `shop_owner_name`=?,`shop_owner_phone`=?," +
//                ",`isOnline`=?,`email`=?,`shop_address`=?,`descriptions`=?,`status`=? WHERE id = ?";
//        super.save(sql, shop.getName(), shop.getPhone(), shop.getIsOnline(),shop.getEmail()
//                ,shop.getAddress(),shop.getDesc(),shop.getStatus(), shop.getId());
        return true;
    }

    @Override
    public ShopRegister findById(int id) {
        return null;
    }

    @Override
    public List<ShopRegister> getAll() {
        String sql = "SELECT * FROM `shop_registration` as s join customer as c on c.customer_id = s.customer_id order by s.create_at desc";
        return super.findAll(sql, new ShopRegisterMapper());
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM `shop_registration` WHERE id=?";
        super.delete(sql, id);
        return true;
    }

    public boolean changeStatus (int id){
        String sql = "UPDATE `shop_registration` SET `status`=1 WHERE id=?";
        return super.save(sql, id);
    }

    public int countInactive(){
        String sql = "select count(*) from `shop_registration` where status = 0";
        return super.selectCount(sql);
    }


}
