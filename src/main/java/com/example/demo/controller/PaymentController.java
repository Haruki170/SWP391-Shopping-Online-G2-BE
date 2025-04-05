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

    @GetMapping("/vnpay-callback")
    public void handleVnpayCallback(@RequestParam Map<String, String> requestParams, HttpServletResponse response) throws Exception {
        // Các tham số có trong requestParams
        String amount = requestParams.get("vnp_Amount");
        String vnpTxnRef = requestParams.get("vnp_TxnRef");
        String code = requestParams.get("vnp_ResponseCode");
        String orderInfo = requestParams.get("vnp_OrderInfo");
        // Xử lý xác thực hash và kiểm tra các thông tin cần thiết
        if(orderInfo.equals("pay")){
            if(code.equals("00")){
                shopTransactionService.handleTransactionPay(vnpTxnRef);
            }
            response.sendRedirect("http://localhost:5173/seller/transaction");
        }
        else{
            String returnUrl = paymentService.savePaymentVnp(vnpTxnRef, amount, code);

            if (returnUrl != null) {
                response.sendRedirect(returnUrl);
            } else {
                response.sendRedirect("http://localhost:5173/payment-err");
            }
        }


    }

    @PostMapping("/create-cod")
    public ResponseEntity createCodPayment(@RequestBody PaymentDto paymentDto, HttpServletResponse response) throws Exception {
        String url = paymentService.savePaymentCod(paymentDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "success", url));
    }


}
