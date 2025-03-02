package com.example.socket.token;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class TokenService {
    public String getSubjectFromToken(String token) throws Exception {
        try {
            // 1. Parse token từ chuỗi JWT
            SignedJWT signedJWT = SignedJWT.parse(token);
            // 2. Lấy thông tin claims từ token
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            // 3. Lấy subject từ claims
            String subject = claims.getSubject();

            // 4. Trả về subject
            return subject;

        } catch (ParseException e) {
            throw new Exception("lỗi");
        } catch (Exception e) {
            throw new Exception("lỗi");
        }
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
}
