package cn.iocoder.lfl.module.system.service.oauth2;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.lfl.framework.common.util.date.DateUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2CodeDO;
import cn.iocoder.lfl.module.system.dal.mysql.oauth2.OAuth2CodeMapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.OAUTH2_CODE_EXPIRE;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS;
import static cn.iocoder.lfl.framework.common.exception.util.ServiceExceptionUtil.exception;

public class OAuth2CodeServiceImpl implements OAuth2CodeService{
    /**
     * 授权码的过期时间，默认 5 分钟
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Resource
    private OAuth2CodeMapper oauth2CodeMapper;

    @Override
    public OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUri, String state) {
        OAuth2CodeDO oAuth2CodeDO = new OAuth2CodeDO().setUserId(userId).setUserType(userType)
                .setClientId(clientId).setScopes(scopes).setState(state)
                .setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT))
                .setRedirectUri(redirectUri)
                .setCode(generateCode());
        oauth2CodeMapper.insert(oAuth2CodeDO);
        return oAuth2CodeDO;
    }

    @Override
    public OAuth2CodeDO consumeAuthorizationCode(String code) {
        OAuth2CodeDO codeDO = oauth2CodeMapper.selectByCode(code);
        if (codeDO == null) {
            throw exception(OAUTH2_CODE_NOT_EXISTS);
        }
        if (DateUtils.isExpired(codeDO.getExpiresTime())) {
            throw exception(OAUTH2_CODE_EXPIRE);
        }
        oauth2CodeMapper.deleteById(codeDO.getId());
        return codeDO;
    }

    private String generateCode() {
        return IdUtil.fastSimpleUUID();
    }
}
