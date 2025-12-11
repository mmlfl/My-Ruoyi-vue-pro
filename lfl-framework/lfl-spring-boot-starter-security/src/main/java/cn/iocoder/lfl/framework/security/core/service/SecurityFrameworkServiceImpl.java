package cn.iocoder.lfl.framework.security.core.service;

import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;

import java.util.Objects;

public class SecurityFrameworkServiceImpl implements SecurityFrameworkService{

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
        return false;
    }
}
