package cn.iocoder.lfl.module.system.service.permission;

import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.iocoder.lfl.module.system.controller.admin.permission.vo.menu.MenuSaveReqVO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.MenuDO;

import java.util.List;

public interface MenuService {
    MenuDO getMenuIdByPermission(String permission);

    List<Long> getMenuIdListByPermissionFromCache(String permission);

    Long createMenu(MenuSaveReqVO reqVO);

    void updateMenu(MenuSaveReqVO reqVO);

    void deleteMenu(Long id);

    void deleteMenuList(List<Long> ids);

    MenuRespVO getMenu(Long id);

    List<MenuRespVO> getMenuList(MenuListReqVO reqVO);
}
