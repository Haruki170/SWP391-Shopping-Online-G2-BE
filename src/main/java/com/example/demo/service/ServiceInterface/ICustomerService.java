package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.Auth;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICustomerService {

    public Customer getCustomer(int id) throws AppException;
    public List<Customer> getAllCustomers() throws AppException;
    public boolean addCustomer(Customer customer) throws AppException;
    public boolean updateCustomer(int id ,Customer customer) throws AppException;
    public boolean deleteCustomer(int id) throws AppException;
    public boolean uploadAvatar(String avatar, int id) throws AppException;
    public boolean updatePassword(Auth auth, int id) throws AppException;
    public int updateStatus(int id, int status) throws AppException;
    public boolean updateCusManager(int id, Customer customer) throws AppException;
    public boolean forgotPassword(String email) throws AppException;
    public boolean resetPassword(String code, String password) throws AppException;
    public boolean checkCode(String code) throws AppException;
}
