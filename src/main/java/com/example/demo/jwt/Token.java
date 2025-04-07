package com.example.demo.jwt;

import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.security.CustomUserdetail;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Component
public class Token {

    private static final String SECRET = "kRInWzzg9Kvm+g/6Dy4AthiZ3cq+OzOa4ZEZk+1XJj89TCapD2C10A7KIpLTjFjA";

    public String generateToken(String email, String role) throws Exception {
        // 1. Tạo phần header của JWT (dùng HS256)
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // 2. Tạo phần payload (claims)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(email)                          // Đặt "subject" (ví dụ: email người dùng)
                .issuer("chus")                      // Tên của ứng dụng
                .expirationTime(new Date(new Date().getTime() + 86400000))  // Hết hạn sau 1 giờ
                .issueTime(new Date())
                .claim("role", role)// Thời điểm phát hành token
                .build();

        // 3. Kết hợp header và claims lại để tạo JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // 4. Ký JWT bằng khóa bí mật và thuật toán HS256
        JWSSigner signer = new MACSigner(SECRET.getBytes());
        signedJWT.sign(signer);

        // 5. Trả về token dạng chuỗi
        return signedJWT.serialize();
    }

    public boolean validateToken(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET.getBytes());

        if (!signedJWT.verify(verifier)) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        Date expirationTime = claims.getExpirationTime();
        if (expirationTime.before(new Date())) {
            throw new AppException(ErrorCode.TOKEN_TIME_EXPIRATION);
        }
        return true;
    }

    public String extractRoleFromToken(String token) throws Exception {
        // 1. Parse token (token đã được ký trước đó
        SignedJWT signedJWT = SignedJWT.parse(token);
        // 2. Lấy claims từ token
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        // 3. Trích xuất claim "role"3
        String role = claims.getStringClaim("role");
        // 4. Trả về giá trị role
        return role;
    }



    public String getSubjectFromToken(String token) {
        try {

            String jwt = getToken(token);
            // 1. Parse token từ chuỗi JWT
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            // 2. Lấy thông tin claims từ token
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            // 3. Lấy subject từ claims
            String subject = claims.getSubject();

            // 4. Trả về subject
            return subject;

        } catch (ParseException e) {
            throw new CustomException(500, "Lỗi parse token: " + e.getMessage());
        } catch (Exception e) {
            throw new CustomException(500, "Lỗi xác thực token: " + e.getMessage());
        }
    }

    public String getEmailfromToken() {
        CustomUserdetail userdetail = (CustomUserdetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userdetail.getEmail();
        return email;
    }

    public int getIdfromToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + auth);
        if (auth != null) {
            Object principal = auth.getPrincipal();
            System.out.println("Principal: " + principal);
            CustomUserdetail userdetail = (CustomUserdetail) principal;
            return userdetail.getId();
        }
        throw new IllegalStateException("No authenticated user found");
    }

    public String getToken(String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return jwtToken;
    }


}
