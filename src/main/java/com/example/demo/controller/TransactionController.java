package com.example.demo.controller;
import com.example.demo.service.ServiceInterface.IShopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private IShopOwnerService shopOwnerService;

}
