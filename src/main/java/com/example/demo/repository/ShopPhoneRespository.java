package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.ShopAddress;
import com.example.demo.entity.ShopPhone;
import com.example.demo.repository.repositoryInterface.IShopPhoneRespository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopPhoneRespository extends AbstractRepository implements IShopPhoneRespository {
    public List<ShopPhone> FindPhoneByShopID(int shopID){
        List<ShopPhone> shopPhones = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnect();

            String sql = "SELECT sp.shop_phone_id, sp.shop_phone_number, sp.create_at, sp.update_at, "
                    + "s.shop_id, s.shop_name "
                    + "FROM shop_phone sp JOIN shop s ON sp.shop_id = s.shop_id "
                    + "WHERE s.shop_id = ?";



            statement = connection.prepareStatement(sql);
            statement.setInt(1, shopID);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
               ShopPhone shopPhone = new ShopPhone();
                shopPhone.setId(resultSet.getInt("shop_phone_id"));
                shopPhone.setPhoneNumber(resultSet.getString("shop_phone_number"));

                shopPhones.add(shopPhone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shopPhones;
    }
    }

