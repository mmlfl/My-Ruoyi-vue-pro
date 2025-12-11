package cn.iocoder.lfl.module.system.service;

import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginReqVO;
import cn.iocoder.lfl.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDo;

public interface UserService extends IService<AdminUserDo> {
    AuthLoginRespVO login(AuthLoginReqVO reqVO);
}
