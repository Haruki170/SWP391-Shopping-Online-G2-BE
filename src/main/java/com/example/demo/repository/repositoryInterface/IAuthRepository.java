package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;
import com.example.demo.entity.User;

public interface IAuthRepository {
    public Customer customerLogin(String email);
    public boolean customerRegister(String username, String email, String password);
    public int checkCustomerEmail(String email);
    public int checkShopEmail(String email);
    public int checkAdminEmail(String email);

}
