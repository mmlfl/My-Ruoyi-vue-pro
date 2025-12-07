package com.lfl.lfl.framework.common.oauth2;

import com.lfl.lfl.framework.common.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import com.lfl.lfl.framework.common.oauth2.DTO.OAuth2AccessTokenRespDTO;

public interface OAuth2TokenCommonApi {

    OAuth2AccessTokenCheckRespDTO checkAccessToken(String token);
}
