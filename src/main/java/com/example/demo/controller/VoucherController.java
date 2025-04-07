package com.example.demo.controller;

import com.example.demo.entity.Shop;
import com.example.demo.entity.Voucher;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ShopService;
import com.example.demo.service.VoucherService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voucher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherController {
    @Autowired
    Token tokenService;
    @Autowired
    VoucherService voucherService;
    @Autowired
    ShopService shopService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Voucher>>> getAll() throws Exception {
        int shopOwnerId = tokenService.getIdfromToken(); // Get authenticated user's ID
        Shop shop = shopService.getShopByOwnerId(shopOwnerId); // Get shop by owner ID

        List<Voucher> list = voucherService.findVouchersByShopId(shop.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Thành công", list));
    }

    @PostMapping("/add-voucher")
    public ResponseEntity addVoucher(@RequestBody Voucher voucher) throws Exception {
        int shopOwnerId = tokenService.getIdfromToken();
        Shop shop = shopService.getShopByOwnerId(shopOwnerId);
        voucher.setShop(shop);
        voucherService.addVoucher(voucher);
        return ResponseEntity.ok(new ApiResponse<>(200, "thanh cong", null));
    }

    @PutMapping("/update-voucher/{id}")
    public ResponseEntity updateVoucher(@PathVariable int id, @RequestBody Voucher voucher) throws Exception {
        voucherService.updateVoucher(id, voucher);
        return ResponseEntity.ok(new ApiResponse<>(200, "Cập nhật voucher thành công", voucher));
    }

    @DeleteMapping("/delete-voucher/{id}")
    public ResponseEntity deleteVoucher(@PathVariable int id) throws Exception {
        System.out.println(id);
        voucherService.deleteVoucher(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Xóa voucher thành công", null));
    }
    @GetMapping("/get-valid")
    public ResponseEntity<ApiResponse<List<Voucher>>> getValidVouchers(@RequestParam("shopId") int shopId) throws Exception {
        List<Voucher> validVouchers = voucherService.findValidVouchersByShopId(shopId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Lấy danh sách voucher còn hiệu lực thành công", validVouchers));
    }
}
