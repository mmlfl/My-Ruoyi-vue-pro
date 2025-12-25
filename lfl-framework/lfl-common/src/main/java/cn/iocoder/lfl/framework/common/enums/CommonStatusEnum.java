package cn.iocoder.lfl.framework.common.enums;

import cn.iocoder.lfl.framework.common.core.ArrayValuable;
import lombok.Getter;
import org.aspectj.weaver.ArrayAnnotationValue;

import java.util.Objects;

@Getter
public enum CommonStatusEnum implements ArrayValuable<Integer> {
    ENABLE(0,"开启"),
    DISABLE(1,"禁用");

    public static final Integer[] ARRAYS = {0,1};

    private final Integer status;
    private final String name;

    CommonStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }


    public static boolean isDisable(Integer status){
        return Objects.equals(status,DISABLE.getStatus());
    }

    @Override
    public Integer[] array() {
        return ARRAYS;
    }
}
