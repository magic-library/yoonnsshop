package com.example.yoonnsshop.domain.admins;

import com.example.yoonnsshop.config.security.JwtUtilities;
import com.example.yoonnsshop.domain.admins.dto.LoginDto;
import com.example.yoonnsshop.domain.admins.entity.Admin;
import com.example.yoonnsshop.domain.admins.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, AuthenticationManager authenticationManager, JwtUtilities jwtUtilities) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtilities = jwtUtilities;
    }

    public String authenticate(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPrincipal(), loginDto.getCredentials())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Admin admin = adminRepository.findByEmail(loginDto.getPrincipal()).orElseThrow();

        String token = jwtUtilities.generateToken(admin.getUsername(), Collections.emptyList());
        return token;
    }
}
