package cn.iocoder.lfl.module.system.service.oauth2;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface OAuth2ClientService {
    Long createOAuth2Client(OAuth2ClientSaveReqVO createReqVO);

    void updateOAuth2Client(OAuth2ClientSaveReqVO updateReqVO);

    void deleteOAuth2Client(Long id);

    void deleteOAuth2ClientBatch(List<Long> ids);

    PageResult<OAuth2ClientDO> getOAuth2ClientPage(OAuth2ClientPageReqVO pageReqVO);

    OAuth2ClientDO getOAuth2Client(Long id);

    default OAuth2ClientDO validOAuthClientFromCache(String clientId) {
        return validOAuthClientFromCache(clientId, null, null, null, null);
    }

    /**
     * 从缓存中，校验客户端是否合法
     *
     * 非空时，进行校验
     *
     * @param clientId 客户端编号
     * @param clientSecret 客户端密钥
     * @param authorizedGrantType 授权方式
     * @param scopes 授权范围
     * @param redirectUri 重定向地址
     * @return 客户端
     */
    OAuth2ClientDO validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType,
                                             Collection<String> scopes, String redirectUri);


    OAuth2ClientDO getOAuth2ClientFromCache(String clientId);
}
