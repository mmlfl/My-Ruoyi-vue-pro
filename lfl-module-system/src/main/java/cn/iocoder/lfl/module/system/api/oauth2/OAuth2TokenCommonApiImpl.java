package cn.iocoder.lfl.module.system.api.oauth2;

import cn.iocoder.lfl.framework.common.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.lfl.framework.common.oauth2.OAuth2TokenCommonApi;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.dal.redis.oauth2.LoginUserRedisDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2TokenCommonApiImpl implements OAuth2TokenCommonApi {
    @Resource
    private LoginUserRedisDAO loginUserRedisDAO;

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String token) {
        LoginUser loginUser = loginUserRedisDAO.get(token);
        if(loginUser ==null){
            return null;
        }
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
