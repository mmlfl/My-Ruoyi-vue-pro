package cn.iocoder.lfl.module.system.enums.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN_USERNAME(100), //使用账号登录
    LOGIN_SOCIAL(101),  //使用社交登录
    LOGIN_MOBILE(102),  //使用手机登录
    LOGIN_SMS(104),     //使用手机验证码登录

    LOGOUT_SELF(200), // 自己主动退出
    LOGOUT_DELETE(201); // 被管理员强制退出

    private final Integer type;
}
