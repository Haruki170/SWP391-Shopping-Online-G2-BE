package com.example.demo.service;

import com.example.demo.dto.Auth;
import com.example.demo.entity.Customer;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.ServiceInterface.ICustomerService;
import com.example.demo.util.SendMail;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService implements ICustomerService {

    @Autowired
    Util util;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private SendMail sendMail;



    @Override
    public Customer getCustomer(int id) throws AppException {
        Customer c = customerRepository.findById(id);
        if(c == null){
            throw new AppException(ErrorCode.CUSTOMER_NOTFOUND);
        }
        c.setPassword(null);
        return c;
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean addCustomer(Customer customer) throws AppException {
        if (customerRepository.checkExist(customer.getEmail()) > 0) {
            throw new AppException(ErrorCode.USER_EXIST);
        }
        String oldPassword = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(oldPassword));
        boolean check = customerRepository.insert(customer);
        if (check) {
            sendMail.sentMailRegister(customer.getEmail(),"Mật khẩu đăng ký: ", oldPassword);
            return true;
        }
        else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean updateCustomer(int id, Customer customer) throws AppException {
        Customer c = customerRepository.findById(id);
        if(c == null){
            throw new AppException(ErrorCode.CUSTOMER_NOTFOUND);
        }
        c.setName(customer.getName());
        if(customer.getPhoneNumber() != null){
            c.setPhoneNumber(customer.getPhoneNumber());
        }
        boolean check = customerRepository.update(c);
        if (!check) {
            throw  new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public boolean deleteCustomer(int id) {
        return false;
    }

    @Override
    public boolean uploadAvatar(String avatar, int id) throws AppException {
        boolean upload = customerRepository.uploadAvatar(avatar, id);
        if(!upload) {
            throw  new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public boolean updatePassword(Auth auth, int id) throws AppException {
        String password = auth.getPassword();
        String oldPassword = auth.getOldPassword();
        String comfirmPassword = auth.getConfirmPassword();

        Customer c= customerRepository.findById(id);

        if(!passwordEncoder.matches(oldPassword, c.getPassword())) {
            throw new AppException(ErrorCode.USER_OLD_PASSWORD);
        }
        if(!comfirmPassword.equals(password)) {
            throw new AppException(ErrorCode.USER_CONFIRM_PASSWORD);
        }
        if(passwordEncoder.matches(password, c.getPassword())) {
            throw new AppException(ErrorCode.DUPLICATE_PASSWORD);
        }


        String newPassword = passwordEncoder.encode(password);
        boolean check = customerRepository.updatePassword(newPassword,id);
        if(!check) {
            throw  new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public int updateStatus(int id, int status) throws AppException {
        Customer c = customerRepository.findById(id);
        if(c == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        c.setStatus(status);
        return customerRepository.updateStatus(id, status);
    }

    @Override
    public boolean updateCusManager(int id, Customer customer) throws AppException {
        if (customerRepository.findById(id) == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        String oldPassword = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(oldPassword));
        boolean check = customerRepository.update(customer);
        if (check) {
            sendMail.sentMailRegister(customer.getEmail(), "thay đổi mật khẩu", oldPassword);
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean forgotPassword(String email) throws AppException {
        Customer c = customerRepository.findByEmail(email);
        if(c == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }
        
        // Tạo mã captcha ngẫu nhiên 8 ký tự
        String code = util.generateRandomCode( 8);
        
        // Lưu mã captcha vào database
        boolean check = customerRepository.forgotPasswordCode(code, email);
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
        int codeCount = customerRepository.checkCode(code);
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

        Customer c = customerRepository.findByEmail(email);
        if(c == null) {
            throw new AppException(ErrorCode.USER_NOTFOUND);
        }

        boolean check = customerRepository.updatePassword(passwordEncoder.encode(password), c.getId());
        if (!check) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        boolean deleteCodeCheck = customerRepository.deletePasswordCode(email);
        if (!deleteCodeCheck) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }

        return true;
    }


}
