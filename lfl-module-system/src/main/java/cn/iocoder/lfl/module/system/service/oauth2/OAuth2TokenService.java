package cn.iocoder.lfl.module.system.service.oauth2;

import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

public interface OAuth2TokenService {
    OAuth2AccessTokenDO createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    OAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId);

    OAuth2AccessTokenDO getAccessToken(String accessToken);

    /**
     * 移除访问令牌
     * 注意：该流程中，会移除相关的刷新令牌
     *
     * 参考 DefaultTokenServices 的 revokeToken 方法
     *
     * @param accessToken 刷新令牌
     * @return 访问令牌的信息
     */
    OAuth2AccessTokenDO removeAccessToken(String accessToken);

    OAuth2AccessTokenDO checkAccessToken(String token);
}
