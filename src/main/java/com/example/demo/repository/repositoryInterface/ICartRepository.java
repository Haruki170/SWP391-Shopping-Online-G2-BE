package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Cart_item;

import java.util.List;

public interface ICartRepository {
    public boolean insertCart(int userId);
    public boolean updateCart(Cart cart);
    public Cart getCart(int user_id);
    public List<Cart_item>  getIteminCart(Cart cart);
    public boolean deleteCart(Cart cart);

}
