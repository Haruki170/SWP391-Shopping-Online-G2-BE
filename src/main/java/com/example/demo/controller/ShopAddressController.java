package com.example.demo.controller;

import com.example.demo.entity.ShopAddress;
import com.example.demo.service.ShopAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-address")
public class ShopAddressController {
    @Autowired
    private ShopAddressService shopAddressService;
    @GetMapping("/{shopId}")
    public ResponseEntity<List<ShopAddress>> getShopAddressesByShopId(@PathVariable int shopId) {
        List<ShopAddress> shopAddresses = shopAddressService.findShopAddressByShopId(shopId);
        if (shopAddresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shopAddresses);
    }
}
