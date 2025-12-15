package cn.iocoder.lfl.framework.common.util.object;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ObjectUtils {

    @SafeVarargs
    public static <T> boolean equalsAny(T obj,T... objs){
        return Arrays.asList(objs).contains( obj);
    }
}
