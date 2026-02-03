package cn.iocoder.lfl.module.system.dal.mysql.oauth2;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

public interface OAuth2AccessTokenMapper extends BaseMapperX<OAuth2AccessTokenDO> {
    default List<OAuth2AccessTokenDO> selectListByRefreshToken(String refreshToken) {
        return selectList(OAuth2AccessTokenDO::getRefreshToken,refreshToken);
    }

    default OAuth2AccessTokenDO selectByAccessToken(String accessToken) {
        return selectOne(OAuth2AccessTokenDO::getAccessToken,accessToken);
    }
}
