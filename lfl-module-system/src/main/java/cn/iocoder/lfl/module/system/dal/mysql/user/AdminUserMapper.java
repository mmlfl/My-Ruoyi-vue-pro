package cn.iocoder.lfl.module.system.dal.mysql.user;

import cn.iocoder.lfl.framework.mybatis.core.mybatis.BaseMapperX;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {
}
