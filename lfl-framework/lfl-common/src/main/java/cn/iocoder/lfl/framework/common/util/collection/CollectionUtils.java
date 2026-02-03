package cn.iocoder.lfl.framework.common.util.collection;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T,U> Set<U> convertSet(Collection<T> from, Function<T,U> function){
        if(CollUtil.isEmpty(from)){
            return new HashSet<>();
        }
        return from.stream().map(function).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T,U> List<U> convertList(Collection<T> from, Function<T,U> function){
        if(CollUtil.isEmpty(from)){
            return new ArrayList<>();
        }
        return from.stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T,U> List<U> convertList(Collection<T> from,Function<T,U> func,Predicate<T> filter){
        if(CollUtil.isEmpty(from)){
            return new ArrayList<>();
        }
        return from.stream().filter(filter).map(func).collect(Collectors.toList());
    }

    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        if (item == null) {
            return;
        }
        coll.add(item);
    }

    public static boolean startWith(Collection<String> prefixs, String str) {
        if(CollUtil.isEmpty(prefixs) || StrUtil.isEmpty( str)){
            return false;
        }
        for (String suffix : prefixs) {
            if(StrUtil.startWith(str, suffix,false)){
                return true;
            }
        }
        return false;
    }
    public static <T,K> Map<K,T> convertMap(Collection<T> from,Function<T,K> keyFunc){
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from,keyFunc,Function.identity());
    }

    public static <T,K,V> Map<K,V> convertMap(Collection<T> from,Function<T,K> keyFunc,Function<T,V> valueFunc){
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from,keyFunc,valueFunc,(v1,v2) -> v1);
    }

    public static <T,K,V> Map<K,V> convertMap(Collection<T> from,Function<T,K> keyFunc,Function<T,V> valueFunc,BinaryOperator<V> mergeFunction){
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from,keyFunc,valueFunc,mergeFunction,HashMap::new);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }
}
