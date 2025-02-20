package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserdetailSevice implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //trả lại customer deltail theo id
        return null;
    }

    public CustomUserdetail mapUserDetail(String email,String role) {
        return CustomUserdetail.mapUserDetail(email,role);
    }
}
