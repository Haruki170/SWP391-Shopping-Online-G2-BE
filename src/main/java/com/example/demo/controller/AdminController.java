package com.example.demo.controller;

import com.example.demo.dto.Auth;
import com.example.demo.dto.ForgotPasswordDto;
import com.example.demo.entity.Admin;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminService;
import com.example.demo.service.ShopOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.jwt.Token;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CrossOrigin(origins = "http://localhost:5175")
public class AdminController {
    @Autowired
    Token tokenService;
    @Autowired
    AdminService adminService;
    @Autowired
    private Token token;

    @GetMapping("/get-all")
    public ResponseEntity getAll() throws AppException {
        List<Admin> list = adminService.getAllAdmins();
        ApiResponse<List<Admin>> api = new ApiResponse<>(200, "thanh cong", list);
        return ResponseEntity.ok(api);
    }

    @PostMapping("/insert-admin")
    public ResponseEntity InsertAdmin(@RequestBody Admin admin) throws AppException {
        try {
            boolean isInserted = adminService.addAdmin(admin.getId(), admin);
            if (isInserted) {
                return ResponseEntity.ok(new ApiResponse<>(200, "Thêm quản trị thành công", admin));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Thêm quản trị thất bại", null));
            }
        } catch (AppException e) {
            if (e.getErrorCode() == ErrorCode.USER_EXIST) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Email đã tồn tại", null));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, e.getMessage(), null));
            }
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity updateStatus(@PathVariable int id, @RequestBody Admin admin) throws AppException {
        // Cập nhật trạng thái của chủ cửa hàng

        admin.setStatus(admin.getStatus()); // Thêm dòng này để cập nhật trạng thái
        adminService.updateStatus(id, admin.getStatus()); // Gọi service để thực hiện cập nhật
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật quản trị viên thành công", admin));
    }

    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) throws AppException {
        System.out.println(id);
        adminService.deleteAdmin(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Xóa chủ cửa hàng thành công", null));
    }

    @PutMapping("/update-admin/{id}")
    public ResponseEntity updateAdmin(@PathVariable int id, @RequestBody Admin admin) throws AppException {
        adminService.updateAdmin(id, admin);
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật quản trị viên thành công", admin));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestParam String email) throws AppException {
        System.out.println(email);
        adminService.forgotPassword(email);
        return ResponseEntity.ok(new ApiResponse<>(200, "Email khôi phục mật khẩu đã được gửi", email));
    }

    @PostMapping("/change-password-by-code")
    public ResponseEntity changePasswordByCode(@RequestBody ForgotPasswordDto forgotPasswordDto) throws AppException {
        System.out.println(forgotPasswordDto);
        adminService.resetPassword(forgotPasswordDto.getEmail(), forgotPasswordDto.getNewPassword());
        return ResponseEntity.ok(new ApiResponse<>(200, "Đổi mật khẩu thành công", null));
    }

    @PostMapping("/check-code")
    public ResponseEntity checkCode(@RequestParam String code, @RequestParam String email) throws AppException {
        System.out.println(code);
        adminService.checkCode(code);
        return ResponseEntity.ok(new ApiResponse<>(200, "Mã xác thực đã được kiểm tra", null));
    }

   @PostMapping("/update-password")
   public ResponseEntity updatePasswordAdmin(@RequestBody Auth auth)
           throws AppException {
        int idFromToken = token.getIdfromToken();
       // Gọi service để cập nhật mật khẩu mới
       adminService.updatePassword(auth, idFromToken);
       return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật mật khẩu thành công", null));
   }
}
