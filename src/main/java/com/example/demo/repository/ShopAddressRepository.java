package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.Shop;
import com.example.demo.entity.ShopAddress;

import com.example.demo.mapper.ShopAddressMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
    public class ShopAddressRepository  extends AbstractRepository<ShopAddress> {
        public List<ShopAddress> findShopAddressByShopID(int shopId) {
            List<ShopAddress> shopAddresses = new ArrayList<>();
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                connection = DBContext.getConnect();

                String sql = "SELECT sa.shop_address_id, sa.shop_address_province, sa.shop_address_district, sa.shop_address_ward, "
                        + "sa.shop_address_detail, sa.create_at, sa.update_at, s.shop_id, s.shop_name "
                        + "FROM shop_address sa JOIN shop s ON sa.shop_id = s.shop_id "
                        + "WHERE s.shop_id = ?";


                statement = connection.prepareStatement(sql);
                statement.setInt(1, shopId);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    ShopAddress shopAddress = new ShopAddress();
//                    Shop shop = new Shop();
                    shopAddress.setId(resultSet.getLong("shop_address_id"));
                    shopAddress.setProvince(resultSet.getString("shop_address_province"));
                    shopAddress.setDistrict(resultSet.getString("shop_address_district"));
                    shopAddress.setWard(resultSet.getString("shop_address_ward"));
                    shopAddress.setAddressDetail(resultSet.getString("shop_address_detail"));
//                    shop.setId(resultSet.getInt("shop_id"));
//                    shop.setName(resultSet.getString("shop_name"));
//                    shopAddress.setShop(shop);
                    shopAddresses.add(shopAddress);
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
            return shopAddresses;
        }

        public boolean saveShopAddress(ShopAddress shopAddress, int shopId) {
            String sql = "INSERT INTO `shop_address`(`shop_address_province`, `shop_address_district`, `shop_address_ward`, " +
                    "`shop_address_detail`, `shop_id`) " +
                    "VALUES (?,?,?,?,?)";
            super.save(sql, shopAddress.getProvince(),shopAddress.getDistrict(),shopAddress.getWard(),shopAddress.getAddressDetail(),shopId);
            return true;
        }

        public List<ShopAddress> findShopAddressByShopId(int shopId) {
            String sql ="select * from shop_address where shop_id = ?";
            return super.findAll(sql, new ShopAddressMapper(), shopId);
        }

        public boolean updateShopAddress(ShopAddress shopAddress) {
            String sql = "UPDATE `shop_address` SET `shop_address_province`=?, `shop_address_district`=?, " +
                    "`shop_address_ward`=?, `shop_address_detail`=? WHERE shop_address_id = ?";
            return super.save(sql, shopAddress.getProvince(), shopAddress.getDistrict(), shopAddress.getWard(), shopAddress.getAddressDetail(), shopAddress.getId());
        }

        public boolean deleteShopAddress(long shopAddressId){
            String sql = "DELETE FROM `shop_address` WHERE shop_address_id=?";
            return super.delete(sql,shopAddressId);
        }
    }
