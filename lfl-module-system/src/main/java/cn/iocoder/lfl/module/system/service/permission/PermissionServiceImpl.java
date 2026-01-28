package cn.iocoder.lfl.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.lfl.framework.common.biz.system.permission.dto.DeptDataPermissionRespDTO;
import cn.iocoder.lfl.framework.common.enums.CommonStatusEnum;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleMenuDO;
import cn.iocoder.lfl.module.system.dal.dataobject.permission.UserRoleDO;
import cn.iocoder.lfl.module.system.dal.mysql.permission.RoleMenuMapper;
import cn.iocoder.lfl.module.system.dal.mysql.permission.UserRoleMapper;
import cn.iocoder.lfl.module.system.dal.redis.RedisKeyConstants;
import cn.iocoder.lfl.module.system.enums.permission.DataScopeEnum;
import cn.iocoder.lfl.module.system.service.dept.DeptService;
import cn.iocoder.lfl.module.system.service.user.AdminUserService;
import com.google.common.base.Suppliers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;

import static cn.iocoder.lfl.framework.common.util.collection.CollectionUtils.converSet;
import static cn.iocoder.lfl.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.lfl.framework.common.util.json.JsonUtils.toJsonString;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService{

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private AdminUserService userService;
    @Autowired
    private DeptService deptService;

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

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        //如果角色为空 直接返回 true
        if(ArrayUtil.isEmpty(roles)){
            return true;
        }
        List<RoleDO> roleDOS = getEnableUserRoleListByUserIdFromCache(userId);
        if(CollUtil.isEmpty(roleDOS)){
            return false;
        }
        Set<String> codes = converSet(roleDOS, RoleDO::getCode);
        // 1.是否有角色
        if(CollUtil.containsAny(codes,CollUtil.newHashSet( roles))){
            return true;
        }
        // 2.是否是超级管理员
        return roleService.hasAnySuperAdmin(converSet(roleDOS,RoleDO::getId));
    }

    // ========== 角色-菜单的相关方法  ========== 以下

    @Cacheable(value = RedisKeyConstants.MENU_ROLE_ID_LIST,key = "#menuId")
    public List<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId) {
        return convertList(roleMenuMapper.selectListByMenuId(menuId), RoleMenuDO::getRoleId);
    }


    public List<RoleDO> getEnableUserRoleListByUserIdFromCache(Long userId) {
        Set<Long> roleIds = getSelf().getUserRoleIdListByUserIdFromCache(userId);
        List<RoleDO> roleDOS = roleService.getRoleListFromCache(roleIds);
        if(roleDOS==null){
            return null;
        }
        roleDOS.removeIf(roleDO -> CommonStatusEnum.isDisable(roleDO.getStatus()));
        return roleDOS;
    }

    @Override
    @Cacheable(value = RedisKeyConstants.USER_ROLE_ID_LIST,key = "#userId"
            ,unless = "#result = null ")
    public Set<Long> getUserRoleIdListByUserIdFromCache(Long userId) {
        return getUserRoleIdListByUserId(userId);
    }

    @Override
    public DeptDataPermissionRespDTO getDeptDataPermission(Long userId) {
        // 获得用户的角色
        List<RoleDO> roles = getEnableUserRoleListByUserIdFromCache(userId);
        //如果角色为空,则只能查看自己
        DeptDataPermissionRespDTO result = new DeptDataPermissionRespDTO();
        if(CollUtil.isEmpty(roles)){
            result.setSelf(true);
            return result;
        }
        // 获得用户的部门编号的缓存，通过 Guava 的 Suppliers 惰性求值，即有且仅有第一次发起 DB 的查询
        Supplier<Long> userDeptId = Suppliers.memoize(() -> userService.getUser(userId).getDeptId());
        //遍历每个角色,进行计算
        for (RoleDO role : roles) {
            // 为空时，跳过
            if (role.getDataScope() == null) {
                continue;
            }
            // 情况一，ALL
            if (Objects.equals(role.getDataScope(), DataScopeEnum.ALL.getScope())) {
                result.setAll(true);
                continue;
            }
            // 情况二，DEPT_CUSTOM
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_CUSTOM.getScope())) {
                result.setDeptIds(role.getDataScopeDeptIds());
                continue;
            }
            // 情况三，DEPT_ONLY
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_ONLY.getScope())) {
                CollectionUtils.addIfNotNull(result.getDeptIds(), userDeptId.get());
                continue;
            }
            // 情况四，DEPT_CHILDREN
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_AND_CHILD.getScope())) {
                CollUtil.addAll(result.getDeptIds(),deptService.getChildDeptIdListFromCache(userDeptId.get()));
                // 添加本身部门编号
                CollUtil.addAll(result.getDeptIds(), userDeptId.get());
                continue;
            }
            // 情况五，DEPT_SELF
            if (Objects.equals(role.getDataScope(), DataScopeEnum.SELF.getScope())) {
                result.setSelf(true);
                continue;
            }
            //未知情况
            log.error("[getDeptDataPermission][LoginUser({}) role({}) 无法处理]", userId, toJsonString(result));
        }
        return result;
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return CollectionUtils.converSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleId(Collection<Long> roleIds) {
        return Set.of();
    }


    /**
     * 获取自身Bean对象,用于时springAop发挥作用
     * @return
     */
    private PermissionServiceImpl getSelf(){
        return SpringUtil.getBean(getClass());
    }
}
