package cn.iocoder.lfl.module.system.dal.mysql.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.iocoder.lfl.module.system.dal.dataobject.user.AdminUserDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserDo> {
}
