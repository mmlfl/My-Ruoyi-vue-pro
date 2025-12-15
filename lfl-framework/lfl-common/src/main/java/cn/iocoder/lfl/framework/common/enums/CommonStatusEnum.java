package cn.iocoder.lfl.framework.common.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum CommonStatusEnum {
    ENABLE(0,"开启"),
    DISABLE(1,"禁用");

    private final Integer status;
    private final String name;

    CommonStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }


    public static boolean isDisable(Integer status){
        return Objects.equals(status,DISABLE.getStatus());
    }
}
