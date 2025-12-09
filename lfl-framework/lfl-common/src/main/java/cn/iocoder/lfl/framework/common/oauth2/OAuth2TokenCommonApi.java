package cn.iocoder.lfl.framework.common.oauth2;

import cn.iocoder.lfl.framework.common.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;

public interface OAuth2TokenCommonApi {

    OAuth2AccessTokenCheckRespDTO checkAccessToken(String token);
}
