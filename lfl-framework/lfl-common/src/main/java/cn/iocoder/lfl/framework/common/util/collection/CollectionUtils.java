package cn.iocoder.lfl.framework.common.util.collection;

import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T,U> Set<U> converSet(Collection<T> from, Function<T,U> function){
        if(CollUtil.isEmpty(from)){
            return new HashSet<>();
        }
        return from.stream().map(function).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T,U> List<U> convertList(Collection<T> from, Function<T,U> function){
        if(CollUtil.isEmpty(from)){
            return new ArrayList<>();
        }
        return from.stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        if (item == null) {
            return;
        }
        coll.add(item);
    }
}
