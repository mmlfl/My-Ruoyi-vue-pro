package cn.iocoder.lfl.module.system.enums.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginResultEnum {
    SUCCESS(0), // 成功
    BAD_CREDENTIALS(10), // 账号密码错误
    USER_DISABLED(20), // 账号被禁用
    CAPTCHA_NOT_FOUND(30), // 验证码不存在
    CAPTCHA_CODE_ERROR(31); // 验证码错误


    private final Integer result;
}
