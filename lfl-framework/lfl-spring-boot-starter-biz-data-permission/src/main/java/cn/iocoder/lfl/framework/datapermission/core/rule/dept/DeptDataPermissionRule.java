package cn.iocoder.lfl.framework.datapermission.core.rule.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.lfl.framework.common.biz.system.permission.PermissionCommonApi;
import cn.iocoder.lfl.framework.common.biz.system.permission.dto.DeptDataPermissionRespDTO;
import cn.iocoder.lfl.framework.common.enums.UserTypeEnum;
import cn.iocoder.lfl.framework.common.util.collection.CollectionUtils;
import cn.iocoder.lfl.framework.common.util.json.JsonUtils;
import cn.iocoder.lfl.framework.datapermission.core.rule.DataPermissionRule;
import cn.iocoder.lfl.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.lfl.framework.mybatis.core.util.MyBatisUtils;
import cn.iocoder.lfl.framework.security.core.LoginUser;
import cn.iocoder.lfl.framework.security.core.util.SecurityFrameworkUtils;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
public class DeptDataPermissionRule implements DataPermissionRule {

    private final PermissionCommonApi permissionCommonApi;

    private static final String DEPT_COLUMN_NAME = "dept_id";
    private static final String USER_COLUMN_NAME = "user_id";
    // 上下文 key
    private static final String CONTEXT_KEY = DataPermissionRule.class.getSimpleName();
    //要修改sql语句所需要的表名和字段名
    private final Map<String,String> deptColumns = new HashMap<>();
    private final Map<String,String> userColumns = new HashMap<>();

    private final Set<String> TABLE_NAMES = new HashSet<>();
    @Override
    public Set<String> tableNames() {
        return TABLE_NAMES;
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        // 只有有登陆用户的情况下，才进行数据权限的处理
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            return null;
        }
        // 只有管理员类型的用户，才进行数据权限的处理
        if (ObjectUtil.notEqual(loginUser.getUserType(), UserTypeEnum.ADMIN.getValue())) {
            return null;
        }
        //获得数据权限
        DeptDataPermissionRespDTO deptDataPermission = loginUser.getContext(CONTEXT_KEY, DeptDataPermissionRespDTO.class);
        if(deptDataPermission == null){
            deptDataPermission = permissionCommonApi.getDeptDataPermission(loginUser.getId());
            if (deptDataPermission == null) {
                log.error("[getExpression][LoginUser({}) 获取数据权限为 null]", JsonUtils.toJsonString(loginUser));
                throw new NullPointerException(String.format("LoginUser(%d) Table(%s/%s) 未返回数据权限",
                        loginUser.getId(), tableName, tableAlias.getName()));
            }
            // 添加到上下文中，避免重复计算
            loginUser.setContext(CONTEXT_KEY, deptDataPermission);
        }
        // 情况一，如果是 ALL 可查看全部，则无需拼接条件
        if (deptDataPermission.getAll()) {
            return null;
        }
        // 情况二，即不能查看部门，又不能查看自己，则说明 100% 无权限
        if (CollUtil.isEmpty(deptDataPermission.getDeptIds())
                && Boolean.FALSE.equals(deptDataPermission.getSelf())) {
            return new EqualsTo(null, null); // WHERE null = null，可以保证返回的数据为空
        }
        // 情况三，拼接 Dept 和 User 的条件，最后组合
        Expression deptExpression = buildDeptExpression(tableName, tableAlias, deptDataPermission.getDeptIds());
        Expression userExpression = buildUserExpression(tableName, tableAlias, deptDataPermission.getSelf(), loginUser.getId());

        if (deptExpression == null) {
            return userExpression;
        }
        if (userExpression == null) {
            return deptExpression;
        }

        return new ParenthesedExpressionList(new OrExpression(deptExpression, userExpression));
    }

    private Expression buildUserExpression(String tableName, Alias tableAlias, Boolean self,Long userId) {
        // 如果不查看自己，则无需作为条件
        if (Boolean.FALSE.equals(self)) {
            return null;
        }
        String columnName = userColumns.get(tableName);
        if (StrUtil.isEmpty(columnName)) {
            return null;
        }
        return new EqualsTo(MyBatisUtils.buildColumn(tableName, tableAlias, columnName),
                new LongValue(userId));
    }

    private Expression buildDeptExpression(String tableName, Alias tableAlias, Set<Long> deptIds) {
        String column = deptColumns.get(tableName);
        if(StrUtil.isEmpty( column)){
            return null;
        }
        //如果deptids为空 则无条件
        if(CollUtil.isEmpty(deptIds)){
            return null;
        }

        return new InExpression(MyBatisUtils.buildColumn(tableName,tableAlias,column),
                // Parenthesis 的目的，是提供 (1,2,3) 的 () 左右括号
                new ParenthesedExpressionList(new ExpressionList<LongValue>(CollectionUtils.convertList(deptIds, LongValue::new))));
    }

    // ==================== 添加配置 ====================

    public void addDeptColumn(Class<? extends BaseDO> entityClass) {
        addDeptColumn(entityClass, DEPT_COLUMN_NAME);
    }

    public void addDeptColumn(Class<? extends BaseDO> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
        addDeptColumn(tableName, columnName);
    }

    public void addDeptColumn(String tableName, String columnName) {
        deptColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }

    public void addUserColumn(Class<? extends BaseDO> entityClass) {
        addUserColumn(entityClass, USER_COLUMN_NAME);
    }

    public void addUserColumn(Class<? extends BaseDO> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
        addUserColumn(tableName, columnName);
    }

    public void addUserColumn(String tableName, String columnName) {
        userColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }
}
