package com.example.demo.controller;

import com.example.demo.dto.Auth;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.CheckLoginDto;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IAuthSerive;
import jakarta.servlet.http.HttpServletRequest;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthSerive authSerive;

    @Autowired
    Token token;
    @PostMapping("/customer/sign-up")
    public ResponseEntity signUp(@RequestBody Auth user) throws AppException {
        String mess = authSerive.customerRegister(user);
        ApiResponse<String> response = new ApiResponse<>(200,mess,null);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/customer/sign-in")
    public ResponseEntity signIn(@RequestBody Auth user) throws Exception {
        AuthResponse auth = authSerive.customerLogin(user);
        ApiResponse<AuthResponse> response = new ApiResponse<>(200,"success",auth);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/check-login")
    public ResponseEntity checkLogin(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String email = token.getEmailfromToken();
        String jwt = extractJwtFromRequest(authorizationHeader);
        String role = token.extractRoleFromToken(jwt);
        String roleResponse = authSerive.checkLogin(email, role);
        CheckLoginDto response = new CheckLoginDto(email,roleResponse);
        ApiResponse<CheckLoginDto> response1 = new ApiResponse<>(200,"success",response);
        return ResponseEntity.ok(response1);
    }

    @PostMapping("/shop/login")
    public ResponseEntity shoplogin(@RequestBody Auth auth) throws Exception {

        AuthResponse authResponse = authSerive.shopOwnerLogin(auth);
        ApiResponse<AuthResponse> response = new ApiResponse<>(200,"success",authResponse);
        return ResponseEntity.ok().body(response);
    }
    private String extractJwtFromRequest(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);
            System.out.println(jwt);
            return bearerToken.substring(7); // Bỏ chữ "Bearer " để lấy token
        }
        return null;
    }

    @PostMapping("/admin/login")
    public ResponseEntity adminlogin(@RequestBody Auth auth) throws Exception {
        AuthResponse auths = authSerive.AdminLogin(auth);
        ApiResponse<AuthResponse> response = new ApiResponse<>(200,"success",auths);
        return ResponseEntity.ok().body(response);
    }
}
