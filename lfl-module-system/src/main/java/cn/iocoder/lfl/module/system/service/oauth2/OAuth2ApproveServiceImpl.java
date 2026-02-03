package cn.iocoder.lfl.module.system.service.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.date.DateUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.iocoder.lfl.module.system.dal.mysql.oauth2.OAuth2ApproveMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OAuth2ApproveServiceImpl implements OAuth2ApproveService{

    /**
     * 批准的过期时间，默认 30 天
     */
    private static final Integer TIMEOUT = 30 * 24 * 60 * 60; // 单位：秒

    @Resource
    private OAuth2ApproveMapper oauth2ApproveMapper;
    @Resource
    private OAuth2ClientService oauth2ClientService;

    @Override
    public List<OAuth2ApproveDO> getApproveList(Long loginUserId, Integer userType, String clientId) {
        List<OAuth2ApproveDO> oAuth2ApproveDOS = oauth2ApproveMapper
                .selectListByUserIdAndUserTypeAndClientId(loginUserId, userType, clientId);
        oAuth2ApproveDOS.removeIf(o -> DateUtils.isExpired(o.getExpiresTime()));
        return oAuth2ApproveDOS;
    }

    @Override
    public boolean checkForPreApproval(Long loginUserId, Integer userType, String clientId, Collection<String> requestedScopes) {
        OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientId);
        Assert.notNull(client, "客户端不能为空"); // 防御性编程
        if(CollUtil.containsAll(client.getAutoApproveScopes(),requestedScopes)){
            LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
            for (String scope : requestedScopes) {
                saveApprove(loginUserId,userType,clientId,scope,true,expireTime);
            }
            return true;
        }
        List<OAuth2ApproveDO> approveList = getApproveList(loginUserId, userType, clientId);
        Set<String> scopes = CollectionUtils.convertSet(approveList, OAuth2ApproveDO::getScope,
                OAuth2ApproveDO::getApproved);
        return CollUtil.containsAll(scopes, requestedScopes);
    }

    @Override
    public boolean updateForAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes) {
        if(CollUtil.isEmpty(requestedScopes)){
            return true;
        }
        boolean success = false;
        for (Map.Entry<String, Boolean> scope : requestedScopes.entrySet()) {
            if(scope.getValue()){
                success = true;
            }
            LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
            saveApprove(userId,userType,clientId,scope.getKey(),scope.getValue(),expireTime);
        }
        return success;
    }

    void saveApprove(Long userId, Integer userType, String clientId,
                     String scope, Boolean approved, LocalDateTime expireTime){
        //先更新 再插入
        OAuth2ApproveDO oAuth2ApproveDO = new OAuth2ApproveDO().setUserId(userId).setUserType(userType).setClientId(clientId)
                .setApproved(approved).setScope(scope).setExpiresTime(expireTime);
        if(oauth2ApproveMapper.update(oAuth2ApproveDO) == 1){
            return;
        }
        oauth2ApproveMapper.insert(oAuth2ApproveDO);
    }
}
