package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.repository.repositoryInterface.IAuthRepository;
import com.example.demo.util.FileUpload;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository extends AbstractRepository<Customer> implements IAuthRepository {
    @Override
    public Customer customerLogin(String email) {
        String sql = "select * from customer where customer_email = ?";
        Customer c = super.findOne(sql, new CustomerMapper(), email);
        return c;
    }

    @Override
    public boolean customerRegister(String username, String email, String password) {
       String sql = "insert into customer (customer_name, customer_email, customer_password, customer_avatar,phone, status) values (?, ?, ?, ?,NULL, 1)";
       boolean result = super.save(sql, username, email, password, "http://localhost:8080/uploads/avatar.png");
       return result;
    }

    @Override
    public int checkCustomerEmail(String email) {
        String sql = "select count(*) from customer where customer_email = ?";
        int count = super.selectCount(sql, email);
        return count;
    }

    @Override
    public int checkShopEmail(String email) {
        String sql = "select count(*) from shop_owner where shop_owner_email = ?";
        int count = super.selectCount(sql, email);
        return count;
    }

    @Override
    public int checkAdminEmail(String email) {
        String sql = "select count(*) from admin where admin_email = ?";
        int count = super.selectCount(sql, email);
        return count;
    }

    @Override
    public int CheckShipperInfo(String phone, String identity) {
        String sql = "select count(*) from shipper where phone = ? and shipper_identity = ?";
        int count = super.selectCount(sql, phone, identity);
        return count;
    }

    @Override
    public boolean ShipperRegister(String name, String phone, String identity, String password) {
        String sql = "insert into shipper (shipper_name, phone, shipper_identity,shipper_password, shipper_avatar, status) values (?, ?, ?, ?, ?, 1)";
        boolean result = super.save(sql, name, phone, identity, password, "http://localhost:8080/uploads/avatar.png");
        return result;
    }
}
