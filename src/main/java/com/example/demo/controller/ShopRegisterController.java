package com.example.demo.controller;

import com.example.demo.dto.Mail;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopRegister;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopRegisterRespository;
import com.example.demo.repository.repositoryInterface.IShopRegisterRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IShopRegisterService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shop-register")
public class ShopRegisterController {
    @Autowired
    IShopRegisterService shopRegisterService;
    @Autowired
    Token token;
    @Autowired
    ShopRegisterRespository shopRegisterRespository;
    @Autowired
    FileUpload fileUpload;
    

    @GetMapping("/find-add")
    public ResponseEntity getAll() {
        List<ShopRegister> list = shopRegisterService.getAllShops();
        for (ShopRegister shopRegister : list) {
            shopRegister.setImages(shopRegisterRespository.getImage(shopRegister.getId()));
        }
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", list);
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/mail")
    public ResponseEntity mail(@RequestBody Mail mail) {
        shopRegisterService.sendMail(mail.getEmail(), mail.getSubject(), mail.getContent());
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/send-mail")
    public ResponseEntity sendMail(@RequestBody Mail mail) {
        shopRegisterService.sendMail(mail.getEmail(), mail.getSubject(), mail.getContent());
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", null);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable int id) {
        shopRegisterRespository.changeStatus(id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", null);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        shopRegisterRespository.delete(id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/count-inactive")
    public ResponseEntity countInactive() {
        int count = shopRegisterRespository.countInactive();
        ApiResponse<String> apiResponse = new ApiResponse(200, "sucess", count);
        return ResponseEntity.ok(apiResponse);
    }

}
