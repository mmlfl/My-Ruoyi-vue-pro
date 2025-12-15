package cn.iocoder.lfl.module.system.enums.permission;

import cn.iocoder.lfl.framework.common.util.object.ObjectUtils;
import lombok.Getter;

@Getter
public enum RoleCodeEnum {
    SUPER_ADMIN("super_admin", "超级管理员");

    private final String code;
    private final String name;

    RoleCodeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }
}
