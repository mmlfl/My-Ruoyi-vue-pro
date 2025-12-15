package cn.iocoder.lfl.module.system.api.permission;

import cn.iocoder.lfl.framework.common.biz.system.permission.PermissionCommonApi;

import java.util.Collection;
import java.util.Set;

public interface PermmsionApi extends PermissionCommonApi {

    /**
     * 获得拥有多个角色的用户编号集合
     *
     * @param roleIds 角色编号集合
     * @return 用户编号集合
     */
    Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds);

}
