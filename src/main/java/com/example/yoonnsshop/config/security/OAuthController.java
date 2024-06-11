package com.example.yoonnsshop.config.security;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.config.ApiController;
import com.example.yoonnsshop.domain.members.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiController
@RequestMapping("/oauth")
public class OAuthController {
    OAuthService oAuthService;

    @Autowired
    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @GetMapping("/code/google")
    public ResponseEntity<ApiResponse> handleGoogleCallback(@RequestParam("code") String code) {
        Member profile = oAuthService.googleLogin(code);
        return ResponseEntity.ok().body(new ApiResponse(true, profile));
    }
}
