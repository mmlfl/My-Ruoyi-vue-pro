package cn.iocoder.lfl.module.system.dal.mysql.permission;

import cn.iocoder.lfl.module.system.dal.dataobject.permission.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
}
