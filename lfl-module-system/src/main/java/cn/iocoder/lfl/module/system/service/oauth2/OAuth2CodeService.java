package cn.iocoder.lfl.module.system.service.oauth2;

import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2CodeDO;

import javax.tools.Diagnostic;
import java.util.List;

public interface OAuth2CodeService {
    OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUri, String state);

    OAuth2CodeDO consumeAuthorizationCode(String code);
}
