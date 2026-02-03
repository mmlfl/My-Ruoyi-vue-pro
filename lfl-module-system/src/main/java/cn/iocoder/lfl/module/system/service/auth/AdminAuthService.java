package cn.iocoder.lfl.module.system.service.auth;

import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;

public interface AdminAuthService {
    AuthLoginRespVO login(AuthLoginReqVO reqVO);

    AdminUserDO authenticate(String username, String password);
}
