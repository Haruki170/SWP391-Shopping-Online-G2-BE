package com.example.demo.security;

import com.example.demo.exception.AppException;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component

//tạo một filter request
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    Token tokenService;
    @Autowired
    CustomUserdetailSevice customUserdetailSevice;

    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // Sử dụng AntPathMatcher để so khớp pattern
    // list những url ko cần filter
    private static final List<String> PUBLIC_URLS = Arrays.asList(
            "/auth/customer/sign-in",
            "/auth/customer/sign-up",
            "/product/getbyID/*",
            "/uploads/*",
            "/customer/forgot-password",
            "/customer/change-password-by-code",
            "/payment/vnpay-callback",
            "/customer/check-code",
            "/shopOwner/check-code",
            "/shopOwner/forgot-password",
            "/shopOwner/change-password-by-code",
            "/auth/shop/login",
            "/auth/admin/login",
            "/category/get-all",
            "/category/getAll",
            "/shop/detail-customer/*",
            "/shop/detail-customer",
            "/home/get",
            "/ws/*",
            "/product/search",
            "/product/filter",
            "/category/products",
            "/admin/forgot-password",
            "/admin/change-password-by-code",
            "/admin/check-code",
            "/shop/customer-detail-shop/*",
            "category/products",
            "/banners/all",
            "/blog/*",
            "/blog",
            "/auth/shipper/sign-up",
            "/auth/shipper/sign-in"
            // Thêm các endpoint khác nếu cần
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //lấy end point
        String requestURI = request.getRequestURI();


        //nếu endpoint trùng với 1 trong những endpoint public thì đi tiếp
        if (isPublicUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // nếu ko thì lấy subject từ token người dùng gửi lên
        String token = extractJwtFromRequest(request);
        try {

            //nếu token bị null sẽ trả về lỗi
            if(token != null && tokenService.validateToken(token)) {
                //lấy email from token
                String email = tokenService.getSubjectFromToken(token);
                String role = tokenService.extractRoleFromToken(token);
                //load customedetail
                UserDetails userDetails = customUserdetailSevice.mapUserDetail(email,role);
                //tạo một đói tượng authentication
                Authentication authentication = getAuthentication(userDetails, request);
                //lưu lại trong context
                SecurityContextHolder.getContext().setAuthentication(authentication);
//
            }
            else{
                setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Vui lòng đăng nhập");
            }
        }
        catch (Exception e) {
            //nếu token ko đúng hoặc hết hạn thì trả về lỗi
            setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
        }
        // Tiếp tục chuỗi lọc để xử lý request
        filterChain.doFilter(request, response);

    }



    private boolean isPublicUrl(String requestURI) {
        // Dùng AntPathMatcher để kiểm tra pattern thay vì chỉ so sánh chuỗi URL
        return PUBLIC_URLS.stream().anyMatch(url -> pathMatcher.match(url, requestURI));
    }


    public Authentication getAuthentication(UserDetails userDetails, HttpServletRequest request) {
        //tạo một authentication trong đó có userdetail và list authorities
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String jwt = bearerToken.substring(7);

            return bearerToken.substring(7); // Bỏ chữ "Bearer " để lấy token
        }
        return null;
    }

    private void setErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json; charset=UTF-8");
        ApiResponse<String> apiResponse = new ApiResponse<>(statusCode, message, null);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
