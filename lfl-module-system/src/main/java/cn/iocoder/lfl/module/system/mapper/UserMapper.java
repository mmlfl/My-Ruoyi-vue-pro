package cn.iocoder.lfl.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.iocoder.lfl.module.system.pojo.DO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
