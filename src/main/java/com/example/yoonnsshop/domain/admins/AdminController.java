package com.example.yoonnsshop.domain.admins;

import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.config.ResponseMessageDto;
import com.example.yoonnsshop.domain.admins.dto.LoginDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiController
@RequestMapping("admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessageDto> login(@RequestBody @Valid LoginDto loginDto) {
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(false, "Email already exists");
        return ResponseEntity.ok().body(responseMessageDto);
    }
}
