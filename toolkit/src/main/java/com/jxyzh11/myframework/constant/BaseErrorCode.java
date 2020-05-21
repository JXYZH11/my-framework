package com.jxyzh11.myframework.constant;

/**
 * 基础错误码
 *
 * @author JXYZH11
 * @version 1.0
 * @description TODO
 * @date 2020/1/18 10:36
 */
public enum BaseErrorCode implements ErrorCode {

    SUCCESS("200", "请求成功"),
    FAILURE("500", "系统繁忙，请稍后再试，亲~"),
    CLIENT_QUERY_NULL("201", "请求成功，返回结果为空~");

    private static final String NAME = ErrorCodeName.BASE.name();
    private String code;
    private String msg;

    BaseErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
