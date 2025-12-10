package cn.iocoder.lfl.module.system.mapper;

import cn.iocoder.lfl.module.system.pojo.DO.SysLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {
}
