package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Cart_item;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.response.CartResponse;

import java.util.List;

public interface ICartService {
    public boolean addCart(Cart_item cart_item, int id) throws AppException;
    public int totalItem(int cart_id);
    public boolean deleteCart(int cart_id);
    public boolean updateCart(Cart_item cart_item, int id);
    public Cart getCart(int cart_id);
    public List<CartResponse> groupProducts(int cart_id);
}
