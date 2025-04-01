package com.example.demo.controller;

import com.example.demo.dto.Auth;
import com.example.demo.dto.ForgotPasswordDto;
import com.example.demo.dto.Upload;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonInclude;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerController {
    @Autowired
    Token tokenService;
    @Autowired
    CustomerService customerService;

    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/find")
    public ResponseEntity find() throws AppException {
        int id = tokenService.getIdfromToken();
        Customer customer = customerService.getCustomer(id);
        ApiResponse<Customer> api = new ApiResponse<>(200, "thanh cong", customer);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll() {
        List<Customer> list = customerService.getAllCustomers();
        ApiResponse<List<Customer>> api = new ApiResponse<>(200, "thanh cong", list);
        return ResponseEntity.ok(api);
    }

    @PreAuthorize("hasAuthority('customer')")
    @PostMapping("/upload-avatar")
    public ResponseEntity uploadAvatar(@RequestBody Upload upload) throws AppException {
        int id = tokenService.getIdfromToken();
        customerService.uploadAvatar(upload.getAvatar(), id);
        ApiResponse<List<Customer>> api = new ApiResponse<>(200, "thanh cong", null);
        return ResponseEntity.ok(api);
    }

    @PreAuthorize("hasAuthority('customer')")
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Customer customer) throws AppException {
        int id = tokenService.getIdfromToken();
        customerService.updateCustomer(id, customer);
        ApiResponse<List<Customer>> api = new ApiResponse<>(200, "thanh cong", null);
        return ResponseEntity.ok(api);
    }



    @PostMapping("/insert-customer")
    public ResponseEntity insertCustomer(@RequestBody Customer customer) throws AppException {
        customerService.addCustomer(customer);
        return ResponseEntity.ok(new ApiResponse<>(200, "Thêm người dùng thành công", customer));
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity updateStatus(@PathVariable int id, @RequestBody Customer customer) throws AppException {
        // Cập nhật trạng thái của chủ cửa hàng
        customer.setStatus(customer.getStatus()); // Thêm dòng này để cập nhật trạng thái
        customerService.updateStatus(id, customer.getStatus()); // Gọi service để thực hiện cập nhật
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật khách hàng hàng thành công", customer));
    }

    @PutMapping("/update-customer/{id}")
    public ResponseEntity updateCustomer(@PathVariable int id, @RequestBody Customer customer) throws AppException {
        customerService.updateCusManager(id, customer);
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật khách hàng thành công", customer));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestParam String email) throws AppException {
        System.out.println(email);
        customerService.forgotPassword(email);
        return ResponseEntity.ok(new ApiResponse<>(200, "Email khôi phục mật khẩu đã được gửi", email));
    }

    @PostMapping("/change-password-by-code")
    public ResponseEntity changePasswordByCode(@RequestBody ForgotPasswordDto forgotPasswordDto) throws AppException {
        customerService.resetPassword(forgotPasswordDto.getEmail(), forgotPasswordDto.getNewPassword());
        return ResponseEntity.ok(new ApiResponse<>(200, "Đổi mật khẩu thành công", null));
    }

    @PostMapping("/check-code")
    public ResponseEntity checkCode(@RequestParam String code, @RequestParam String email) throws AppException {
        System.out.println(code);
        customerService.checkCode(code);
        return ResponseEntity.ok(new ApiResponse<>(200, "Mã xác thực đã được kiểm tra", null));
    }




}
