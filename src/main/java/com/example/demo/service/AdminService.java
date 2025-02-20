package com.example.demo.service;

import com.example.demo.dto.Auth;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.ServiceInterface.IAdminService;
import com.example.demo.util.SendMail;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService implements IAdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SendMail sendMail;
    @Autowired
    private Util util;

    @Override
    public Admin getAdminById(int id) throws AppException {
        Admin admin = adminRepository.findAdminById(id);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return admin;
    }

    @Override
    public List<Admin> getAllAdmins() throws AppException {
        return adminRepository.findAllAdmin();
    }

    @Override
    public boolean addAdmin(int id, Admin admin) throws AppException {
        if (adminRepository.checkExist(admin.getEmail()) > 0) {
            throw new AppException(ErrorCode.USER_EXIST);
        }
        String oldPassword = admin.getPassword();
        admin.setPassword(passwordEncoder.encode(oldPassword));
        boolean check = adminRepository.insert(admin);
        if (check) {
            sendMail.sentMailRegister(admin.getEmail(), "kích hoạt tài khoản", oldPassword);
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean updateAdmin(int id, Admin admin) throws AppException {
        if (adminRepository.findAdminById(id) == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        String oldPassword = admin.getPassword();
        admin.setPassword(passwordEncoder.encode(oldPassword));
        boolean check = adminRepository.update(admin);
        if (check) {
            sendMail.sentMailRegister(admin.getEmail(), "thay đổi mật khẩu", oldPassword);
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public int updateStatus(int id, int status) throws AppException {
        Admin admin = adminRepository.findAdminById(id);
        if (admin == null) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        admin.setStatus(status);
        return adminRepository.updateStatus(id, status);
    }

    @Override
    public boolean deleteAdmin(int id) throws AppException {
            Admin admin = adminRepository.findAdminById(id);
            if (admin == null) {
                throw new AppException(ErrorCode.USER_NOTFOUND);
            }
            return adminRepository.delete(admin);

    }

    @Override
    public boolean forgotPassword(String email) throws AppException {
        Admin a = adminRepository.findByEmail(email);
        if(a == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        // Tạo mã captcha ngẫu nhiên 8 ký tự
        String code = util.generateRandomCode( 8);

        // Lưu mã captcha vào database
        boolean check = adminRepository.forgotPasswordAdminCode(code, email);
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
        int codeCount = adminRepository.checkCodeAdmin(code);
        if (codeCount == 0) {
            throw new AppException(ErrorCode.CODE_NOT_FOUND);
        }
        return true;
    }

    @Override
    public boolean updatePassword(Auth auth, int id) throws AppException {
        String password = auth.getPassword();
        String oldPassword = auth.getOldPassword();
        String comfirmPassword = auth.getConfirmPassword();

        Admin a = adminRepository.findAdminById(id);

        if(!passwordEncoder.matches(oldPassword, a.getPassword())) {
            throw new AppException(ErrorCode.USER_OLD_PASSWORD);
        }
        if(!comfirmPassword.equals(password)) {
            throw new AppException(ErrorCode.USER_CONFIRM_PASSWORD);
        }
        if(passwordEncoder.matches(password, a.getPassword())) {
            throw new AppException(ErrorCode.DUPLICATE_PASSWORD);
        }
        String newPassword = passwordEncoder.encode(password);
        boolean check = adminRepository.updatePassword(newPassword,id);
        if(!check) {
            throw  new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public boolean resetPassword(String email, String password) throws AppException {
        // Kiểm tra mật khẩu mới
        if (password == null || password.length() < 8) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        Admin a = adminRepository.findByEmail(email);
        if(a == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        boolean check = adminRepository.updatePassword(passwordEncoder.encode(password), a.getId());
        if (!check) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        boolean deleteCodeCheck = adminRepository.deletePasswordAdminCode(email);
        if (!deleteCodeCheck) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        return true;
    }
}
