package com.example.demo.service;

import com.example.demo.dto.IdentifyDto;
import com.example.demo.dto.ShopOwnerPasswordDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopOwnerRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IShopOwnerService;
import com.example.demo.util.SendMail;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ShopOwnerService implements IShopOwnerService {
    @Autowired
    ShopOwnerRepository shopOwnerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SendMail sendMail;
    @Autowired
    Token tokenService;
    @Autowired
    private Util util;

    @Override
    public ShopOwner getShopOwnerById(int id) throws AppException {
        ShopOwner shopOwner = shopOwnerRepository.findById(id);
        shopOwner.setPassword(null);
        return shopOwner;
    }

    @Override
    public List<ShopOwner> getAllShopOwners() {
        return shopOwnerRepository.findAll();
    }

    @Override
    public boolean addShopOwner(int id, ShopOwner shopOwner) throws AppException {
        if (shopOwnerRepository.checkExist(shopOwner.getEmail()) > 0) {
            throw new AppException(ErrorCode.USER_EXIST);
        }
        String oldPassword = shopOwner.getPassword();
        shopOwner.setPassword(passwordEncoder.encode(shopOwner.getPassword()));
        shopOwner.setAvatar("http://localhost:8080/uploads/avatar.png");
        boolean check = shopOwnerRepository.insert(shopOwner);
        if (check) {
            sendMail.sendMailShopRegister(shopOwner.getEmail(), "Kích khoạt tài khoản", shopOwner.getEmail(), oldPassword);
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean deleteShopOwner(int id) throws AppException {
        ShopOwner shopOwner = shopOwnerRepository.findById(id);
        if (shopOwner == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        return shopOwnerRepository.delete(shopOwner);
    }

    @Override
    public int updateStatus(int id, int status) throws AppException {
        ShopOwner shopOwner = shopOwnerRepository.findById(id);
        if (shopOwner == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        shopOwner.setStatus(status);
        return shopOwnerRepository.updateStatus(id, status);
    }

    @Override
    public boolean updateIndentify(IdentifyDto identifyDto, String frontImage, String backImage, String province, String district, String ward) throws AppException {
        boolean check = shopOwnerRepository.updateIdentify(identifyDto, frontImage, backImage,province,district,ward);
        if (!check) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public boolean forgotPassword(String email) throws AppException {
        ShopOwner s = shopOwnerRepository.findByEmail(email);
        if(s == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        // Tạo mã captcha ngẫu nhiên 8 ký tự
        String code = util.generateRandomCode( 8);

        // Lưu mã captcha vào database
        boolean check = shopOwnerRepository.forgotPasswordShopOwnerCode(code, email);
        if (!check) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        // Gửi email cho người dùng
        sendMail.sentMailRegister(email, "Mã khôi phục mật khẩu", code);

        return true;
    }

    @Override
    public boolean checkCode(String code) throws AppException {
        // Kiểm tra mã xác thực
        if (code == null || code.isEmpty() || code.length() != 8) {
            throw new AppException(ErrorCode.INVALID_CODE);
        }
        // Kiểm tra mã có tồn tại trong cơ sở dữ liệu
        int codeCount = shopOwnerRepository.checkCodeShopOwner(code);
        if (codeCount == 0) {
            throw new AppException(ErrorCode.CODE_NOT_FOUND);
        }
        return true;
    }

    @Override
    public boolean resetPassword(String email, String password) throws AppException {
        // Kiểm tra mật khẩu mới
        if (password == null || password.length() < 8) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        ShopOwner s = shopOwnerRepository.findByEmail(email);
        if(s == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        boolean check = shopOwnerRepository.updatePasswordShopOwner(passwordEncoder.encode(password), s.getId());
        if (!check) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        boolean deleteCodeCheck = shopOwnerRepository.deletePasswordShopOwnerCode(email);
        if (!deleteCodeCheck) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        return true;
    }

    public ResponseEntity<?> changePassword(int id, ShopOwnerPasswordDto data) throws AppException {
        ShopOwner shopOwner = shopOwnerRepository.findById(id);
        if (shopOwner == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(data.getOldPassword(), shopOwner.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_OLD_PASSWORD);
        }
        if (!data.getNewPassword().equals(data.getConfirmNewPassword())) {
            throw new AppException(ErrorCode.NEW_PASSWORD_MISMATCH);
        }
        // Cập nhật mật khẩu mới
        String encodedPassword = passwordEncoder.encode(data.getNewPassword());
        shopOwnerRepository.updatePassword(id, encodedPassword);
        return ResponseEntity.ok(new ApiResponse<>( 200, "Đổi mật khẩu thành công", null));
    }



}
