package com.example.demo.config;

import com.example.demo.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtFilter jwtFilter, AccessDeniedHandler customAccessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF
                .cors().and()  // Apply CORS configurations
                .authorizeHttpRequests((requests) -> requests
                        // Public endpoints (WebSocket)
                        .requestMatchers("/banners/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/auth/customer/sign-in",
                                "/auth/customer/sign-up",
                                "/auth/shipper/sign-in",
                                "/product/getbyID/*",
                                "/uploads/**",
                                "/payment/vnpay-callback",
                                "/shop/add",
                                "/banners/all",
                                "/auth/shop/login",
                                "/customer/forgot-password",
                                "/customer/change-password-by-code",
                                "/customer/check-code",
                                "/shopOwner/forgot-password",
                                "/shopOwner/change-password-by-code",
                                "/shopOwner/check-code",
                                "/auth/admin/login",
                                "/shop/customer-detail-shop/",
                                "/home/get", // Ensure WebSocket endpoint is open
                                "/payment/vnpay-callback",
                                "/product/search",
                                "/product/filter",
                                "/category/getbyID/*",
                                "/category/update/*",
                                "/category/delete/*",
                                "/category/create",
                                "/category/get-all",
                                "/category/getAll",
                                "/category/*",
                                "/admin/forgot-password",
                                "/admin/change-password-by-code",
                                "/admin/check-code",
                                "/banners","/blog/*","/blog",            "/shop/detail-customer/*",
                                "/shop/detail-customer","/product/update-status", "/auth/shipper/sign-up"
                        ).permitAll()  // No authentication required
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)  // Handle 403 errors
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter

        return http.build();
    }

}
