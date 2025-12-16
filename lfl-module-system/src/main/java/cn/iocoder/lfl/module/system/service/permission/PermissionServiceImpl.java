package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleMenuDO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.RoleMenuMapper;
import cn.iocoder.lfl.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.iocoder.lfl.framework.common.util.collection.CollectionUtils.converSet;
import static cn.iocoder.lfl.framework.common.util.collection.CollectionUtils.convertList;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
       if(ArrayUtil.isEmpty(permissions)){
           return true;
       }

       //获取当前登录的角色集合,如果为空,说明无权限
        List<RoleDO> roleDOS = getEnableUserRoleListByUserIdFromCache(userId);
        if(CollUtil.isEmpty(roleDOS)){
            return false;
        }
        //进行校验权限
        for(String permission : permissions){
            if(hasAnyPermission(roleDOS,permission)){
                return true;
            }
        }
        //如果是超级管理员也可以直接放行
        return roleService.hasAnySuperAdmin(converSet(roleDOS,RoleDO::getId));
    }

    private boolean hasAnyPermission(List<RoleDO> roleDOS, String permission) {
        List<Long> menuIds = menuService.getMenuIdListByPermissionFromCache(permission);
        // 采用严格模式，如果权限找不到对应的 Menu 的话，也认为没有权限
        if (CollUtil.isEmpty(menuIds)) {
            return false;
        }
        Set<Long> roleIds = CollectionUtils.converSet(roleDOS,RoleDO::getId);
        for(Long menuId : menuIds){
            List<Long> menuRoleIds = getSelf().getMenuRoleIdListByMenuIdFromCache(menuId);
            if(CollUtil.containsAny(menuRoleIds,roleIds)){
                return true;
            }
        }
        return false;
    }

    @Cacheable(value = RedisConstants.MENU_ROLE_ID_LIST,key = "#menuId")
    public List<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId) {
        return convertList(roleMenuMapper.selectListByMenuId(menuId), RoleMenuDO::getRoleId);
    }

    public List<RoleDO> getEnableUserRoleListByUserIdFromCache(Long userId) {
        Set<Long> roleIds = getSelf().getUserRoleIdListByUserIdFromCache(userId);
        List<RoleDO> roleDOS = roleService.getRoleListFromCache(roleIds);
        roleDOS.removeIf(roleDO -> CommonStatusEnum.isDisable(roleDO.getStatus()));
        return roleDOS;
    }

    @Cacheable(value = RedisConstants.USER_ROLE_ID_LIST,key = "#userId")
    public Set<Long> getUserRoleIdListByUserIdFromCache(Long userId) {
        return getUserRoleIdListByUserId(userId);
    }

    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return CollectionUtils.converSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        return false;
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleId(Collection<Long> roleIds) {
        return Set.of();
    }


    private PermissionServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
