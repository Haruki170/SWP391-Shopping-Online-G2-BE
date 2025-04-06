package com.example.demo.controller;

import com.example.demo.dto.ForgotPasswordDto;
import com.example.demo.dto.ShopOwnerPasswordDto;
import com.example.demo.entity.ShopOwner;
import com.example.demo.entity.shop_report;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.ShopOwnerReportRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ShopOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.jwt.Token;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shopOwner")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ShopOwnerController {
    @Autowired
    Token tokenService;
    @Autowired
    ShopOwnerService shopOwnerService;
    @Autowired
    Token token;
    @Autowired
    ShopOwnerReportRespository shopOwnerReportRespository;

    @GetMapping("/find")
    public ResponseEntity find() throws AppException {
        int id = tokenService.getIdfromToken();
        ShopOwner shopOwner = shopOwnerService.getShopOwnerById(id);
        ApiResponse<ShopOwner> api = new ApiResponse<>(200, "thanh cong", shopOwner);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll() throws AppException {
        List<ShopOwner> list = shopOwnerService.getAllShopOwners();
        ApiResponse<List<ShopOwner>> api = new ApiResponse<>(200, "thanh cong", list);
        return ResponseEntity.ok(api);
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity updateStatus(@PathVariable int id, @RequestBody ShopOwner shopOwner) throws AppException {
        // Cập nhật trạng thái của chủ cửa hàng
        shopOwner.setStatus(shopOwner.getStatus()); // Thêm dòng này để cập nhật trạng thái
        shopOwnerService.updateStatus(id, shopOwner.getStatus()); // Gọi service để thực hiện cập nhật
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật chủ cửa hàng thành công", shopOwner));
    }
    @DeleteMapping("/deleteShopOwner/{id}")
    public ResponseEntity deleteShopOwner(@PathVariable int id) throws AppException {
        shopOwnerService.deleteShopOwner(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Xóa chủ cửa hàng thành công", null));
    }
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<ShopOwner>> getShopOwnerById() throws AppException {
        int id = token.getIdfromToken();
        ShopOwner shopOwner = shopOwnerService.getShopOwnerById(id);
        if (shopOwner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "Chủ cửa hàng không tồn tại", null));}
        System.out.println(shopOwner);
        ApiResponse<ShopOwner> api = new ApiResponse<>(200, "Thành công", shopOwner);
        return ResponseEntity.ok(api);
    }
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ShopOwnerPasswordDto data) throws AppException {
        int id = token.getIdfromToken();
        System.out.println(id);
        System.out.println(data);
        ResponseEntity<?> response = shopOwnerService.changePassword(id, data);
        return response;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@RequestParam String email) throws AppException {
        System.out.println(email);
        shopOwnerService.forgotPassword(email);
        return ResponseEntity.ok(new ApiResponse<>(200, "Email khôi phục mật khẩu đã được gửi", email));
    }

    @PostMapping("/change-password-by-code")
    public ResponseEntity changePasswordByCode(@RequestBody ForgotPasswordDto forgotPasswordDto) throws AppException {
        System.out.println(forgotPasswordDto);
        shopOwnerService.resetPassword(forgotPasswordDto.getEmail(), forgotPasswordDto.getNewPassword());
        return ResponseEntity.ok(new ApiResponse<>(200, "Đổi mật khẩu thành công", null));
    }

    @PostMapping("/check-code")
    public ResponseEntity checkCode(@RequestParam String code, @RequestParam String email) throws AppException {
        System.out.println(code);
        shopOwnerService.checkCode(code);
        return ResponseEntity.ok(new ApiResponse<>(200, "Mã xác thực đã được kiểm tra", email));
    }

    @PostMapping("/save-response")
    public ResponseEntity saveResponse(@RequestBody shop_report report){
        shopOwnerReportRespository.saveResponse(report);
        ApiResponse apiResponse= new ApiResponse<>(200, "success");
        return ResponseEntity.ok(apiResponse);
    }
}
