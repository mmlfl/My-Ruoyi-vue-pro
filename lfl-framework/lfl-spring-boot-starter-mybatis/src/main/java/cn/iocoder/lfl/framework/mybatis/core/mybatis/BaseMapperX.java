package cn.iocoder.lfl.framework.mybatis.core.mybatis;

import cn.iocoder.lfl.framework.common.pojo.PageParam;
import cn.iocoder.lfl.framework.common.pojo.PageResult;
import cn.iocoder.lfl.framework.common.pojo.SortingField;
import cn.iocoder.lfl.framework.mybatis.core.util.MyBatisUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface BaseMapperX<T> extends MPJBaseMapper<T> {

    default List<T> selectList(SFunction<T,?> field,Object value){
        return selectList(new LambdaQueryWrapper<T>().eq(field,value));
    }

    default T selectOne(SFunction<T,?> field,Object value){
        return selectOne(new LambdaQueryWrapper<T>().eq(field,value));
    }

    default PageResult<T> selectPage(PageParam pageParam, @Param("ew")Wrapper<T> queryWrapper){
        return selectPage(pageParam,null,queryWrapper);
    }

    default PageResult<T> selectPage(PageParam pageParam, Collection<SortingField> sortingFields,@Param("ew")Wrapper<T> wrapper){
        //1.特殊不分页
        if(PageParam.PAGE_SIZE_NONE.equals(pageParam.getPageSize())){
            MyBatisUtils.addOrder(wrapper,sortingFields);
            List<T> list = selectList(wrapper);
            return new PageResult<>(list,(long)list.size());
        }
        //2.进行分页查询
        IPage<T> mpPage = MyBatisUtils.buildPage(pageParam, sortingFields);
        selectPage(mpPage, wrapper);
        // 转换返回
        return new PageResult<>(mpPage.getRecords(), mpPage.getTotal());
    }


    default T selectOne(SFunction<T,?> field1,Object value1,SFunction<T,?> field2,Object value2){
        return selectOne(new LambdaQueryWrapper<T>().eq(field1,value1).eq(field2,value2));
    }
}
