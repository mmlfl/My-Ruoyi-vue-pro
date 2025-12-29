package cn.iocoder.lfl.module.system.service.permission;

import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RolePageReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RoleRespVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService  {
    List<RoleDO> getRoleList(Set<Long> roleIds);

    List<RoleDO> getRoleListFromCache(Collection<Long> roleIds);

    RoleDO getRoleFromCache(Long roleId);

    boolean hasAnySuperAdmin(Collection<Long> roleIds);

    Long createRole(RoleSaveReqVO reqVO);

    void deleteRole(Long id);

    void deleteRoleList(List<Long> ids);

    void updateRole(RoleSaveReqVO reqVO);

    void updateRoleStatus(Long id, Integer status);

    RoleRespVO getRole(Long id);

    PageResult<RoleDO> getRolePage(RolePageReqVO reqVO);
}
