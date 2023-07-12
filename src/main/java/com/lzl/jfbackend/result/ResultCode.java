package com.lzl.jfbackend.result;

public enum ResultCode {
    SUCCESS(200),
    FAIL(400);
    int code;
    ResultCode(int code) {
        this.code = code;
    }
}
