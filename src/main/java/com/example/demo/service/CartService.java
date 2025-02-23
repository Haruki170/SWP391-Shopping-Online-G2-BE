package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Cart_item;
import com.example.demo.entity.ProductAddOn;
import com.example.demo.entity.Shop;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ShopRespository;
import com.example.demo.repository.repositoryInterface.ICartRepository;
import com.example.demo.repository.repositoryInterface.IProductAddonRepository;
import com.example.demo.response.CartResponse;
import com.example.demo.service.ServiceInterface.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CartService implements ICartService {
    @Autowired
    ICartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    IProductAddonRepository productAddonRepository;
    @Autowired
    ShopRespository shopRespository;

    @Override
    public boolean addCart(Cart_item cart_item,int id) throws AppException {
        Cart cart = cartRepository.getCart(id);
        if (cart == null) {
            boolean insert = cartRepository.insertCart(id);
            System.out.println("insert cart");
            if (insert) {
                System.out.println("insert cart success");
                cart = cartRepository.getCart(id);
                cartItemRepository.insertNewCartItem(cart.getId(), cart_item);
            }
            return true;
        }else{
            List<Cart_item> items = cartItemRepository.getCartItem(cart.getId());
            for (Cart_item item : items) {
                if(item.getProduct().getId() == cart_item.getProduct().getId()){
                    if(item.getOption().getId() == cart_item.getOption().getId() && item.getAddOns().equals(cart_item.getAddOns())){
                        boolean check = cartItemRepository.insertQuantity(cart_item.getQuantity(), item.getId());

                        if(!check){
                            throw new AppException(ErrorCode.SERVER_ERR);
                        }
                        return true;
                    }
                }
            }
            boolean check = cartItemRepository.insertNewCartItem(cart.getId(), cart_item);
            System.out.println(check);
            if(!check){
                throw new AppException(ErrorCode.SERVER_ERR);
            }
            return true;
        }
    }

    @Override
    public int totalItem(int customer) {
        Cart cart = cartRepository.getCart(customer);
        int total = cartItemRepository.getTotalItem(cart.getId());
        return total;
    }

    @Override
    public boolean deleteCart(int cart_id) {
        return false;
    }

    @Override
    public boolean updateCart(Cart_item cart_item, int id) {
        return false;
    }

    @Override
    public Cart getCart(int customer_id) {
            Cart cart = cartRepository.getCart(customer_id);
            List<Cart_item> cartItems = cartRepository.getIteminCart(cart);
        System.out.println(1);
            for (Cart_item cart_item : cartItems) {

                System.out.println("Addons: ");
                String addOns = cart_item.getAddOns();
                System.out.println(addOns);
                if(!addOns.isEmpty()){
                    List<ProductAddOn> listAddons = productAddonRepository.findAllProductAddonByID(addOns);
                    cart_item.setProductAddOns(listAddons);
                }
            }
            cart.setCart_items(cartItems);
            return cart;
    }

    @Override
    public List<CartResponse> groupProducts(int customerId) {
        Cart cart = cartRepository.getCart(customerId);
        cart.setCart_items( cartRepository.getIteminCart(cart));
        Set<Shop> shops = shopRespository.findShopByCart(cart.getId());
        List<CartResponse> cartResponses = getGroupCartItem(shops,cart.getCart_items());
        return cartResponses;
    }

    public List<CartResponse> getGroupCartItem(Set<Shop> shops, List<Cart_item> cartItems){
            List<CartResponse> cartResponses = new ArrayList<>();
            for(Shop shop : shops){
                List<Cart_item> items = new ArrayList<>();
                for (Cart_item cart_item : cartItems) {
                    String addOns = cart_item.getAddOns();
                    List<ProductAddOn> listAddons = productAddonRepository.findAllProductAddonByID(addOns);
                    cart_item.setProductAddOns(listAddons);
                    if(cart_item.getProduct().getShop().getId() == shop.getId()){
                        items.add(cart_item);
                    }
                }
                cartResponses.add(new CartResponse(shop,items));
            }
            return cartResponses;
     }
}
