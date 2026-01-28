    package cn.iocoder.lfl.framework.datapermission.config;

    import cn.iocoder.lfl.framework.common.biz.system.permission.PermissionCommonApi;
    import cn.iocoder.lfl.framework.datapermission.core.rule.dept.DeptDataPermissionRule;
    import cn.iocoder.lfl.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
    import cn.iocoder.lfl.framework.security.core.LoginUser;
    import org.springframework.boot.autoconfigure.AutoConfiguration;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
    import org.springframework.context.annotation.Bean;

    import java.util.List;

    @AutoConfiguration
    @ConditionalOnClass(LoginUser.class)
    @ConditionalOnBean(value = {DeptDataPermissionRuleCustomizer.class})
    public class LflDeptDataPermissionAutoConfiguration {

        @Bean
        public DeptDataPermissionRule deptDataPermissionRule(PermissionCommonApi permissionApi,
                                                             List<DeptDataPermissionRuleCustomizer> customizers){
            DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
            customizers.forEach(customizer -> customizer.customize(rule));
            return rule;
        }
    }
