package com.example.yoonnsshop.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private boolean status;
    private String message;
    private T data;

    public ApiResponse(boolean status) {
        this.status = status;
    }

    public ApiResponse(boolean status, T data) {
        this.status = status;
        this.data = data;
    }
}
