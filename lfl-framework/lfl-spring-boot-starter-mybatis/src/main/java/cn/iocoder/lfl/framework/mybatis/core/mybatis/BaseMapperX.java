package cn.iocoder.lfl.framework.mybatis.core.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;

public interface BaseMapperX<T> extends MPJBaseMapper<T> {

    default List<T> selectList(SFunction<T,?> field,Object value){
        return selectList(new LambdaQueryWrapper<T>().eq(field,value));
    }
}
