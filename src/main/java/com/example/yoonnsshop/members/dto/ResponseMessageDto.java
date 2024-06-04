package com.example.yoonnsshop.members.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseMessageDto {
    private boolean success;
    private String response;

    public ResponseMessageDto(boolean success, String response) {
        this.success = success;
        this.response = response;
    }
}
