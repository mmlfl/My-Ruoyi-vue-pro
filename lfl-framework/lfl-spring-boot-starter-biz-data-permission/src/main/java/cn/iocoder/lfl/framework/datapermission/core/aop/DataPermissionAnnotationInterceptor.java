package cn.iocoder.lfl.framework.datapermission.core.aop;

import cn.iocoder.lfl.framework.datapermission.core.annotation.DataPermission;
import lombok.Getter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@DataPermission
public class DataPermissionAnnotationInterceptor implements MethodInterceptor {

    private final DataPermission DATA_PERMISSION_NULL =
            DataPermissionAnnotationInterceptor.class.getAnnotation(DataPermission.class);

    @Getter
    private final Map<MethodClassKey, DataPermission> dataPermissionCache =
            new ConcurrentHashMap<>();
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DataPermission dataPermission = getDataPermission(invocation);
        if(dataPermission != null){
            DataPermissionContextHolder.add(dataPermission);
        }
        try{
            return invocation.proceed();
        }finally {
            //出栈
            if(dataPermission != null){
                DataPermissionContextHolder.remove();
            }
        }
    }

    private DataPermission getDataPermission(MethodInvocation invocation) {
        //从缓存中获取
        Method method = invocation.getMethod();
        Object object = invocation.getThis();
        Class<?> clazz = object!=null?object.getClass():method.getDeclaringClass();
        MethodClassKey key = new MethodClassKey(method, clazz);
        DataPermission dataPermission = dataPermissionCache.get(key);
        if(dataPermission!=null){
            return dataPermission != DATA_PERMISSION_NULL?dataPermission:null;
        }
        //先从方法中获取
        DataPermission annotation = AnnotationUtils.findAnnotation(method, DataPermission.class);
        //再从类中获取
        if(annotation == null){
            annotation = AnnotationUtils.findAnnotation(clazz, DataPermission.class);
        }
        dataPermissionCache.put(key,annotation==null?annotation:DATA_PERMISSION_NULL);
        return annotation;
    }
}
