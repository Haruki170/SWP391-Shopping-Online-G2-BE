package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.Auth;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.ShipperAuth;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;

public interface IAuthSerive  {
    public AuthResponse customerLogin(Auth auth) throws Exception;
    public String logout() throws AppException;
    public String customerRegister(Auth auth) throws AppException;
    public String checkLogin(String email, String role) throws AppException;
    public AuthResponse shopOwnerLogin(Auth auth) throws Exception;
    public  AuthResponse AdminLogin(Auth auth) throws  Exception ;
    public  String ShipperRegister(ShipperAuth auth) throws  AppException ;
    public  AuthResponse ShipperLogin(ShipperAuth auth) throws  Exception ;
}
