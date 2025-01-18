package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Cart_item;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.CartResponse;
import com.example.demo.service.CartService;
import oracle.jdbc.proxy.annotation.Pre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    Token tokenService;
    @Autowired
    CartItemRepository cartItemRepository;

    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/view")
    public ResponseEntity view() {
        int id =tokenService.getIdfromToken();
        System.out.println(id);
        Cart cart = cartService.getCart(id);
        ApiResponse<Cart> api = new ApiResponse<>(200, "success",cart);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/view-group")
    public ResponseEntity viewGroup() {
        int id =tokenService.getIdfromToken();
        List<CartResponse> cartResponses = cartService.groupProducts(id);
        ApiResponse api = new ApiResponse<>(200, "success",cartResponses);
        return ResponseEntity.ok(api);
    }

    @PreAuthorize("hasAuthority('customer')")
    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(@RequestBody Cart_item cart_item) throws AppException {
        int id = tokenService.getIdfromToken();
        cartService.addCart(cart_item, id);
        ApiResponse<Cart> api = new ApiResponse<>(200, "success",null);
        return ResponseEntity.ok(api);
    }

    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/total-item")
    public ResponseEntity totalItem() {
        int id =tokenService.getIdfromToken();
        int total = cartService.totalItem(id);
        ApiResponse<Integer> api = new ApiResponse<>(200, "success",total);
        return ResponseEntity.ok(api);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        cartItemRepository.deleteCart(id);
        ApiResponse<Cart> api = new ApiResponse<>(200, "success",null);
        return ResponseEntity.ok(api);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestParam int action) {
            Cart_item cart_item = cartItemRepository.getOneCartItem(id);
        System.out.println(action);
            int quantity = cart_item.getQuantity();
            if(action == 0){
                quantity -= 1;
            }
            else if(action == 1){
                quantity += 1;
            }
            if(quantity == 0){
                cartItemRepository.deleteCart(id);
            }
            else{
                cartItemRepository.updateQuantity(id, quantity);
            }
            ApiResponse<Cart> api = new ApiResponse<>(200, "success",null);
            return ResponseEntity.ok(api);
    }
}
