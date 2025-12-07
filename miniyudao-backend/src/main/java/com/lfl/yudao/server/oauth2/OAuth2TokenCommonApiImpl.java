package com.lfl.yudao.server.oauth2;

import com.lfl.lfl.framework.common.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import com.lfl.lfl.framework.common.oauth2.OAuth2TokenCommonApi;
import com.lfl.lfl.framework.security.LoginUser;
import com.lfl.yudao.server.pojo.DAO.LoginUserRedisDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2TokenCommonApiImpl implements OAuth2TokenCommonApi {
    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String token) {
        LoginUser loginUser = loginUserRedisDAO.get(token);
        return OAuth2AccessTokenCheckRespDTO.builder()
                .userId(loginUser.getId())
                .userType(loginUser.getUserType())
                .userInfo(loginUser.getInfo())
                .tenantId(loginUser.getTenantId())
                .scopes(loginUser.getScopes())
                .expiresTime(loginUser.getExpiresTime())
                .build();
    }
}
