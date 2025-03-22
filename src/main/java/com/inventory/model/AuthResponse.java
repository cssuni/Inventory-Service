package com.inventory.model;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Data
public class AuthResponse {

    private String userName;
    List<SimpleGrantedAuthority> authorities;
    UserDetails userDetails;

}
