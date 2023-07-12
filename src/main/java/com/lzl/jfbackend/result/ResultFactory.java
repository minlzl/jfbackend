package com.lzl.jfbackend.result;

public class ResultFactory {

    public static Result buildSuccessResult(Object o) {
        return new Result(ResultCode.SUCCESS.code,"成功", o);
    }

    public static Result buildFailResult(Object o) {
        return new Result(ResultCode.FAIL.code, "失败", o);
    }
}
