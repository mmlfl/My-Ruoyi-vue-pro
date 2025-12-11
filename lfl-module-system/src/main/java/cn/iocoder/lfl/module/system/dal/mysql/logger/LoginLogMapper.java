package cn.iocoder.lfl.module.system.dal.mysql.logger;

import cn.iocoder.lfl.module.system.dal.dataobject.logger.LoginLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLogDO> {
}
