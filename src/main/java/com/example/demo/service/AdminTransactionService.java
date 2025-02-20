package com.example.demo.service;

import com.example.demo.dto.PayForShopDto;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;
import com.example.demo.entity.TransactionAdmin;
import com.example.demo.entity.TransactionShop;
import com.example.demo.repository.AdminTransactionRepository;
import com.example.demo.repository.ShopTransactionRepository;
import com.example.demo.repository.repositoryInterface.IOrderRespository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminTransactionService {

    @Autowired
    AdminTransactionRepository adminTransactionRepository;
    @Autowired
    IOrderRespository orderRepository;
    @Autowired
    ShopTransactionRepository shopTransactionRepository;
    public boolean payForShop(PayForShopDto payForShopDto) {
        TransactionAdmin transactionAdmin = new TransactionAdmin();
        transactionAdmin.setAmount(payForShopDto.getAmount());

        if(payForShopDto.getDescription().isEmpty() == true){
            transactionAdmin.setDescription("Thanh toán đơn hàng "+payForShopDto.getCode());
        }
        else{
            transactionAdmin.setDescription(payForShopDto.getDescription());
        }

        transactionAdmin.setType(0);
        Shop shop = new Shop();
        shop.setId(payForShopDto.getShopId());
        transactionAdmin.setShop(shop);
        Order order = orderRepository.getOrderByCode(payForShopDto.getCode());
        transactionAdmin.setOrder(order);
        transactionAdmin.setIsPaid(0);

        TransactionShop transactionShop = new TransactionShop();
        transactionShop.setShop(shop);
        transactionShop.setAmount(payForShopDto.getAmount());
        transactionShop.setType(1);
        transactionShop.setIsCommisson(2);
        transactionShop.setDescription(transactionAdmin.getDescription());
        transactionShop.setOrder(transactionAdmin.getOrder());

        shopTransactionRepository.savePay(transactionShop);

        adminTransactionRepository.savePayForShop(transactionAdmin);
        adminTransactionRepository.updateIsPaid(payForShopDto.getTid());
        return true;
    }
}
