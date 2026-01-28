package cn.iocoder.lfl.framework.datapermission.core.db;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRule;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRuleFactory;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Table;

import java.util.List;

@RequiredArgsConstructor
public class DataPermissionRuleHandler implements MultiDataPermissionHandler {

    private final DataPermissionRuleFactory factory;

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        List<DataPermissionRule> rules = factory.getDataPermissionRules(mappedStatementId);
        if(CollUtil.isEmpty( rules)){
            return null;
        }
        Expression allExpression = null;
        for(DataPermissionRule rule : rules){
            if(!rule.tableNames().contains(table.getName())){
                continue;
            }
            Expression expression = rule.getExpression(table.getName(), table.getAlias());
            if(expression == null){
                continue;
            }
            //拼接到Expression中
            allExpression = allExpression == null ? expression : new AndExpression(allExpression, expression);
        }

        return allExpression;
    }
}
