package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;

import java.util.List;

public interface IAdminRepository {
    public List<Admin> findAllAdmin();
    public Admin findAdminById(int id);
    public boolean update(Admin admin);
    public boolean delete(Admin admin);
    public int checkExist(String email);
    public Admin findByEmail(String email);
    public int updateStatus(int id, int status);
    public boolean forgotPasswordAdminCode(String code, String email);
    public boolean deletePasswordAdminCode(String email);
    public Admin resetPasswordAdminByCode(String code);
    public int checkCodeAdmin(String code);
    public boolean updatePassword(String password,int id);
    public int checkLogin(String email, int role);
}
