package cn.iocoder.lfl.framework.datapermission.core.rule;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;

import java.util.Set;

public interface DataPermissionRule {

    Set<String> tableNames();


    Expression getExpression(String tableName, Alias tableAlias);
}
