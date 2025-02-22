package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.Auth;
import com.example.demo.entity.Admin;
import com.example.demo.exception.AppException;

import java.util.List;

public interface IAdminService {
    public Admin getAdminById(int id) throws AppException;
    public List<Admin> getAllAdmins() throws AppException;
    public boolean addAdmin(int id, Admin admin) throws AppException;
    public boolean updateAdmin(int id, Admin admin) throws AppException;
    public int updateStatus(int id, int status) throws AppException;
    public boolean deleteAdmin(int id) throws AppException;
    public boolean forgotPassword(String email) throws AppException;
    public boolean resetPassword(String code, String password) throws AppException;
    public boolean checkCode(String code) throws AppException;
    public boolean updatePassword(Auth auth, int id) throws AppException;

}
