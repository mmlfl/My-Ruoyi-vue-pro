package cn.iocoder.lfl.module.system.service.oauth2;

import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OAuth2ApproveService {
    List<OAuth2ApproveDO> getApproveList(Long loginUserId, Integer userType, String clientId);

    boolean checkForPreApproval(Long loginUserId, Integer userType, String clientId, Collection<String> entries);

    boolean updateForAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> scopes);
}
