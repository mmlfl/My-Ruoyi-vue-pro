package cn.iocoder.lfl.framework.datapermission.core.aop;

import cn.iocoder.lfl.framework.datapermission.core.annotation.DataPermission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DataPermissionAnnotationAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;

    private final Pointcut pointcut;

    public DataPermissionAnnotationAdvisor(){
        this.advice = new DataPermissionAnnotationInterceptor();
        this.pointcut = buildPointcut();
    }

    private Pointcut buildPointcut() {
        AnnotationMatchingPointcut annotationMatchingPointcut = new AnnotationMatchingPointcut(DataPermission.class, true);
        AnnotationMatchingPointcut annotationMatchingPointcut1 = new AnnotationMatchingPointcut(null, DataPermission.class, true);
        return new ComposablePointcut(annotationMatchingPointcut).union(annotationMatchingPointcut1);
    }
}
