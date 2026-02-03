package cn.iocoder.lfl.module.system.api.oauth2;

import cn.iocoder.lfl.framework.common.biz.system.oauth2.DTO.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.lfl.framework.common.biz.system.oauth2.OAuth2TokenCommonApi;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.lfl.module.system.dal.redis.oauth2.LoginUserRedisDAO;
import cn.iocoder.lfl.module.system.service.oauth2.OAuth2TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OAuth2TokenCommonApiImpl implements OAuth2TokenCommonApi {
    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String token) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.checkAccessToken(token);
        return BeanUtils.toBean(accessTokenDO, OAuth2AccessTokenCheckRespDTO.class);
    }
}
