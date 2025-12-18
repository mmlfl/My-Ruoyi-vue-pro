package cn.iocoder.lfl.framework.common.util.object;


import cn.hutool.core.bean.BeanUtil;

/**
 * Bean 工具类
 *
 * 1. 默认使用 {@link cn.hutool.core.bean.BeanUtil} 作为实现类，虽然不同 bean 工具的性能有差别，但是对绝大多数同学的项目，不用在意这点性能
 * 2. 针对复杂的对象转换，可以搜参考 AuthConvert 实现，通过 mapstruct + default 配合实现
 *
 */
public class BeanUtils {
    public static <T> T toBean(Object source, Class<T> targetClass){
        return BeanUtil.toBean(source, targetClass);
    }
}
