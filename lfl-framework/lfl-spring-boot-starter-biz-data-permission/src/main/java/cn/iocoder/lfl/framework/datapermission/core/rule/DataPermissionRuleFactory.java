package cn.iocoder.lfl.framework.datapermission.core.rule;

import java.util.List;

public interface DataPermissionRuleFactory {

   List<DataPermissionRule> getDataPermissionRules();


   List<DataPermissionRule> getDataPermissionRules(String mappedStatementId);
}
