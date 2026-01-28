package cn.iocoder.lfl.framework.datapermission.config;

import cn.iocoder.lfl.framework.datapermission.core.aop.DataPermissionAnnotationAdvisor;
import cn.iocoder.lfl.framework.datapermission.core.db.DataPermissionRuleHandler;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRule;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRuleFactory;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRuleFactoryImpl;
import cn.iocoder.lfl.framework.mybatis.config.LflMybatisAutoConfiguration;
import cn.iocoder.lfl.framework.mybatis.core.util.MyBatisUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@AutoConfiguration
public class LflDataPermissionAutoConfiguration {
    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }

    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    @Bean
    public DataPermissionRuleHandler dataPermissionRuleHandler(MybatisPlusInterceptor interceptor,
                                                               DataPermissionRuleFactory factory) {
        DataPermissionRuleHandler handler = new DataPermissionRuleHandler(factory);
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(handler);
        // 将handler添加到mybatis处理sql的过滤器链中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, dataPermissionInterceptor, 0);
        return handler;
    }


}
