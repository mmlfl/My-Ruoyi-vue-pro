package cn.iocoder.lfl.module.system.service.user;

import cn.iocoder.lfl.module.system.controller.user.vo.user.UserSaveReqVO;

import javax.validation.Valid;

public interface AdminUserService {
     Long createUser( UserSaveReqVO reqVO);
}
