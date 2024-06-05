package com.example.yoonnsshop.domain.admins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginDto {
    @NotNull
    @NotEmpty
    @Email
    private String principal; // email
    @NotNull
    @NotEmpty
    private String credentials; // password
}
