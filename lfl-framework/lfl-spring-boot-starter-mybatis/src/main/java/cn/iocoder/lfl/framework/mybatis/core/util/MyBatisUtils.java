package cn.iocoder.lfl.framework.mybatis.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.lfl.framework.common.pojo.PageParam;
import cn.iocoder.lfl.framework.common.pojo.SortingField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;

import java.util.ArrayList;
import java.util.Collection;

public class MyBatisUtils {


    @SuppressWarnings("PatterVariableCanBeUsed")
    public static <T> void addOrder(Wrapper<T> wrapper, Collection<SortingField> sortingFields){
        if(CollUtil.isEmpty(sortingFields)){
            return;
        }
        if(wrapper instanceof QueryWrapper) {
            QueryWrapper<T> queryWrapper = (QueryWrapper<T>) wrapper;
            for (SortingField sortingField : sortingFields) {
                queryWrapper.orderBy(
                        true,
                        SortingField.ASC.equals(sortingField.getOrder()),
                        StrUtil.toUnderlineCase(sortingField.getField())
                );
            }
        }else if(wrapper instanceof LambdaQueryWrapper){
                // LambdaQueryWrapper 不直接支持字符串字段排序，使用 last 方法拼接 ORDER BY
                LambdaQueryWrapper<T> lambdaQueryWrapper = (LambdaQueryWrapper<T>) wrapper;
                StringBuilder orderBy = new StringBuilder();
                for (SortingField sortingField : sortingFields) {
                    if(StrUtil.isNotEmpty(orderBy)){
                        orderBy.append(", ");
                    }
                    orderBy.append(StrUtil.toUnderlineCase(sortingField.getField()))
                            .append(" ")
                            .append(SortingField.ASC.equals(sortingField.getOrder()) ? "ASC" : "DESC");
                }
                lambdaQueryWrapper.last(" ORDER BY " + orderBy);
            }else {
                throw new IllegalArgumentException("Unsupported wrapper type: " + wrapper.getClass().getName());
            }
        }

    public static <T> Page<T> buildPage(PageParam pageParam, Collection<SortingField> sortingFields) {
        Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        if(CollUtil.isNotEmpty(sortingFields)){
            for(SortingField sortingField : sortingFields){
                page.addOrder(new OrderItem().setAsc(SortingField.ASC.equals(sortingField.getOrder()))
                        .setColumn(StrUtil.toUnderlineCase(sortingField.getField())));
            }
        }
        return page;
    }

    public static void addInterceptor(MybatisPlusInterceptor interceptor, InnerInterceptor inner,int index){
        ArrayList<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
        inners.add(index,inner);
        interceptor.setInterceptors(inners);
    }

    public static Column buildColumn(String tableName, Alias tableAlias, String column) {
        if(tableAlias != null){
            tableName = tableAlias.getName();
        }
        return new Column(tableName + StringPool.DOT + column);
    }
}
