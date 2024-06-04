package com.example.yoonnsshop.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class JoinDto {
    private String principal; // email
    private String credentials; // password
}
