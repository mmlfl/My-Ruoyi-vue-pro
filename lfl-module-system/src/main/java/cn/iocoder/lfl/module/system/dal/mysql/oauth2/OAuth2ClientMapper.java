package cn.iocoder.lfl.module.system.dal.mysql.oauth2;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.lfl.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2ClientDO;

public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {
    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO){
        return selectPage(reqVO,new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
        );
    }
}
