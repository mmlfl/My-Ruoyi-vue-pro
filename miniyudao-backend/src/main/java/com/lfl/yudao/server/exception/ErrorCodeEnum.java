package com.lfl.yudao.server.exception;


import lombok.Getter;

import javax.print.attribute.IntegerSyntax;

@Getter
public enum ErrorCodeEnum {
    SUCCESS(200,"成功"),
    BADREQUEST(400,"请求参数错误"),
    UNAUTHORIZED(401,"未授权"),
    USERNAME_PASSWORD_ERROR(1001,"用户名或密码错误"),
    USER_NOT_EXIST(1002,"用户不存在"),
    INTERNAL_ERROR(500,"服务器内部错误");

    private Integer code;
    private String message;

    ErrorCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
