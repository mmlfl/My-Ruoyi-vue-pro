package com.lfl.lfl.framework.security.core.handler;

import com.lfl.lfl.framework.common.exception.enums.ErrorCodeEnum;
import com.lfl.lfl.framework.common.pojo.CommonResult;
import com.lfl.lfl.framework.common.util.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.debug("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);

        //返回401
        ServletUtils.writeJSON(response, CommonResult.error(ErrorCodeEnum.UNAUTHORIZED));
    }
}
