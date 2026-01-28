package cn.iocoder.lfl.framework.datapermission.core.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.lfl.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.lfl.framework.datapermission.core.aop.DataPermissionContextHolder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DataPermissionRuleFactoryImpl implements DataPermissionRuleFactory{

    private final List<DataPermissionRule> rules;

    @Override
    public List<DataPermissionRule> getDataPermissionRules() {
        return rules;
    }

    @Override
    public List<DataPermissionRule> getDataPermissionRules(String mappedStatementId) {
        //1.无数据权限
        if(CollUtil.isEmpty(rules)){
            return Collections.emptyList();
        }
        //2.未配置,则默认开启
        DataPermission dataPermission = DataPermissionContextHolder.get();
        if(dataPermission == null){
            return Collections.emptyList();
        }
        //3.配置了,但是已禁用
        if(!dataPermission.enable()){
            return Collections.emptyList();
        }
        //4.配置了,且已启用,则进行过滤
        if(ArrayUtil.isNotEmpty(dataPermission.includeRules())){
            return rules.stream().filter(rule -> ArrayUtils.contains(dataPermission.includeRules(), rule.getClass()))
                    .collect(Collectors.toList());
        }
        //5.配置了,且已启用,则进行过滤
        if(ArrayUtil.isNotEmpty(dataPermission.excludeRules())){
            return rules.stream().filter(rule -> !ArrayUtils.contains(dataPermission.excludeRules(), rule.getClass()))
                    .collect(Collectors.toList());
        }
        //6.已配置,开启全部规则
        return rules;

    }
}
