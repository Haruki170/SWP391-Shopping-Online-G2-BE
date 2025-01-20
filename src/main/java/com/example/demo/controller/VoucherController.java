package com.example.demo.controller;

import com.example.demo.entity.Voucher;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
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

    @GetMapping("/get-all")
    public ResponseEntity getAll() throws Exception {
        List<Voucher> list = voucherService.findAllVoucher();
        ApiResponse<List<Voucher>> api = new ApiResponse<>(200, "thanh cong", list);
        return ResponseEntity.ok(api);
    }

    @PostMapping("/add-voucher")
    public ResponseEntity addVoucher(@RequestBody Voucher voucher) throws Exception {
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
}
