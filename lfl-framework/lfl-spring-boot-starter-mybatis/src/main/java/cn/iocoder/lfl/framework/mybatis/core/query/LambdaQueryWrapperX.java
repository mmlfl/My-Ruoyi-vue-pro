package cn.iocoder.lfl.framework.mybatis.core.query;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;


public class LambdaQueryWrapperX<T> extends LambdaQueryWrapper<T> {


    public LambdaQueryWrapperX<T> likeIfPresent(SFunction<T,?> column, String val) {
        if(StringUtils.hasText(val)){
            return (LambdaQueryWrapperX<T>) super.like(column,val);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> eqIfPresent(SFunction<T,?> column, Object obj) {
       if(ObjectUtil.isNotEmpty(obj)){
           return (LambdaQueryWrapperX<T>) super.eq(column,obj);
       }
       return this;
    }

    public LambdaQueryWrapperX<T> betweenIfPresent(SFunction<T,?> column, Object[] vals) {
        Object val1 = ArrayUtils.get(vals,0);
        Object val2 = ArrayUtils.get(vals,1);
        return betweenIfPresent(column,val1,val2);
    }

    private LambdaQueryWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if(val1 != null && val2 != null){
            return (LambdaQueryWrapperX<T>) super.between(column,val1,val2);
        }
        if(val1 != null){
            return (LambdaQueryWrapperX<T>) super.ge(column,val1);
        }
        if(val2 != null){
            return (LambdaQueryWrapperX<T>) super.le(column,val2);
        }
        return this;
    }

    public LambdaQueryWrapperX<T> inIfPresent(SFunction<T,?> column, Collection<?> values) {
        if(ObjectUtil.isAllNotEmpty(values)){
            return (LambdaQueryWrapperX<T>)super.in(column,values);
        }
        return this;
    }

    @Override
    public LambdaQueryWrapperX<T> eq(SFunction<T, ?> column, Object val) {
        super.eq(column, val);
        return this;
    }
}
