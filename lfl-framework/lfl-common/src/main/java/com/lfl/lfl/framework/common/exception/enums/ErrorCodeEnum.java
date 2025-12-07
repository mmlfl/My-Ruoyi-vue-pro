package com.lfl.lfl.framework.common.exception.enums;


import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SUCCESS(200,"成功"),
    BADREQUEST(400,"请求参数错误"),
    UNAUTHORIZED(401,"未授权"),
    FORBIDDEN(403,"没有该操作权限"),
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
