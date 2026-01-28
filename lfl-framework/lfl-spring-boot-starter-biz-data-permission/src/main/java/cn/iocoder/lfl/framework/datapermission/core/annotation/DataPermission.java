package cn.iocoder.lfl.framework.datapermission.core.annotation;

import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRule;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    boolean enable() default true;

    Class<? extends DataPermissionRule>[] includeRules() default {};

    Class<? extends DataPermissionRule>[] excludeRules() default {};
}
