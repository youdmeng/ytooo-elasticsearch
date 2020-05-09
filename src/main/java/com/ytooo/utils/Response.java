package com.ytooo.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<T> {

    private int code = 0;

    private String msg = "success";

    private T data;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public Response(String msg) {
        this.msg = msg;
    }
}