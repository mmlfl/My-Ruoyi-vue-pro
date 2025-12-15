package cn.iocoder.lfl.module.system.service.permission;

import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;

import java.util.List;

public interface MenuService {
    MenuDO getMenuIdByPermission(String permission);

    List<Long> getMenuIdListByPermissionFromCache(String permission);
}
