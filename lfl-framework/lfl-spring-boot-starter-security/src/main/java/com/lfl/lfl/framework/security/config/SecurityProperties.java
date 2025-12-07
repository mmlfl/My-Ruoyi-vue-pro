package com.lfl.lfl.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "lfl.security")
@Validated
@Data
public class SecurityProperties {

    /**
     * Http请求时,访问令牌的请求header
     */
    @NotEmpty(message = "Http请求时,访问令牌的请求header不能为空")
    private String headerName = "Authorization";

    /**
     * Http请求时,访问令牌的请求参数名
     */
    @NotEmpty(message = "Http请求时,访问令牌的请求参数名不能为空")
    private String parameterName = "token";

    /**
     * PasswordEncoder 加密复杂度,越高开销越大 默认为10 最大31
     */
    private Integer passwordEncoderLength = 4;
}
