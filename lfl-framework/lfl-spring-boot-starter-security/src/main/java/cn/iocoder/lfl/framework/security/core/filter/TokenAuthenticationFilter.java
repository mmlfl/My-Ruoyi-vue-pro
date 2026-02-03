package cn.iocoder.lfl.framework.security.core.filter;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.lfl.framework.common.biz.system.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.lfl.framework.common.biz.system.oauth2.OAuth2TokenCommonApi;
import cn.iocoder.lfl.framework.common.exception.ServiceException;
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
        try {
            OAuth2AccessTokenCheckRespDTO accessToken = oAuth2TokenCommonApi.checkAccessToken(token);
            if(accessToken ==null){
                return null;
            }
            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId()).setUserType(accessToken.getUserType())
                    .setInfo(accessToken.getUserInfo()) // 额外的用户信息
                    .setTenantId(accessToken.getTenantId()).setScopes(accessToken.getScopes())
                    .setExpiresTime(accessToken.getExpiresTime());
        } catch (ServiceException serviceException) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
        }
    }
