package cn.iocoder.lfl.framework.security.core.service;

import cn.iocoder.lfl.framework.common.biz.system.permission.PermissionCommonApi;
import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;
import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;

@RequiredArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService{

    private final PermissionCommonApi permissionApi;
    @Override
    public boolean hasPermission(String permission) {
        return hasAnyPermissions( permission);
    }

    @Override
    public boolean hasAnyPermissions(String... permissions) {

        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        if(loginUserId == null){
            return false;
        }
        return permissionApi.hasAnyPermissions(loginUserId, permissions);
    }
}
