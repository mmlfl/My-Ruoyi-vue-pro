package cn.iocoder.lfl.module.system.dal.mysql.oauth2;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2CodeDO;

public interface OAuth2CodeMapper extends BaseMapperX<OAuth2CodeDO> {
    default OAuth2CodeDO selectByCode(String code) {
        return selectOne(OAuth2CodeDO::getCode,code);
    }
}
