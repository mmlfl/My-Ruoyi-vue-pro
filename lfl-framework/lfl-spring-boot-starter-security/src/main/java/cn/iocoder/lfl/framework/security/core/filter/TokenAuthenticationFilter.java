package cn.iocoder.lfl.framework.security.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.lfl.framework.common.biz.system.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.lfl.framework.common.biz.system.oauth2.OAuth2TokenCommonApi;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.framework.security.config.SecurityProperties;
import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter{

    private final OAuth2TokenCommonApi oAuth2TokenCommonApi;

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = SecurityFrameworkUtils.getAuthorization( request,
                securityProperties.getHeaderName(),securityProperties.getParameterName());
        if(StrUtil.isNotEmpty(token)){
            LoginUser loginUser = buildLoginUserByToken(token);
            if(loginUser!=null){
                SecurityFrameworkUtils.setLoginUser(loginUser,request);
            }
        }
        filterChain.doFilter(request,response);
    }


    private LoginUser buildLoginUserByToken(String token){
        OAuth2AccessTokenCheckRespDTO accessToken = oAuth2TokenCommonApi.checkAccessToken(token);
        if(accessToken ==null){
            return null;
        }
        return LoginUser.builder()
                .id(accessToken.getUserId())
                .userType(accessToken.getUserType())
                .info(accessToken.getUserInfo())
                .tenantId(accessToken.getTenantId())
                .scopes(accessToken.getScopes())
                .expiresTime(accessToken.getExpiresTime())
                .build();
            }
}
