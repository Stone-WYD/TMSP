package com.njxnet.service.tmsp.common;

public class BaseException extends RuntimeException {

    private int code;

    private String message;

    public BaseException(int code, String name) {
        this.code = code;
        this.message = name;
    }
}
