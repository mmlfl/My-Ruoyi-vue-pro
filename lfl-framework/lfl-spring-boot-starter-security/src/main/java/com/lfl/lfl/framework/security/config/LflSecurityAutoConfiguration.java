package com.lfl.lfl.framework.security.config;

import com.lfl.lfl.framework.security.core.handler.AccessDeniedHandlerImpl;
import com.lfl.lfl.framework.security.core.handler.AuthenticationEntryPointImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import java.security.Security;

@AutoConfiguration
@AutoConfigureOrder(-1)
@EnableConfigurationProperties(SecurityProperties.class)
public class LflSecurityAutoConfiguration {

    @Resource
    private SecurityProperties securityProperties;
    /**
     * 认证失败处理器
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器
     * @return AccessDeniedHandler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandlerImpl();
    }

    /**
     * 密码加密器
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }



}
