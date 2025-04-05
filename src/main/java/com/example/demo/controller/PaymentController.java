package com.example.demo.controller;

import com.example.demo.dto.PaymentDto;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.PaymentService;
import com.example.demo.service.ShopTransactionService;
import com.example.demo.service.VnpService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    VnpService vnpService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    Token tokenService;
    @Autowired
    ShopTransactionService shopTransactionService;
    @PostMapping("/create-vnpay")
    public ResponseEntity createVpnPayment(@RequestBody PaymentDto paymentDto) throws Exception {
        String url = vnpService.getUrlVnPay(paymentDto);
        ApiResponse<String> api = new ApiResponse<>(200, "success", url);
        return ResponseEntity.ok(api);
    }



    @PostMapping("/create-cod")
    public ResponseEntity createCodPayment(@RequestBody PaymentDto paymentDto, HttpServletResponse response) throws Exception {
        String url = paymentService.savePaymentCod(paymentDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "success", url));
    }


}
