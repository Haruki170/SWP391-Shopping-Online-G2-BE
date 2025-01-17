package com.example.demo.controller;

import com.example.demo.dto.DailyTransaction;
import com.example.demo.dto.PayForShopDto;
import com.example.demo.entity.TransactionAdmin;
import com.example.demo.repository.AdminTransactionRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-transaction")
public class AdminTransactionController {
    @Autowired
    AdminTransactionRepository adminTransactionRepository;
    @Autowired
    AdminTransactionService adminTransactionService;
    @GetMapping("/get-all-shop/{id}")
    public ResponseEntity getAllByShop(@PathVariable int id) {
        List<TransactionAdmin> list = adminTransactionRepository.getAllTransactionsByShop(id);
        ApiResponse apiResponse = new ApiResponse(200,"success",list);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        List<TransactionAdmin> list = adminTransactionRepository.getAllTransactions();
        ApiResponse apiResponse = new ApiResponse(200,"success",list);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/pay-for-shop")
    public ResponseEntity payForShop(@RequestBody PayForShopDto payForShopDto) {
        adminTransactionService.payForShop(payForShopDto);
        ApiResponse apiResponse = new ApiResponse(200,"success",null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-daily")
    public ResponseEntity getDaily(){
        System.out.println(1);
        List<DailyTransaction> list = adminTransactionRepository.getDaily();
        ApiResponse apiResponse = new ApiResponse(200,"success",list);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/day-income")
    public ResponseEntity getDayIncome(){
        int income = adminTransactionRepository.getIncomeDay();
        ApiResponse apiResponse = new ApiResponse(200,"success",income);
        return ResponseEntity.ok(apiResponse);
    }
}
