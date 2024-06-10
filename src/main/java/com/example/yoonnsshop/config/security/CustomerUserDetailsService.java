package com.example.yoonnsshop.config.security;

import com.example.yoonnsshop.domain.admins.entity.Admin;
import com.example.yoonnsshop.domain.admins.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
        return  admin;
    }
}
