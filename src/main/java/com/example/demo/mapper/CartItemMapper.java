package com.example.demo.mapper;

import com.example.demo.entity.Cart_item;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOption;
import com.example.demo.entity.Shop;

import java.sql.ResultSet;

public class CartItemMapper implements RowMapper<Cart_item> {
    @Override
    public Cart_item mapRow(ResultSet rs) {
        Cart_item cart_item = new Cart_item();
        try {
            cart_item.setId(rs.getInt("cart_item_id"));
            cart_item.setQuantity(rs.getInt("quantity"));

            cart_item.setAddOns(rs.getString("product_addons"));
            Product p = new Product();
            p.setId(rs.getInt("product_id"));
            p.setName(rs.getString("product_name"));
            p.setPrice((int) rs.getDouble("product_price"));
            p.setAvatar(rs.getString("product_avatar"));
            p.setDescription(rs.getString("product_desc"));
            p.setWeight(rs.getDouble("weight"));
            p.setWidth(rs.getDouble("width"));
            p.setHeight(rs.getDouble("height"));
            p.setLength(rs.getDouble("length"));


            ProductOption po = new ProductOption();
            po.setId(rs.getInt("product_option_id"));
            po.setName(rs.getString("product_option_name"));
            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            shop.setLogo(rs.getString("shop_logo"));
            p.setShop(shop);
            cart_item.setProduct(p);
            cart_item.setOption(po);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return cart_item;
    }
}
