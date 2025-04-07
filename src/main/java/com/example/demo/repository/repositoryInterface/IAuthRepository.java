package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.*;

public interface IAuthRepository {
    public Customer customerLogin(String email);
    public boolean customerRegister(String username, String email, String password);
    public int checkCustomerEmail(String email);
    public int checkShopEmail(String email);
    public int checkAdminEmail(String email);
    public int CheckShipperInfo(String phone, String identity);
    public boolean ShipperRegister(String name, String phone, String identity, String password);
}
