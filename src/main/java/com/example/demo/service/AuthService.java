package com.example.demo.service;

import com.example.demo.dto.Auth;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.ShipperAuth;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Shipper;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopOwnerRepository;
import com.example.demo.repository.repositoryInterface.IAdminRepository;
import com.example.demo.repository.repositoryInterface.IAuthRepository;
import com.example.demo.repository.repositoryInterface.IShipperAuth;
import com.example.demo.repository.repositoryInterface.IShopOwnerRepository;
import com.example.demo.service.ServiceInterface.IAdminService;
import com.example.demo.service.ServiceInterface.IAuthSerive;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthSerive {
    @Autowired
    IAuthRepository authRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    Token jwtToken;
    @Autowired
    IShopOwnerRepository shopOwnerRepository;
    @Autowired
    IAdminRepository adminRepository;
    @Autowired
    IShipperAuth shipperAuthRepository;

    @Override
    public AuthResponse customerLogin(Auth auth) throws Exception {
        String token = null;
        if (auth.getEmail() == null || auth.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USER_EMAIL_EMPTY);
        }
        if (authRepository.checkCustomerEmail(auth.getEmail()) == 0) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        }

        Customer customer = authRepository.customerLogin(auth.getEmail());
        if (customer == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);

        }
        else if(customer.getStatus() == 2){
            throw new AppException(ErrorCode.CUSTOMER_BAND);
        }

        else {

            if (!passwordEncoder.matches(auth.getPassword(), customer.getPassword())) {
                throw new AppException(ErrorCode.USER_PASSWORD_WRONG);
            } else {
                token = jwtToken.generateToken(customer.getEmail(), "customer");
            }
        }
        return new AuthResponse(token, "customer",1,auth.getEmail());
    }

    @Override
    public String logout() throws AppException {
        return "";
    }

    @Override
    public String customerRegister(Auth auth) throws AppException {
        if (auth.getEmail() == null || auth.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USER_EMAIL_EMPTY);
        } else if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        } else if (!auth.getPassword().equals(auth.getConfirmPassword())) {
            throw new AppException(ErrorCode.USER_CONFIRM_PASSWORD);
        } else {
            int exits = authRepository.checkCustomerEmail(auth.getEmail());
            if (exits == 0) {
                String bpass = passwordEncoder.encode(auth.getPassword());
                if (authRepository.customerRegister(auth.getName(), auth.getEmail(), bpass)) {
                    return "Success";
                } else {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            } else {
                throw new AppException(ErrorCode.USER_EXIST);
            }
        }
    }

    @Override
    public String checkLogin(String email, String role) throws AppException {
        System.out.println(email);
        System.out.println(role);
        String responseRole = "customer";
        int check = 0;
        if (role.equals("customer")) {
            check = authRepository.checkCustomerEmail(email);
            responseRole = "customer";

        } else if (role.equals("shopOwner")) {
            check = authRepository.checkShopEmail(email);
            responseRole = "shopOwner";
        }
        else if(role.equals("superAdmin")){
            check = adminRepository.checkLogin(email,2);
            responseRole = "superAdmin";
        }
        else if(role.equals("admin")){
            check = adminRepository.checkLogin(email,1);
            responseRole = "admin";
        }
        if (check == 0) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        return responseRole;
    }

    @Override
    public AuthResponse shopOwnerLogin(Auth auth) throws Exception {
        if (auth.getEmail() == null || auth.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USER_EMAIL_EMPTY);
        } else if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        }

        else{
            ShopOwner shopOwner = shopOwnerRepository.findByEmail(auth.getEmail());
            if (shopOwner == null) {
                throw new AppException(ErrorCode.USER_NOTFOUND);
            }

            else if(shopOwner.getStatus() == 2){
                throw new AppException(ErrorCode.CUSTOMER_BAND);
            }
            else{
                if(!passwordEncoder.matches(auth.getPassword(), shopOwner.getPassword())){
                    throw new AppException(ErrorCode.USER_PASSWORD_WRONG);
                }
                else{
                    String token = jwtToken.generateToken(auth.getEmail(),"shopOwner");
                    return new AuthResponse(token, "shopOwner",shopOwner.getStatus() == 0 ? 0 : 1, auth.getEmail());
                }
            }
        }
    }
    @Override
    public  AuthResponse AdminLogin(Auth auth) throws  Exception {
        if (auth.getEmail() == null || auth.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USER_EMAIL_EMPTY);
        } else if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        }
        else{
            Admin admin = adminRepository.findByEmail(auth.getEmail());
            if(admin == null){
                throw new AppException(ErrorCode.USER_NOTFOUND);
            }
            else{
                if(!passwordEncoder.matches(auth.getPassword(), admin.getPassword())){
                    throw new AppException(ErrorCode.USER_PASSWORD_WRONG);
                }
                else{
                    String token = "";
                    if(admin.getStatus() == 0){
                        token = jwtToken.generateToken(admin.getEmail(),"admin");
                    }
                    else{
                        token = jwtToken.generateToken(admin.getEmail(),"superAdmin");
                    }
                    return new AuthResponse(token, admin.getRole()==0?"admin":"superAdmin",admin.getStatus() == 0 ? 0 : 1, auth.getEmail());
                }
            }
        }
    }
    @Override
    public String ShipperRegister(ShipperAuth auth) throws AppException {
        if (auth.getPhone() == null || auth.getPhone().isEmpty()) {
            throw new AppException(ErrorCode.SHIPPER_PHONE_EMPTY);
        }
        if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        }
        if (auth.getIdentity() == null || auth.getIdentity().isEmpty()) {
            throw new AppException(ErrorCode.SHIPPER_IDENTITY_EMPTY);
        }

        int exits = authRepository.CheckShipperInfo(auth.getPhone(), auth.getIdentity());
        if (exits == 0) {
            String bpass = passwordEncoder.encode(auth.getPassword());
            if (authRepository.ShipperRegister(auth.getName(), auth.getPhone(), auth.getIdentity(), bpass)) {
                return "Success";
            } else {
                throw new AppException(ErrorCode.SERVER_ERR);
            }
        } else {
            throw new AppException(ErrorCode.SHIPPER_EXIST);
        }
    }

    @Override
    public AuthResponse ShipperLogin(ShipperAuth auth) throws Exception {
        String token = null;
        if (auth.getPhone() == null || auth.getPhone().isEmpty()) {
            throw new AppException(ErrorCode.SHIPPER_PHONE_EMPTY);
        }
        if (shipperAuthRepository.checkShipperPhone(auth.getPhone()) == 0) {
            throw new AppException(ErrorCode.SHIPPER_NOTFOUND);
        }
        if (auth.getPassword() == null || auth.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.USER_PASSWORD_EMPTY);
        }

        Shipper shipper = shipperAuthRepository.shipperLogin(auth.getPhone());
        if (shipper == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        if(shipper.getStatus() == 1){
            throw new AppException(ErrorCode.SHIPPER_NOT_ACTIVE);
        }

        if (!passwordEncoder.matches(auth.getPassword(), shipper.getPassword())) {
            throw new AppException(ErrorCode.USER_PASSWORD_WRONG);
        }

        token = jwtToken.generateToken(shipper.getPhone(), "shipper");

        return new AuthResponse(token, "shipper",1,auth.getPhone());
    }
}
