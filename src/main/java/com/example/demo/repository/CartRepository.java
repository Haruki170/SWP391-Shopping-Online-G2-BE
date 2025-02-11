package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Cart_item;
import com.example.demo.mapper.CartItemMapper;
import com.example.demo.mapper.CartMapper;
import com.example.demo.repository.repositoryInterface.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CartRepository extends AbstractRepository<Cart> implements ICartRepository {
    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public boolean insertCart(int userId) {
            String sql = "insert into cart (customer_id) values (?)";
            boolean check = super.save(sql, userId);
            return true;
    }

    @Override
    public Cart getCart(int user_id) {
        String sql = "select * from cart where customer_id = ?";
        Cart cart = super.findOne(sql, new CartMapper(), user_id);
        return cart;
    }

    @Override
    public List<Cart_item> getIteminCart(Cart cart) {
        List<Cart_item> cart_items = cartItemRepository.getCartItem(cart.getId());
        return cart_items;
    }

    @Override
    public boolean deleteCart(Cart cart) {
        String sql = "delete from cart where customer_id = ?";
        return super.delete(sql, cart.getId());
    }

    public int checkCart(int user_id) {
        String sql = "SELECT count(*) FROM `cart` where customer_id = ?;";
        int count = super.selectCount(sql, user_id);
        return count;
    }


    @Override
    public boolean updateCart(Cart cart) {
        return false;
    }
}
