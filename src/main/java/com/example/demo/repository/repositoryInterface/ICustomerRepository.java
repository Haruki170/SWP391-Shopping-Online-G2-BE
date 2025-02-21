package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Customer;

import java.util.List;

public interface ICustomerRepository {
    public List<Customer> findAll() ;
    public Customer findById(int id);
    public boolean insert(Customer customer);
    public boolean update(Customer customer);
    public boolean delete(Customer customer);
    public Customer findByEmail(String email);
    public int checkExist(String email);
    public boolean uploadAvatar(String avatar,int id);
    public boolean updatePassword(String password,int id);
    public int updateStatus(int id,int status);
    public boolean updateCusManager(Customer customer);
    public boolean forgotPasswordCode(String code, String email);
    public boolean deletePasswordCode(String email);
    public Customer resetPasswordByCode(String code);
    public int checkCode(String code);

}
