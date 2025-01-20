package com.example.demo.controller;

import com.example.demo.entity.TransactionShop;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopTransactionRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ShopService;
import com.example.demo.service.ShopTransactionService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-transaction")
public class ShopTransactionController {
    @Autowired
    Token token;
    @Autowired
    ShopService shopService;
    @Autowired
    ShopTransactionRepository shopTransactionRepository;
    @Autowired
    ShopTransactionService shopTransactionService;

    @GetMapping("/get-all")
    public ResponseEntity getAll () throws AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);
        List<TransactionShop> list =  shopTransactionRepository.getALlTransactions(shopId);
        ApiResponse apiResponse = new ApiResponse(200, "success",list);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/save-pay")
    public ResponseEntity savePay (@RequestBody TransactionShop transactionShop) throws Exception {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);
        String url = shopTransactionService.getPayforAdmin(transactionShop);
        ApiResponse apiResponse = new ApiResponse(200, "success", url);
        return ResponseEntity.ok(apiResponse);
    }


}
