package cn.iocoder.lfl.module.system.convert.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.lfl.framework.common.core.KeyValue;
import cn.iocoder.lfl.framework.common.enums.UserTypeEnum;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.object.BeanUtils;
import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAccessTokenRespVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAuthorizeInfoRespVO;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.open.OAuth2OpenCheckTokenRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.iocoder.lfl.module.system.util.OAuth2Utils;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OAuth2OpenConvert {
    OAuth2OpenConvert INSTANCE = Mappers.getMapper(OAuth2OpenConvert.class);

    default OAuth2OpenAuthorizeInfoRespVO convert(OAuth2ClientDO client, List<OAuth2ApproveDO> approves){
        //构建scopes
        List<KeyValue<String,Boolean>> scopes = new ArrayList<>(client.getScopes().size());
        Map<String, OAuth2ApproveDO> approveMap = CollectionUtils.convertMap(approves, OAuth2ApproveDO::getScope);
        for (String scope : client.getScopes()) {
            scopes.add(new KeyValue<>(scope, approveMap.get(scope) != null ?
                    approveMap.get(scope).getApproved() : false));
        }
        //构建返回
        return new OAuth2OpenAuthorizeInfoRespVO(
                new OAuth2OpenAuthorizeInfoRespVO.Client(client.getName(),client.getLogo()),
                scopes
        );
    }

    default OAuth2OpenAccessTokenRespVO convert(OAuth2AccessTokenDO bean) {
        OAuth2OpenAccessTokenRespVO respVO = BeanUtils.toBean(bean, OAuth2OpenAccessTokenRespVO.class);
        respVO.setTokenType(SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        respVO.setExpiresIn(OAuth2Utils.getExpiresIn(bean.getExpiresTime()));
        respVO.setScope(OAuth2Utils.buildScopeStr(bean.getScopes()));
        return respVO;
    }

    default OAuth2OpenCheckTokenRespVO convert2(OAuth2AccessTokenDO bean) {
        OAuth2OpenCheckTokenRespVO respVO = BeanUtils.toBean(bean, OAuth2OpenCheckTokenRespVO.class);
        respVO.setExp(LocalDateTimeUtil.toEpochMilli(bean.getExpiresTime()) / 1000L);
        respVO.setUserType(UserTypeEnum.ADMIN.getValue());
        return respVO;
    }
}
