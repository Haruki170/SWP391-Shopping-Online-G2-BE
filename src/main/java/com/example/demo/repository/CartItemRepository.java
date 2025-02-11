package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.mapper.CartItemMapper;
import com.example.demo.mapper.CartProductOptionMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CartItemRepository extends AbstractRepository<Cart_item> {


    public boolean insertNewCartItem(int cart_id, Cart_item cart_item){
        int product_id = cart_item.getProduct().getId();
        int option_id = cart_item.getOption().getId();
        String sql = "INSERT INTO `cart_item`( `cart_id`, `product_id`, `product_option_id`, `quantity`, product_addons)" +
                " VALUES (?,?,?,?,?)";
        return super.save(sql, cart_id, product_id, option_id, cart_item.getQuantity(),cart_item.getAddOns());

    }


    public boolean insertQuantity(int quantity, int cart_item_id){
        Cart_item cart_item = getOneCartItem(cart_item_id);
        int quantity_old = cart_item.getQuantity();
        cart_item.setQuantity(quantity + quantity_old);
        String sql = "update cart_item set quantity = ? where cart_item_id = ?";
        return super.save(sql, cart_item.getQuantity(), cart_item_id);

    }

    public List<Cart_item> getCartItem(int cart_id){
        String sql = "SELECT c.cart_item_id, c.quantity, c.product_addons, p.*, s.*, po.product_option_id, \n" +
                "       po.product_option_name \n" +
                "FROM cart_item AS c \n" +
                "JOIN product AS p ON p.product_id = c.product_id \n" +
                "JOIN product_option AS po ON po.product_option_id = c.product_option_id \n" +
                "JOIN shop AS s ON p.shop_id = s.shop_id \n" +
                "WHERE c.cart_id = ?\n ";
        List<Cart_item> list = super.findAll(sql, new CartItemMapper(), cart_id);
        return list;
    }
    public Cart_item getOneCartItem(int cart_id){
        String sql = "select * from cart_item where cart_item_id=?";
        Cart_item cart_item = super.findOne(sql, new CartItemMapper(), cart_id);
        return cart_item;
    }


    public User getUserByEmail(String email){
        String sql = "select * from user where email = ?";
        AbstractRepository<User> userRepository = new AbstractRepository<>();
        User user = userRepository.findOne(sql, new UserMapper(), email);
        return user;
    }

    public int getTotalItem(int cart_id){

        String sql = "select count(*) from cart_item where cart_id=?";
        AbstractRepository<Cart_item> repository = new AbstractRepository<>();
        int total = repository.selectCount(sql, cart_id);
        return total;
    }

    public boolean deleteCart(int cart_item_id){
        System.out.println(cart_item_id);
        String sql = "DELETE FROM `cart_item` WHERE cart_item_id = ?";
        return super.delete(sql, cart_item_id);
    }
    public boolean updateQuantity(int cart_item_id, int quantity){
        String sql = "update cart_item set quantity = ? where cart_item_id = ?";
        return super.save(sql, quantity, cart_item_id);
    }
}
