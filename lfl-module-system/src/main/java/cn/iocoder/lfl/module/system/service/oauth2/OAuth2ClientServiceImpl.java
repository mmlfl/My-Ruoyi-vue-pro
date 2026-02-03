package cn.iocoder.lfl.module.system.service.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.iocoder.lfl.module.system.dal.mysql.oauth2.OAuth2ClientMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisKeyConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

import static cn.iocoder.lfl.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.lfl.module.system.enums.social.ErrorCodeConstants.*;

@Service
public class OAuth2ClientServiceImpl implements OAuth2ClientService {
    @Resource
    private OAuth2ClientMapper oAuth2ClientMapper;

    @Override
    public Long createOAuth2Client(OAuth2ClientSaveReqVO createReqVO) {
        //校验客户端ID唯一
        validateClientIdExists(null, createReqVO.getClientId());
        //保存
        OAuth2ClientDO clientDO = BeanUtils.toBean(createReqVO, OAuth2ClientDO.class);
        oAuth2ClientMapper.insert(clientDO);
        return clientDO.getId();
    }

    @Override
    public void updateOAuth2Client(OAuth2ClientSaveReqVO updateReqVO) {
        //校验客户端ID唯一
        validateClientIdExists(updateReqVO.getId(), updateReqVO.getClientId());
        //更新
        OAuth2ClientDO clientDO = BeanUtils.toBean(updateReqVO, OAuth2ClientDO.class);
        oAuth2ClientMapper.updateById(clientDO);
    }

    @Override
    public void deleteOAuth2Client(Long id) {
        oAuth2ClientMapper.deleteById(id);
    }

    @Override
    public void deleteOAuth2ClientBatch(List<Long> ids) {
        oAuth2ClientMapper.deleteByIds(ids);
    }

    @Override
    public PageResult<OAuth2ClientDO> getOAuth2ClientPage(OAuth2ClientPageReqVO pageReqVO) {
        return oAuth2ClientMapper.selectPage(pageReqVO);
    }

    @Override
    public OAuth2ClientDO getOAuth2Client(Long id) {
        OAuth2ClientDO oAuth2ClientDO = oAuth2ClientMapper.selectById(id);
        if(oAuth2ClientDO == null){
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
        return oAuth2ClientDO;
    }

    @Override
    public OAuth2ClientDO validOAuthClientFromCache(String clientId, String clientSecret, String authorizedGrantType,
                                                    Collection<String> scopes, String redirectUri) {
        OAuth2ClientDO client = getSelf().getOAuth2ClientFromCache(clientId);
        if(client == null){
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
        //校验状态
        if(CommonStatusEnum.isDisable(client.getStatus())){
            throw exception(OAUTH2_CLIENT_DISABLE);
        }
        //校验密钥
        if(StrUtil.isNotEmpty(clientSecret) && !client.getSecret().equals(clientSecret)){
            throw exception(OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        }
        //校验授权类型
        if(StrUtil.isNotEmpty(authorizedGrantType) && !CollUtil.contains(client.getAuthorizedGrantTypes(), authorizedGrantType)){
            throw exception(OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        //校验授权范围
        if(CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(client.getScopes(), scopes)){
            throw exception(OAUTH2_CLIENT_SCOPE_OVER);
        }
        //校验重定向地址
        if(StrUtil.isNotEmpty(redirectUri) && !CollectionUtils.startWith(client.getRedirectUris(), redirectUri)){
            throw exception(OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH);
        }
        return client;
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.OAUTH_CLIENT, key = "#clientId",
                unless = "#result == null ")
    public OAuth2ClientDO getOAuth2ClientFromCache(String clientId) {
        return oAuth2ClientMapper.selectByClientId(clientId);
    }

    private void validateClientIdExists(Long id, String clientId) {
        OAuth2ClientDO oAuth2ClientDO = oAuth2ClientMapper.selectByClientId(clientId);
        if(oAuth2ClientDO == null){
            return;
        }
        if(id == null){
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
        if(!oAuth2ClientDO.getId().equals(id)){
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
    }

    /**
     * 获得自身的代理对象
     */
    private OAuth2ClientServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
