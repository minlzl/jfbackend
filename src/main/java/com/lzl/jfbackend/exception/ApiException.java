package com.lzl.jfbackend.exception;

/**
 * <p>
 * 自定义的异常
 * </p>
 *
 * @author java
 * @since 2023-03-30
 */

public class ApiException extends RuntimeException {

    public ApiException() {
        this("操作失败");
    }

    public ApiException(String message) {
        super(message);
    }

}
