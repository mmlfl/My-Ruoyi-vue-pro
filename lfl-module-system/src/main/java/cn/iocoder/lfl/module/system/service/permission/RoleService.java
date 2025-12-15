package cn.iocoder.lfl.module.system.service.permission;

import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService extends IService<RoleDO> {
    List<RoleDO> getRoleList(Set<Long> roleIds);

    List<RoleDO> getRoleListFromCache(Collection<Long> roleIds);

    RoleDO getRoleFromCache(Long roleId);

    boolean hasAnySuperAdmin(Collection<Long> roleIds);
}
