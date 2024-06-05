package com.example.yoonnsshop.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseMessageDto<T> {
    private boolean success;
    private T response;

    public ResponseMessageDto(boolean success, T response) {
        this.success = success;
        this.response = response;
    }
}
