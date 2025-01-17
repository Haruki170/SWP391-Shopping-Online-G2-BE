package com.example.demo.controller;

import com.example.demo.entity.Address;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    IAddressService addressService;
    @Autowired
    Token token;
    @PostMapping("/add")
    public ResponseEntity insert(@RequestBody Address address) throws AppException {
        int id = token.getIdfromToken();
        System.out.println(address);
        addressService.addAddress(id, address);
        ApiResponse<String> api = new ApiResponse<>(200, "success",null);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/get-list")
    public ResponseEntity getList() throws AppException {
        int id = token.getIdfromToken();
        List<Address> list = addressService.getAllAddressByUserId(id);
        ApiResponse<List<Address>> api = new ApiResponse<>(200,"success",list);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/get-address/{id}")
    public ResponseEntity getAddress(@PathVariable int id) throws AppException {
        int userId = token.getIdfromToken();
        Address a = addressService.getAddress(id, userId);
        ApiResponse<Address> api = new ApiResponse<>(200,"success",a);
        return ResponseEntity.ok(api);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) throws AppException {
        int userId = token.getIdfromToken();
        addressService.deleteAddress(userId, id);
        ApiResponse<String> api = new ApiResponse<>(200,"success",null);
        return ResponseEntity.ok(api);
    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Address address) throws AppException {
        int userId = token.getIdfromToken();
        addressService.updateAddress(userId, address);
        ApiResponse<String> api = new ApiResponse<>(200,"success",null);
        return ResponseEntity.ok(api);
    }

}
