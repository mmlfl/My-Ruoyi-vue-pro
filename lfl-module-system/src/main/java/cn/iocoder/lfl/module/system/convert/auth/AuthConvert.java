package cn.iocoder.lfl.module.system.convert.auth;

import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import cn.iocoder.lfl.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import org.mapstruct.factory.Mappers;

public interface AuthConvert {
    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);
}
