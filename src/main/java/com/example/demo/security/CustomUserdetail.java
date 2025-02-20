package com.example.demo.security;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ShopOwnerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter

//tạo một cusomer userdetail(trong spring security) với các thuộc tính mình muốn
public class CustomUserdetail implements UserDetails {

    private int id;
    private String email;

    //list những quyền mà người dùng có
    private Collection<? extends GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static CustomUserdetail mapUserDetail (String email, String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role.equals("customer")){
            CustomerRepository repository = new CustomerRepository();
            Customer customer = repository.findByEmail(email);
            authorities.add(new SimpleGrantedAuthority(role));
            return new CustomUserdetail(customer.getId(), email, authorities);
        }
        else if(role.equals("shopOwner")){
            ShopOwnerRepository ownerRepository = new ShopOwnerRepository();
            ShopOwner shopOwner = ownerRepository.findByEmail(email);
            authorities.add(new SimpleGrantedAuthority(role));
            return new CustomUserdetail(shopOwner.getId(), email, authorities);
        }
        else if(role.equals("admin") || role.equals("superAdmin")){
            AdminRepository adminRepository = new AdminRepository();
            Admin admin = adminRepository.findByEmail(email);
            authorities.add(new SimpleGrantedAuthority(role));
            return new CustomUserdetail(admin.getId(), email, authorities);
        }
        return null;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }


}
