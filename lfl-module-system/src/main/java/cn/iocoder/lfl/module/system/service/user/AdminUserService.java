package cn.iocoder.lfl.module.system.service.user;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.user.vo.user.UserPageReqVO;
import cn.iocoder.lfl.module.system.controller.user.vo.user.UserRespVO;
import cn.iocoder.lfl.module.system.controller.user.vo.user.UserSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AdminUserService {
     Long createUser( UserSaveReqVO reqVO);

     void updateUser(@Valid UserSaveReqVO reqVO);

     void deleteUser(Long id);

     void deleteUserList(List<Long> ids);

     void updateUserLogin(Long id,String loginIp);

     UserRespVO getUser(Integer id);

     void updateUserPassword(Long id,String password);

     void updateUserStatus(Long id,Integer status);

     PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO);
}
